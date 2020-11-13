package com.easipass.util.core;

import com.easipass.util.core.config.DaKaConfig;
import com.easipass.util.core.config.Project;
import com.easipass.util.core.util.ConsoleUtil;
import com.easipass.util.core.exception.ErrorException;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.BaseException;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * 谷歌驱动
 *
 * @author ZJ
 * */
public final class ChromeDriver {

    /**
     * 驱动
     * */
    private final WebDriver webDriver;

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(ChromeDriver.class);

    /**
     * 驱动资源
     * */
    private static Resource resource;

    static {
        SystemType systemType = Project.SYSTEM_TYPE;

        if (systemType == SystemType.WINDOWS) {
            resource = Resource.CHROME_DRIVER_WINDOWS;
        }

        if (systemType == SystemType.LINUX) {
            resource = Resource.CHROME_DRIVER_LINUX;

            // 设置777权限
            ConsoleUtil.setChmod777(resource.getPath());
        }
    }

    /**
     * 构造函数
     *
     * @param isShow 是否显示浏览器
     * */
    public ChromeDriver(boolean isShow) {
        File file = new File(resource.getPath());

        if (!file.exists()) {
            throw new ErrorException("谷歌驱动文件丢失");
        }

        try {
            this.webDriver = new ChromeWebDriver(file, !isShow);
        } catch (WebDriverException e) {
            throw new ErrorException(e.getMessage());
        }

        log.info("打开谷歌驱动");
    }

    /**
     * 构造函数
     * */
    public ChromeDriver() {
        this(false);
    }

    /**
     * 点击job.swgdRecv.run
     * */
    public void swgdRecvRun() {
        try {
            this.webDriver.url("http://192.168.120.83:9909/console");
            this.webDriver.findElementByCssSelector("input[tabindex='1']").sendKey("Testing");
            this.webDriver.findElementByCssSelector("input[tabindex='2']").sendKey("Testing");
            this.webDriver.findElementByCssSelector("button[tabindex='3']").click();
            this.webDriver.await(1000);
            this.webDriver.findElementByXpath("//span[text()=' Servers']").parent().parent().prev().children(1).click();
            this.webDriver.findElementByXpath("//span[text()=' node.server']").click();
            this.webDriver.findElementByXpath("//div[text()='job.swgdRecv']").parent().next().next().next().children(1).children(1).children(1).children(3).children(1).click();
        } catch (org.openqa.selenium.WebDriverException | BaseException e) {
            throw new BaseException(e.getMessage()) {};
        }
    }

    /**
     * 关闭驱动
     * */
    public void close() {
        if (this.webDriver != null) {
            this.webDriver.close();
            log.info("谷歌驱动已关闭");
        }
    }

    /**
     * 执行打卡
     * */
    public void daKa() {
        DaKaConfig daKaConfig = DaKaConfig.getInstance();

        try {
            this.webDriver.url("http://192.168.0.41/index.jsp");
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > input[type=text]").sendKey(daKaConfig.username);
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(6) > td:nth-child(2) > input[type=password]").sendKey(daKaConfig.password);
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(7) > td:nth-child(1) > div > img").click();
            this.webDriver.findElementByCssSelector("#Image1").click();
        } catch (org.openqa.selenium.WebDriverException | BaseException e) {
            throw new com.easipass.util.core.BaseException(e.getMessage()) {};
        }
    }

    /**
     * 获取驱动
     *
     * @return 驱动
     * */
    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    /**
     * 关闭进程
     * */
    public static void kill() {
        if (Project.SYSTEM_TYPE == SystemType.LINUX) {
            ConsoleUtil.kill("/opt/google/chrome/chrome");
        }

        if (Project.SYSTEM_TYPE == SystemType.WINDOWS) {
            ConsoleUtil.kill("chrome.exe");
        }

        ConsoleUtil.kill(new File(resource.getPath()).getName());
    }

    /**
     * 执行新代理委托回执回收
     * */
    public static void swgdAgentRecvNew() {
        ChromeDriver chromeDriver = null;

        try {
            chromeDriver = new ChromeDriver();
            WebDriver webDriver = chromeDriver.getWebDriver();
            webDriver.url("http://192.168.120.83:9909/console");
            webDriver.findElementByCssSelector("input[tabindex='1']").sendKey("admin");
            webDriver.findElementByCssSelector("input[tabindex='2']").sendKey("admin");
            webDriver.findElementByCssSelector("button[tabindex='3']").click();
            webDriver.await(1000);
            webDriver.findElementByXpath("//span[text()=' Servers']").parent().parent().prev().children(1).click();
            webDriver.findElementByXpath("//span[text()=' node.server']").click();
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > img").click();
            webDriver.findElementByCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(20) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button").click();
        } catch (Throwable e) {
            throw new ErrorException(e.getMessage());
        } finally {
            if (chromeDriver != null) {
                chromeDriver.close();
            }
        }
    }

}