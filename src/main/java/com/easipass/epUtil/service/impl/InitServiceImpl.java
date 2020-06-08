package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.config.*;
import com.easipass.epUtil.config.SystemTypeConfig;
import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.service.InitService;
import com.easipass.epUtil.util.FileUtil;
import java.io.*;

public class InitServiceImpl implements InitService {

    private final Log log = Log.getLog();

    @Override
    public void configLoad() {
        log.info("加载配置文件...");

        // 加载配置
        Config.getConfig().loadData();
        log.info(Config.getConfig().makeString());
    }

    @Override
    public void chromeDriverLoad() {
        log.info("检查谷歌驱动...");

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

        // 检查谷歌驱动是否能打开
        ChromeDriver.check();
        log.info("驱动正常！");
    }

}