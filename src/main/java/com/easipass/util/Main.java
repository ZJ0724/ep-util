package com.easipass.util;

import com.easipass.util.core.*;
import com.easipass.util.core.config.Port;
import com.easipass.util.core.exception.ErrorException;
import com.zj0724.util.springboot.ParameterCheck;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * main
 *
 * @author ZJ
 * */
@SpringBootApplication
@Import({ParameterCheck.class})
public class Main {

    public static void main(String[] args) {
        System.out.println("                        _   _ _ \n" +
                "  ___ _ __        _   _| |_(_) |\n" +
                " / _ \\ '_ \\ _____| | | | __| | |\n" +
                "|  __/ |_) |_____| |_| | |_| | |\n" +
                " \\___| .__/       \\__,_|\\__|_|_|\n" +
                "     |_|                        ");

        // 检查项目
        if (Project.VERSION == null || Project.SYSTEM_TYPE == null) {
            throw new ErrorException("项目启动错误");
        }

        // 指定配置文件
        System.setProperty("spring.config.location", Resource.APPLICATION_PROPERTIES.getPath());

        // 指定端口
        System.setProperty("server.port", Port.getInstance().getPort() + "");

        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

}