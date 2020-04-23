package com.easipass.EpUtilServer.util;

import com.easipass.EpUtilServer.config.projectConfig;
import com.zj0724.StepWebDriver.entity.StepWebDriver;
import java.io.File;

public class StepWebDriverUtil {

    /**
     * 获取一个stepWebDriver
     * */
    public static StepWebDriver getStepWebDriver() {
        return new StepWebDriver(new File(projectConfig.CONFIG_DIR, projectConfig.WINDOWS_CHROME_DRIVER_NAME));
    }

}
