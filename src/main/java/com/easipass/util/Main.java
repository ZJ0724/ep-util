package com.easipass.util;

import com.easipass.util.core.*;
import com.easipass.util.core.config.Port;
import com.easipass.util.core.exception.ErrorException;
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

    public static void main(String[] args) {
        // 指定配置文件
        System.setProperty("spring.config.location", Resource.APPLICATION_PROPERTIES.getPath());

        // 指定端口
        System.setProperty("server.port", Port.getInstance().getPort() + "");

        SpringApplication.run(Main.class, args);

        // 检查项目
        if (Project.VERSION == null || Project.SYSTEM_TYPE == null) {
            throw new ErrorException("项目启动错误");
        }

        // 检查是否开启自动打卡
        DaKa.getInstance();

        log.info("--- < 完成 > ---");
    }

}