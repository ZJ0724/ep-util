package com.easipass.epUtil.module;

import com.easipass.epUtil.config.DaKaSignConfig;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DaKa {

    /**
     * 打卡配置
     * */
    private final com.easipass.epUtil.entity.config.DaKa daKa = Config.getConfig().getDaKa();

    /**
     * 日志
     * */
    private final Log log = Log.getLog();

    /**
     * 打卡日志
     * */
    private final List<String> daKaLog = new ArrayList<>();

    /**
     * 单例
     * */
    private final static DaKa DA_KA = new DaKa();

    /**
     * 构造函数
     * */
    private DaKa() {
        // 检查是否存在打卡标记文件，不存在生成默认标记文件
        if (!ProjectConfig.DAKA_SIGN.exists()) {
            InputStream inputStream = DaKa.class.getResourceAsStream(ResourcePathConfig.DAKA_SIGN);
            FileUtil.copyTextFile(inputStream, ProjectConfig.DAKA_SIGN);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }
        }
    }

    /**
     * 检查是否执行打卡
     *
     * @return 标记为1，则开始打卡，其他都不进行打卡
     * */
    public boolean check() {
        String dakSign = FileUtil.getData(ProjectConfig.DAKA_SIGN);

        return DaKaSignConfig.START.getData().equals(dakSign);
    }

    /**
     * 开始打卡
     * */
    public void start() {
        // 将标记为设置成start
        FileUtil.setData(ProjectConfig.DAKA_SIGN, DaKaSignConfig.START.getData());

        log.info("开启打卡...");

        // 开启另一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (check()) {
                    try {
                        log.info("等待打卡...");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw ErrorException.getErrorException(e.getMessage());
                    }

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
                        log.info("开始上班打卡...");
                        addLog("开始上班打卡...");
                        ChromeDriver chromeDriver = new ChromeDriver();
                        chromeDriver.daKa();
                        chromeDriver.close();
                        addLog("打卡完成！");
                    }
                    if (DateUtil.getNowTime().equals(daKa.getOffWorkTime())) {
                        log.info("开始下班打卡");
                        addLog("开始下班打卡...");
                        ChromeDriver chromeDriver = new ChromeDriver();
                        chromeDriver.daKa();
                        chromeDriver.close();
                        addLog("打卡完成！");
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
        FileUtil.setData(ProjectConfig.DAKA_SIGN, DaKaSignConfig.STOP.getData());
        log.info("停止打卡！");
    }

    /**
     * 添加日志
     * */
    private void addLog(String log) {
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
    public static DaKa getDaKa() {
        return DA_KA;
    }

}