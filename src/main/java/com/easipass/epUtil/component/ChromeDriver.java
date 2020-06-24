package com.easipass.epUtil.component;

import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.config.SystemTypeConfig;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.config.DaKa;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.service.impl.InitServiceImpl;
import com.easipass.epUtil.util.FileUtil;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.exception.WebElementException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import java.io.IOException;
import java.io.InputStream;

public class ChromeDriver {

    private final WebDriver webDriver;

    public ChromeDriver() {
        try {
            webDriver = new ChromeWebDriver(ProjectConfig.CHROME_DRIVER);
            Log.getLog().info("已打开谷歌驱动!");
        } catch (WebDriverException e) {
            throw ChromeDriverException.chromeDriverFileException();
        }
    }

    /**
     * 检查驱动
     * */
    public static void check() {
        // 驱动文件是否存在，不存在生成默认驱动
        if (!ProjectConfig.CHROME_DRIVER.exists()) {
            InputStream inputStream = InitServiceImpl.class.getResourceAsStream(ProjectConfig.RESOURCE_CHROME_DRIVER);
            FileUtil.copyOtherFile(inputStream, ProjectConfig.CHROME_DRIVER);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw ErrorException.getErrorException(e.getMessage());
            }

            // 如果是linux，修改权限777
            if (ProjectConfig.SYSTEM_TYPE == SystemTypeConfig.linux) {
                try {
                    Runtime.getRuntime().exec("chmod 777 " + ProjectConfig.LINUX_CHROME_DRIVER.getAbsolutePath());
                } catch (IOException e) {
                    throw ErrorException.getErrorException(e.getMessage());
                }
            }
        }

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

    /**
     * 执行打卡
     * */
    public void daKa() {
        Log log = Log.getLog();
        DaKa daKa = Config.getConfig().getDaKa();

        try {
            this.webDriver.url("http://192.168.0.41/index.jsp");
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > input[type=text]").sendKey(daKa.getUsername());
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(6) > td:nth-child(2) > input[type=password]").sendKey(daKa.getPassword());
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(7) > td:nth-child(1) > div > img").click();
            this.webDriver.findElementByCssSelector("#Image1").click();
            this.webDriver.close();
            log.info("打卡完成");
        } catch (WebElementException e) {
            this.close();
            throw ChromeDriverException.daKaException();
        }
    }

}