package com.easipass.epUtil;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.DaKa;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.entity.Project;
import com.easipass.epUtil.entity.config.DaKaProperties;
import com.easipass.epUtil.entity.config.SWGDProperties;
import com.easipass.epUtil.entity.config.Sftp83Properties;
import com.easipass.epUtil.exception.BaseException;
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
     * main
     *
     * @param args args
     * */
    public static void main(String[] args) {
        try {
            LOG.info("--- < 启动 > ---" + "\n");

            // version
            LOG.info("version: " + Project.getInstance().getVersion() + "\n");

            // 加载daKa配置
            DaKaProperties.getInstance();
            LOG.info("\n");

            // 加载SWGD配置
            SWGDProperties.getInstance();
            LOG.info("\n");

            // 加载sftp83配置
            Sftp83Properties.getInstance();
            LOG.info("\n");

            // 检查谷歌驱动
            ChromeDriver.getChromeDriver().close();
//            // 开启谷歌驱动池
//            ChromeDriver.openChromeDriverPool();
//            LOG.info("\n");

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