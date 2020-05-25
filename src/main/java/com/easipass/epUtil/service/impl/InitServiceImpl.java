package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.annotation.ConfigAnnotation;
import com.easipass.epUtil.common.ProcessCommon;
import com.easipass.epUtil.config.*;
import com.easipass.epUtil.enumeration.SystemOSEnum;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.service.InitService;
import com.easipass.epUtil.util.FileUtil;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.exception.WebDriverException;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import java.io.*;
import java.util.Properties;

@Configuration
public class InitServiceImpl implements InitService {

    @Override
    @Bean
    @Order(1)
    public void configLoad() {
        // 检查配置文件
        if (!ProjectConfig.CONFIG_FILE.exists()) {
            // 不存在创建默认配置文件
            InputStream inputStream = InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.CONFIG_PATH);
            FileUtil.copyTextFile(inputStream, ProjectConfig.CONFIG_FILE);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ErrorException(e.getMessage());
            }
        }

        // 加载配置文件
        Properties config = new Properties();
        try {
            FileReader fileReader = new FileReader(ProjectConfig.CONFIG_FILE);
            config.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        // 自动装配配置文件，如果装配失败，将配置文件删除，重新生成
        if (!ConfigAnnotation.Method.load(config, SWGDConfig.class) ||
                !ConfigAnnotation.Method.load(config, Sftp83Config.class) ||
                !ConfigAnnotation.Method.load(config, KSDDBConfig.class)) {
            if (!ProjectConfig.CONFIG_FILE.delete()) {
                throw new ErrorException("删除config失败");
            }
            configLoad();
        }
    }

    @Override
    @Bean
    @Order(2)
    public void chromeDriverLoad() {
        // 检查谷歌驱动
        // windows
        if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.windows) {
            if (!ProjectConfig.WINDOWS_CHROME_DRIVER.exists()) {
                InputStream inputStream = InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.WINDOWS_CHROME_DRIVER_PATH);
                FileUtil.copyOtherFile(inputStream, ProjectConfig.WINDOWS_CHROME_DRIVER);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ErrorException(e.getMessage());
                }
            }
        } else if (ProjectConfig.SYSTEM_OS_ENUM == SystemOSEnum.linux) {
            if (!ProjectConfig.LINUX_CHROME_DRIVER.exists()) {
                InputStream inputStream = InitServiceImpl.class.getResourceAsStream(ResourcePathConfig.LINUX_CHROME_DRIVER_PATH);
                FileUtil.copyOtherFile(inputStream, ProjectConfig.LINUX_CHROME_DRIVER);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ErrorException(e.getMessage());
                }
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

        // 检查驱动是否能打开，不能打开删除文件重新生成
        try {
            WebDriver webDriver = new ChromeWebDriver(ProjectConfig.CHROME_DRIVER);
            webDriver.close();
        } catch (WebDriverException e) {
            System.out.println("谷歌驱动失效，正在重新生成...");

            // 杀掉进程
            ProcessCommon.killChromeDriver();

            // 删除文件
            if (!ProjectConfig.CHROME_DRIVER.delete()) {
                throw new ErrorException("删除谷歌驱动失败");
            }

            // 重新加载驱动文件
            chromeDriverLoad();
        }
    }

}
