package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.config.*;
import com.easipass.epUtil.config.SystemTypeConfig;
import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.entity.config.KSDDBConfig;
import com.easipass.epUtil.entity.config.SWGDConfig;
import com.easipass.epUtil.entity.config.Sftp83Config;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.service.InitService;
import com.easipass.epUtil.util.FileUtil;
import java.io.*;

public class InitServiceImpl implements InitService {

    @Override
    public void configLoad() {
        Log.info("加载配置文件...");

        // 加载配置
        SWGDConfig.getSWGDConfig().load();
        Log.info(SWGDConfig.getSWGDConfig().toString());

        KSDDBConfig.getKSDDBConfig().load();
        Log.info(KSDDBConfig.getKSDDBConfig().toString());

        Sftp83Config.getSftp83Config().load();
        Log.info(Sftp83Config.getSftp83Config().toString());
    }

    @Override
    public void chromeDriverLoad() {
        Log.info("检查谷歌驱动...");

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
        Log.info("驱动正常！");
    }

}