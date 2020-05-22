package com.easipass.epUtil.common;

import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.enumeration.SystemOSEnum;
import com.easipass.epUtil.exception.ErrorException;

public class ProcessCommon {

    /**
     * 杀掉谷歌驱动进程
     * */
    public static void killChromeDriver() {
        try {
            // windows
            if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.windows) {
                Runtime.getRuntime().exec("taskkill /f /im " + ProjectConfig.CHROME_DRIVER.getName());
            }

            // linux
            if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.linux) {
                String cmd = "ps -aux | grep " + ProjectConfig.CHROME_DRIVER.getName() + " | grep -v grep | awk '{print $2}' | xargs kill -9";
                System.out.println(cmd);
                Runtime.getRuntime().exec(cmd);
            }

            // 休眠1秒
            Thread.sleep(1000);
        } catch (java.io.IOException | java.lang.InterruptedException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}
