package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.config.*;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.service.InitService;
import com.easipass.EpUtilServer.util.ConfigUtil;
import com.easipass.EpUtilServer.util.FileUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.*;
import java.util.Properties;

@Configuration
public class InitServiceImpl implements InitService {

    @Bean
    @Override
    public void loadConfigFile() {
        // 检查配置文件目录是否存在
        File configDir = new File(BaseConfig.CONFIG_DIR);
        // 不存在就创建目录
        if (!configDir.exists()) {
            if (!configDir.mkdirs()) {
                throw new ErrorException("创建配置文件目录出错");
            }
        }

        // 检查配置文件是否存在
        File configFile = new File(configDir.getAbsolutePath(), BaseConfig.CONFIG_FILE_NAME);
        if (!configFile.exists()) {
            // 不存在创建默认配置文件
            FileUtil.copyTextFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.CONFIG_PATH), BaseConfig.CONFIG_DIR, BaseConfig.CONFIG_FILE_NAME);
        }

        // 加载配置文件
        Properties config = new Properties();
        try {
            config.load(InitServiceImpl.class.getResourceAsStream("/" + BaseConfig.CONFIG_FILE_NAME));
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
        ConfigUtil.load(config, SWGDConfig.class);
        ConfigUtil.load(config, Sftp83Config.class);
        ConfigUtil.load(config, KSDDBConfig.class);
    }

    @Bean
    @Override
    public void checkChromeDriver() {
        // windows
        if (BaseConfig.SYSTEM_TYPE.contains("Windows")) {
            File chromeFile = new File(BaseConfig.CONFIG_DIR, BaseConfig.WINDOWS_CHROME_DRIVER_NAME);
            if (!chromeFile.exists()) {
                FileUtil.copyOtherFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.WINDOWS_CHROME_DRIVER_PATH), BaseConfig.CONFIG_DIR, BaseConfig.WINDOWS_CHROME_DRIVER_NAME);
            }
        }

        // linux
        if (BaseConfig.SYSTEM_TYPE.contains("Linux")) {
            File chromeFile = new File(BaseConfig.CONFIG_DIR, BaseConfig.LINUX_CHROME_DRIVER_NAME);
            if (!chromeFile.exists()) {
                FileUtil.copyOtherFile(InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.LINUX_CHROME_DRIVER_PATH), BaseConfig.CONFIG_DIR, BaseConfig.LINUX_CHROME_DRIVER_NAME);
                try {
                    // 修改权限
                    Runtime.getRuntime().exec("chmod 777 " + chromeFile.getAbsolutePath());
                } catch (IOException e) {
                    throw new ErrorException(e.getMessage());
                }
            }

            // 创建软连接
            File chromeFileLnk = new File(BaseConfig.LINUX_CHROME_DRIVER_LNK_PATH, BaseConfig.LINUX_CHROME_DRIVER_NAME);
            if (chromeFileLnk.exists()) {
                if (!chromeFileLnk.delete()) {
                    throw new ErrorException("删除软连接失败");
                }
            }
            try {
                Runtime.getRuntime().exec("ln -s " + chromeFile.getAbsolutePath() + " " + chromeFileLnk.getAbsolutePath());
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }
    }

}
