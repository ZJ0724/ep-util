package com.easipass.util.util;

import com.easipass.util.config.BaseConfig;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;

public final class ChromeDriverUtil {

    public static WebDriver getChromeDriver(boolean isShow) {
        return new ChromeWebDriver(BaseConfig.CHROME_DRIVER, isShow);
    }

    public static WebDriver getChromeDriver() {
        return getChromeDriver(false);
    }

}