package com.easipass.util;

import com.easipass.util.core.ChromeDriver;
import com.easipass.util.core.DaKa;
import com.easipass.util.core.Project;
import com.easipass.util.core.Config.DaKaProperties;
import com.easipass.util.core.Config.SWGDDatabaseProperties;
import com.easipass.util.core.Config.Sftp83Properties;
import com.easipass.util.core.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * main
 *
 * @author ZJ
 * */
@SpringBootApplication
@ComponentScan(basePackageClasses = {Main.class, com.zj0724.springbootUtil.Main.class})
public class Main {

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * 项目
     * */
    private static final Project PROJECT = Project.getInstance();

    public static void main(String[] args) {
        // 指定配置文件
        System.setProperty("spring.config.location", PROJECT.getApplicationPropertiesPath());

        SpringApplication.run(Main.class, args);

        try {
            // 项目根目录
            log.info("项目根目录: {}", PROJECT.getProjectRootPath());

            // version
            log.info("version: {}", PROJECT.getVersion());

            // 加载daKa配置
            DaKaProperties.getInstance();

            // 加载SWGD配置
            SWGDDatabaseProperties.getInstance();

            // 加载sftp83配置
            Sftp83Properties.getInstance();

            // 校验谷歌浏览器
            ChromeDriver.get().close();

            // 检查是否开启自动打卡
            DaKa.getInstance();

            log.info("--- < 完成 > ---");
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
        }
    }

}