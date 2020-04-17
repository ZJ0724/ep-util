package com.easipass.EpUtilServer.util;

import com.easipass.EpUtilServer.config.BaseConfig;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import java.io.File;

public class StepWebDriverUtil {

    /**
     * 获取一个stepWebDriver
     * */
    public static StepWebDriver getStepWebDriver() {
        return new StepWebDriver(new File(BaseConfig.CONFIG_DIR, BaseConfig.WINDOWS_CHROME_DRIVER_NAME));
    }

}
