package com.easipass.epUtil;

import com.easipass.epUtil.core.entity.ChromeDriver;
import com.easipass.epUtil.core.entity.Log;
import com.easipass.epUtil.core.entity.config.DaKaProperties;
import com.easipass.epUtil.core.entity.config.SWGDProperties;
import com.easipass.epUtil.core.entity.config.Sftp83Properties;
import com.easipass.epUtil.core.exception.BaseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main
 *
 * @author ZJ
 * */
@SpringBootApplication
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

            new ChromeDriver().close();

            SpringApplication.run(Main.class, args);

            LOG.info("--- < 完成 > ---");
        } catch (BaseException e) {
            LOG.error(e.getMessage());
        }
    }

}