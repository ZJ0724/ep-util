package com.easipass.epUtil;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Log;
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
     * */
    public static void main(String[] args) {
        try {
            LOG.info("--- < 启动 > ---");

            DaKaProperties.getInstance();
            SWGDProperties.getInstance();
            Sftp83Properties.getInstance();

            // 开启谷歌驱动池
//            ChromeDriver.openChromeDriverPool();

            SpringApplication.run(Main.class, args);

            LOG.info("--- < 完成 > ---");
        } catch (BaseException e) {
            LOG.error(e.getMessage());
        }
    }

}