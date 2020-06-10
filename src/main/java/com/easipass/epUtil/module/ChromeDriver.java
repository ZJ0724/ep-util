package com.easipass.epUtil.module;

import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.SystemTypeConfig;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.exception.ErrorException;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;

public class ChromeDriver {

    private WebDriver webDriver;

    public ChromeDriver() {
        webDriver = new ChromeWebDriver(ProjectConfig.CHROME_DRIVER);
        Log.getLog().info("已打开谷歌驱动!");
    }

    /**
     * 检查驱动
     * */
    public static void check() {
        try {
            WebDriver webDriver = new ChromeWebDriver(ProjectConfig.CHROME_DRIVER);
            webDriver.close();
        } catch (WebDriverException e) {
            ChromeDriver.killChromeDriverProcess();
            throw ChromeDriverException.chromeDriverFileException();
        }
    }

    /**
     * 杀掉谷歌驱动进程
     * */
    private static void killChromeDriverProcess() {
        try {
            // windows
            if (ProjectConfig.SYSTEM_TYPE == SystemTypeConfig.windows) {
                Runtime.getRuntime().exec("taskkill /f /im " + ProjectConfig.CHROME_DRIVER.getName());
            }

            // linux
            if (ProjectConfig.SYSTEM_TYPE == SystemTypeConfig.linux) {
                String cmd = "ps -aux | grep " + ProjectConfig.CHROME_DRIVER.getName() + " | grep -v grep | awk '{print $2}' | xargs kill -9";
                Runtime.getRuntime().exec(cmd);
            }

            // 休眠1秒
            Thread.sleep(1000);
        } catch (java.io.IOException | java.lang.InterruptedException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 点击job.swgdRecv.run
     * */
    public void swgdRecvRun() {
        webDriver.url("http://192.168.120.83:9909/console");
        webDriver.findElementByCssSelector("input[tabindex='1']").sendKey("Testing");
        webDriver.findElementByCssSelector("input[tabindex='2']").sendKey("Testing");
        webDriver.findElementByCssSelector("button[tabindex='3']").click();
        webDriver.await(1000);
        webDriver.findElementByXpath("//span[text()=' Servers']").parent().parent().prev().children(1).click();
        webDriver.findElementByXpath("//span[text()=' node.server']").click();
        webDriver.findElementByXpath("//div[text()='job.swgdRecv']").parent().next().next().next().children(1).children(1).children(1).children(3).children(1).click();
    }

    /**
     * 关闭驱动
     * */
    public void close() {
        this.webDriver.close();
        Log.getLog().info("谷歌驱动已关闭!");
    }

}