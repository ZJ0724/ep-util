package com.easipass.epUtil.module;

import com.easipass.epUtil.component.ChromeDriver;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.config.DaKaSignConfig;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.DaKaSign;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.DateUtil;
import java.util.ArrayList;
import java.util.List;

public class DaKaModule {

    /** 打卡配置 */
    private final com.easipass.epUtil.entity.config.DaKa daKa = Config.getConfig().getDaKa();

    /** 日志组件 */
    private final Log log = Log.getLog();

    /** 打卡日志 */
    private final List<String> daKaLog = new ArrayList<>();

    /** 是否已经开启打卡 */
    public boolean isStart = false;

    /** 单例 */
    private final static DaKaModule DA_KA = new DaKaModule();

    /** daKaSign */
    private final DaKaSign daKaSign = DaKaSign.getDaKaSign();

    /**
     * 构造函数
     * */
    private DaKaModule() {}

    /**
     * 检查是否执行打卡
     *
     * @return 标记为1，则开始打卡，其他都不进行打卡
     * */
    public boolean check() {
        return DaKaSignConfig.START.getData().equals(daKaSign.getData());
    }

    /**
     * 开始打卡
     * */
    public void start() {
        if (isStart) {
            addLog("已开启打卡！");
            return;
        }

        isStart = true;

        // 将标记为设置成start
        daKaSign.setDaKa(DaKaSignConfig.START.getData());

        addLog("开启打卡...");

        // 开启另一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (check()) {
                    // 检查日期是否符合
                    boolean dateOk = false;
                    for (String date : daKa.getDate()) {
                        if (DateUtil.getDate("yyyy-MM-dd").equals(date)) {
                            dateOk = true;
                        }
                        if (dateOk) {
                            break;
                        }
                    }

                    // 检查星期是否符合
                    boolean weekOk = false;
                    for (String week : daKa.getWeek()) {
                        if (DateUtil.getWeek().equals(week)) {
                            weekOk = true;
                        }
                        if (weekOk) {
                            break;
                        }
                    }

                    // 日期符合或者星期符合都进行打卡
                    if (!(dateOk || weekOk)) {
                        continue;
                    }

                    // 匹配时间进行打卡
                    if (DateUtil.getNowTime().equals(daKa.getToWorkTime())) {
                        addLog("开始上班打卡...");
                    } else if (DateUtil.getNowTime().equals(daKa.getOffWorkTime())) {
                        addLog("开始下班打卡...");
                    } else {
                        continue;
                    }

                    ChromeDriver chromeDriver = new ChromeDriver();
                    chromeDriver.daKa();
                    chromeDriver.close();

                    addLog("打卡完成！");

                    // 打卡完等待1分钟
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        throw ErrorException.getErrorException(e.getMessage());
                    }
                }
            }
        }).start();
    }

    /**
     * 停止打卡
     * */
    public void stop() {
        // 将标记为设置成stop
        daKaSign.setDaKa(DaKaSignConfig.STOP.getData());
        log.info("停止打卡！");

        // 清除日志
        daKaLog.clear();

        // 打卡标记设置成false
        isStart = false;
    }

    /**
     * 添加日志
     * */
    private void addLog(String log) {
        this.log.info(log);
        daKaLog.add("[" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + "] - " + log);
    }

    /**
     * 获取日志
     * */
    public List<String> getLog() {
        return daKaLog;
    }

    /**
     * 获取单例
     * */
    public static DaKaModule getDaKa() {
        return DA_KA;
    }

    /**
     * 获取当前打卡状态
     * */
    public boolean getStatus() {
        return isStart;
    }

    /**
     * 手动打卡
     * */
    public void manualDaKa() {
        ChromeDriver chromeDriver = new ChromeDriver();
        try {
            chromeDriver.daKa();
            chromeDriver.close();
            addLog("已手动打卡！");
        } catch (ChromeDriverException e) {
            addLog(e.getMessage());
            throw ChromeDriverException.daKaException();
        }
    }

    /**
     * 清空日志
     * */
    public void cleanLog() {
        this.daKaLog.clear();
    }

}