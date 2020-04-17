package com.easipass.EpUtilServer;

import com.easipass.EpUtilServer.service.InitService;
import com.easipass.EpUtilServer.service.impl.InitServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // 初始化
        InitService initService = new InitServiceImpl();
        initService.loadConfigFile();
        initService.checkChromeDriver();

        // 启动springboot
        SpringApplication.run(Main.class, args);
    }

}
