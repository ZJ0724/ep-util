package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.annotation.ConfigAnnotation;
import com.easipass.EpUtilServer.config.*;
import com.easipass.EpUtilServer.enumeration.SystemOSEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.InitService;
import com.easipass.EpUtilServer.util.FileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.*;
import java.util.Properties;

@Configuration
public class InitServiceImpl implements InitService {

    @Bean
    @Override
    public void init() {
        // 检查配置文件目录是否存在
        File configDir = new File(ProjectConfig.CONFIG_DIR);
        // 不存在就创建目录
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        // 检查配置文件
        if (!ProjectConfig.CONFIG_FILE.exists()) {
            // 不存在创建默认配置文件
            FileUtil.copyTextFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.CONFIG_PATH), ProjectConfig.CONFIG_FILE);
        }
        // 加载配置文件
        Properties config = new Properties();
        try {
            config.load(new FileReader(ProjectConfig.CONFIG_FILE));
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
        ConfigAnnotation.Method.load(config, SWGDConfig.class);
        ConfigAnnotation.Method.load(config, Sftp83Config.class);
        ConfigAnnotation.Method.load(config, KSDDBConfig.class);

        // 检查谷歌驱动
        // windows
        if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.windows) {
            if (!ProjectConfig.WINDOWS_CHROME_DRIVER.exists()) {
                FileUtil.copyOtherFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.WINDOWS_CHROME_DRIVER_PATH), ProjectConfig.WINDOWS_CHROME_DRIVER);
            }
        } else if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.linux) {
            if (!ProjectConfig.LINUX_CHROME_DRIVER.exists()) {
                FileUtil.copyOtherFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.LINUX_CHROME_DRIVER_PATH), ProjectConfig.LINUX_CHROME_DRIVER);
                try {
                    // 修改权限
                    Runtime.getRuntime().exec("chmod 777 " + ProjectConfig.LINUX_CHROME_DRIVER.getAbsolutePath());
                } catch (IOException e) {
                    throw new ErrorException(e.getMessage());
                }
            }
        } else {
            throw ErrorException.getErrorException("未找到系统类型");
        }
    }

}
