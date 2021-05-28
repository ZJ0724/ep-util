package com.easipass.util.util;

import com.easipass.util.Main;
import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.exception.InfoException;
import com.zj0724.uiAuto.DriverType;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.RemoteWebDriver;

public final class ChromeDriverUtil {

    public static WebDriver getChromeDriver() {
        ConfigService configService = Main.APPLICATION_CONTEXT.getBean(ConfigService.class);
        ConfigPO configPO = configService.getByCode(ConfigPO.Code.SELENIUM_SERVER);
        if (configPO == null) {
            throw new InfoException("selenium服务地址未配置");
        }

        return new RemoteWebDriver(configPO.getData(), DriverType.CHROME);
    }

}