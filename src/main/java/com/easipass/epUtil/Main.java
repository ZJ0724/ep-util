package com.easipass.epUtil;

import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.service.InitService;
import com.easipass.epUtil.service.impl.InitServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Main.class, com.zj0724.springbootUtil.Main.class})
public class Main {

    public static void main(String[] args) {
        try {
            InitService initService = new InitServiceImpl();
            initService.configLoad();
            initService.chromeDriverLoad();
            SpringApplication.run(Main.class, args);
        } catch (ConfigException | ChromeDriverException e) {
            Log.error(e.getMessage());
        }
    }

}