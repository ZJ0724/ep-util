package com.easipass.util.util;

import com.easipass.util.config.BaseConfig;
import com.zj0724.uiAuto.DriverType;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.RemoteWebDriver;

public final class ChromeDriverUtil {

    public static WebDriver getChromeDriver() {
        return new RemoteWebDriver(BaseConfig.SELENIUM_SERVER, DriverType.CHROME);
    }

}