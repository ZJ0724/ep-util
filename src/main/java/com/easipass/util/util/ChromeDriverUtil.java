package com.easipass.util.util;

import com.easipass.util.Main;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.StringUtil;
import com.zj0724.uiAuto.DriverType;
import com.zj0724.uiAuto.Selector;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import com.zj0724.uiAuto.webDriver.RemoteWebDriver;

public final class ChromeDriverUtil {

    public static WebDriver getChromeDriver() {
        ConfigService configService = Main.APPLICATION_CONTEXT.getBean(ConfigService.class);

        ConfigPO configPO = configService.getByCode(ConfigPO.Code.SELENIUM_SERVER);
        if (configPO == null) {
            throw new InfoException("selenium服务地址未配置");
        }
        String seleniumServer = configPO.getData();
        if (StringUtil.isEmpty(seleniumServer)) {
            throw new InfoException("selenium服务地址未配置");
        }

        boolean isShow = false;
        ConfigPO byCode = configService.getByCode(ConfigPO.Code.SELENIUM_IS_SHOW);
        if (byCode != null) {
            isShow = "1".equals(byCode.getData());
        }

        if (seleniumServer.startsWith("http")) {
            return new RemoteWebDriver(seleniumServer, DriverType.CHROME, isShow);
        } else {
            return new ChromeWebDriver(seleniumServer, isShow);
        }
    }

    /**
     * 点击RecvRun
     *
     * @param webDriver webDriver
     * */
    public static void swgdRecvRun(WebDriver webDriver) {
        try {
            webDriver.open("http://192.168.120.83:9909/console");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(3) > td > button")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(1) > table > tbody > tr > td > div > div:nth-child(2) > div > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > img")).click();
            webDriver.findElement(Selector.byCssSelector("#gwt-uid-26 > span")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(5) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button")).click();
        } catch (Exception e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * 点击job.swgdAgentRecvNew
     *
     * @param webDriver webDriver
     * */
    public static void agentRun(WebDriver webDriver) {
        try {
            webDriver.open("http://192.168.120.83:9909/console");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > input")).sendKey("admin");
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr:nth-child(3) > td > button")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(1) > table > tbody > tr > td > div > div:nth-child(2) > div > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > img")).click();
            webDriver.findElement(Selector.byCssSelector("#gwt-uid-26 > span")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > img")).click();
            webDriver.findElement(Selector.byCssSelector("body > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > div > div > div:nth-child(3) > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > div > div > table > tbody > tr:nth-child(2) > td > div > div:nth-child(1) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(20) > td:nth-child(4) > table > tbody > tr > td:nth-child(3) > button")).click();
        } catch (Exception e) {
            throw new InfoException(e.getMessage());
        }
    }

}