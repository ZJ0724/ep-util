package com.easipass.util.core.service;

import com.easipass.util.core.BaseException;
import com.easipass.util.core.ChromeDriver;
import com.easipass.util.core.config.DaKaConfig;
import com.easipass.util.core.entity.DaKaLog;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.ThreadUtil;
import java.util.List;

/**
 * 打卡服务
 *
 * @author ZJ
 * */
public final class DaKaService {

    /**
     * 打卡配置
     * */
    private static final DaKaConfig DA_KA_CONFIG = DaKaConfig.getInstance();

    /**
     * 打卡日志
     * */
    private static final DaKaLog DA_KA_LOG = DaKaLog.getDaKaLog();

    /**
     * 构造函数
     * */
    public DaKaService() {
        new Thread(() -> {
            while (true) {
                // 如果打卡标识为1，这进行打卡
                if (!getStatus()) {
                    continue;
                }

                // 检查日期是否符合
                boolean dateOk = false;
                for (String date : DA_KA_CONFIG.date) {
                    if (DateUtil.getDate("yyyy-MM-dd").equals(date)) {
                        dateOk = true;
                    }
                    if (dateOk) {
                        break;
                    }
                }

                // 检查星期是否符合
                boolean weekOk = false;
                for (String week : DA_KA_CONFIG.week) {
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
                if (DateUtil.getNowTime().equals(DA_KA_CONFIG.toWorkTime)) {
                    DA_KA_LOG.addLog("开始上班打卡...");
                } else if (DateUtil.getNowTime().equals(DA_KA_CONFIG.offWorkTime)) {
                    DA_KA_LOG.addLog("开始下班打卡...");
                } else {
                    continue;
                }

                ChromeDriver chromeDriver = null;

                try {
                    chromeDriver = new ChromeDriver();
                    chromeDriver.daKa();
                    DA_KA_LOG.addLog("打卡完成！");
                } catch (BaseException e) {
                    DA_KA_LOG.addLog(e.getMessage());
                } finally {
                    if (chromeDriver != null) {
                        chromeDriver.close();
                    }
                }

                // 打卡完等待1分钟
                ThreadUtil.sleep(60000);
            }
        }, "daKa-thread").start();
    }

    /**
     * 开启自动打卡
     * */
    public void open() {
        DA_KA_CONFIG.sign = "1";
        DA_KA_CONFIG.commit();
    }

    /**
     * 关闭自动打卡
     * */
    public void close() {
        DA_KA_CONFIG.sign = "0";
        DA_KA_CONFIG.commit();
    }

    /**
     * 获取自动打卡状态
     *
     * @return 开启返回true
     * */
    public boolean getStatus() {
        return "1".equals(DA_KA_CONFIG.sign);
    }

    /**
     * 手动打卡
     * */
    public void manualDaKa() {
        ChromeDriver chromeDriver = null;

        try {
            chromeDriver = new ChromeDriver();
            chromeDriver.daKa();
        } catch (BaseException e) {
            DA_KA_LOG.addLog(e.getMessage());
        } finally {
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }
    }

    /**
     * 清空日志
     * */
    public void cleanLog() {
        DA_KA_LOG.setLog("");
    }

    /**
     * 获取日志
     *
     * @return 日志
     * */
    public List<String> getLog() {
        return DA_KA_LOG.getLogs();
    }

}