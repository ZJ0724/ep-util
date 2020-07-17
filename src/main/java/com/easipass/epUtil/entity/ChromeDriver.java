package com.easipass.epUtil.entity;

import com.easipass.epUtil.entity.config.DaKaProperties;
import com.easipass.epUtil.entity.resources.chromeDriver.EpUtilChromeDriverLinuxResource;
import com.easipass.epUtil.entity.resources.chromeDriver.EpUtilChromeDriverWindowsResource;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.util.ConsoleUtil;
import com.easipass.epUtil.util.FileUtil;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 谷歌驱动
 *
 * @author ZJ
 * */
public final class ChromeDriver {

    /**
     * 驱动
     * */
    private WebDriver webDriver;

    /**
     * 驱动文件存放路径
     * */
    private static final String ROOT_PATH = Project.getInstance().getConfigPath() + "chromeDriver/";

    /**
     * 日志
     * */
    private static final Log LOG = Log.getLog();

    /**
     * 谷歌驱动池
     * */
    private static final List<ChromeDriver> CHROME_DRIVER_POOL = new ArrayList<>();

    /**
     * 谷歌驱动池最大数量
     * */
    private static final int CHROME_DRIVER_POOL_SIZE = 2;

    /**
     * 谷歌驱动资源
     * */
    private static Resource resource;

    static {
        SystemType systemType = Project.getInstance().getSystemType();

        if (systemType == SystemType.WINDOWS) {
            resource = EpUtilChromeDriverWindowsResource.getInstance();
        }

        if (systemType == SystemType.LINUX) {
            resource = EpUtilChromeDriverLinuxResource.getInstance();
        }
    }

    /**
     * 谷歌驱动池是否以打开
     * */
    private static boolean isOpenChromeDriverPool = false;

    /**
     * 构造函数
     * */
    private ChromeDriver() {
        LOG.info("检查谷歌驱动");

        File file = new File(ROOT_PATH, resource.getName());

        FileUtil.createFile(file, resource.getInputStream());
        resource.closeInputStream();

        try {
            this.webDriver = new ChromeWebDriver(file);
        } catch (WebDriverException e) {
            throw new ChromeDriverException(e.getMessage());
        }

        LOG.info("谷歌驱动已打开");
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
        } catch (org.openqa.selenium.WebDriverException e) {
            this.webDriver = null;
            throw new ChromeDriverException("驱动异常，请不要手动杀掉驱动进程！");
        }
    }

    /**
     * 关闭驱动
     * */
    public void close() {
        if (this.webDriver != null) {
            this.webDriver.close();
            LOG.info("谷歌驱动已关闭");
        }
    }

    /**
     * 执行打卡
     * */
    public void daKa() {
        DaKaProperties daKa = DaKaProperties.getInstance();

        try {
            this.webDriver.url("http://192.168.0.41/index.jsp");
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(5) > td:nth-child(2) > input[type=text]").sendKey(daKa.getUsername());
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(6) > td:nth-child(2) > input[type=password]").sendKey(daKa.getPassword());
            this.webDriver.findElementByCssSelector("body > table.flash > tbody > tr > td:nth-child(1) > div > table:nth-child(1) > tbody > tr:nth-child(7) > td:nth-child(1) > div > img").click();
            this.webDriver.findElementByCssSelector("#Image1").click();
        } catch (org.openqa.selenium.WebDriverException e) {
            this.webDriver = null;
            throw new ChromeDriverException("驱动异常，请不要手动杀掉驱动进程！");
        }
    }

    /**
     * 获取一个谷歌驱动
     *
     * @return 一个谷歌驱动
     * */
    public synchronized static ChromeDriver getChromeDriver() {
        // 如果谷歌驱动池的数量为0，则返回一个新的驱动
        if (CHROME_DRIVER_POOL.size() == 0) {
            LOG.info("驱动池没有驱动，返回新的驱动");
            return new ChromeDriver();
        }

        // 返回的驱动
        ChromeDriver chromeDriver = CHROME_DRIVER_POOL.get(0);

        // 将此驱动从池中移除
        CHROME_DRIVER_POOL.remove(0);

        return chromeDriver;
    }

    /**
     * 开启谷歌驱动池功能
     * */
    public static void openChromeDriverPool() {
        if (isOpenChromeDriverPool) {
            return;
        }

        LOG.info("打开谷歌驱动池");
        isOpenChromeDriverPool = true;

        new Thread(() -> {
            CHROME_DRIVER_POOL.clear();
            ConsoleUtil.kill(resource.getName());

            while (true) {
                if (CHROME_DRIVER_POOL.size() == CHROME_DRIVER_POOL_SIZE) {
                    continue;
                }

                LOG.info("添加谷歌驱动");
                CHROME_DRIVER_POOL.add(new ChromeDriver());
            }
        }).start();
    }

}