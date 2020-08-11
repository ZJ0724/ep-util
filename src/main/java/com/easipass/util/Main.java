package com.easipass.util;

import com.easipass.util.entity.ChromeDriver;
import com.easipass.util.entity.DaKa;
import com.easipass.util.entity.Log;
import com.easipass.util.entity.Project;
import com.easipass.util.entity.config.DaKaProperties;
import com.easipass.util.entity.config.SWGDProperties;
import com.easipass.util.entity.config.Sftp83Properties;
import com.easipass.util.exception.BaseException;
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
    private static final Log LOG = Log.getLog();

    /**
     * 项目
     * */
    private static final Project PROJECT = Project.getInstance();

    /**
     * main
     *
     * @param args args
     * */
    public static void main(String[] args) {
        // 指定配置文件
        System.setProperty("spring.config.location", PROJECT.getApplicationPropertiesPath());

        try {
            LOG.info("--- < 启动 > ---" + "\n");

            // 项目根目录
            LOG.info("项目根目录: " + PROJECT.getProjectRootPath() + "\n");

            // version
            LOG.info("version: " + PROJECT.getVersion() + "\n");

            // 加载daKa配置
            DaKaProperties.getInstance();
            LOG.info("\n");

            // 加载SWGD配置
            SWGDProperties.getInstance();
            LOG.info("\n");

            // 加载sftp83配置
            Sftp83Properties.getInstance();
            LOG.info("\n");

            // 校验谷歌浏览器
            ChromeDriver.get().close();
            LOG.info("\n");

            // 检查是否开启自动打卡
            DaKa.getInstance();
            LOG.info("\n");

            SpringApplication.run(Main.class, args);
            LOG.info("\n");

            LOG.info("--- < 完成 > ---" + "\n");
        } catch (BaseException e) {
            LOG.error(e.getMessage());
        }
    }

}