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
            System.out.println();

            // 加载SWGD配置
            SWGDProperties.getInstance();
            System.out.println();

            // 加载sftp83配置
            Sftp83Properties.getInstance();
            System.out.println();

//            // 开启谷歌驱动池
//            ChromeDriver.openChromeDriverPool();
//            System.out.println();

            // 检查是否开启自动打卡
            DaKa.getInstance();
            System.out.println();

            SpringApplication.run(Main.class, args);
            System.out.println();

            LOG.info("--- < 完成 > ---" + "\n");
        } catch (BaseException e) {
            LOG.error(e.getMessage());
        }
    }

}