package com.easipass.epUtil.core.entity;

import com.easipass.epUtil.core.entity.config.DaKaProperties;
import com.easipass.epUtil.core.entity.resources.chromeDriver.EpUtilChromeDriverLinuxResource;
import com.easipass.epUtil.core.entity.resources.chromeDriver.EpUtilChromeDriverWindowsResource;
import com.easipass.epUtil.core.exception.ChromeDriverException;
import com.easipass.epUtil.core.exception.ErrorException;
import com.easipass.epUtil.core.util.FileUtil;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
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
     * 驱动文件存放路径
     * */
    private static final String ROOT_PATH = Project.getInstance().getConfigPath() + "chromeDriver/";

    /**
     * 日志
     * */
    private static final Log LOG = Log.getLog();

    /**
     * 构造函数
     * */
    public ChromeDriver() {
        LOG.info("检查谷歌驱动");

        Resource resource;
        SystemType systemType = Project.getInstance().getSystemType();

        if (systemType == SystemType.WINDOWS) {
            resource = EpUtilChromeDriverWindowsResource.getInstance();
        } else if (systemType == SystemType.LINUX) {
            resource = EpUtilChromeDriverLinuxResource.getInstance();
        } else {
            throw new ErrorException("未找到指定系统类型的驱动文件");
        }

        File file = new File(ROOT_PATH, resource.getName());

        FileUtil.createFile(file, resource.getInputStream());
        resource.closeInputStream();

        try {
            webDriver = new ChromeWebDriver(file);
        } catch (WebDriverException e) {
            throw new ChromeDriverException(e.getMessage());
        }

        LOG.info("谷歌驱动已打开");
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
        LOG.info("谷歌驱动已关闭");
    }

    /**
     * 执行打卡
     * */
    public void daKa() {
        DaKaProperties daKa = DaKaProperties.getInstance();

        this.webDriver.url("http://192.168.0.41/index.jsp");
        this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > input[type=text]").sendKey(daKa.getUsername());
        this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(6) > td:nth-child(2) > input[type=password]").sendKey(daKa.getPassword());
        this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(7) > td:nth-child(1) > div > img").click();
        this.webDriver.findElementByCssSelector("#Image1").click();
    }

}