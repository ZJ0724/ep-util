package com.easipass.util;

import com.easipass.util.entity.Port;
import org.springframework.beans.BeansException;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class Main implements ApplicationContextAware {

    public static ApplicationContext APPLICATION_CONTEXT;

    public static void main(String[] args) {
        System.out.println("                        _   _ _ \n" +
                "  ___ _ __        _   _| |_(_) |\n" +
                " / _ \\ '_ \\ _____| | | | __| | |\n" +
                "|  __/ |_) |_____| |_| | |_| | |\n" +
                " \\___| .__/       \\__,_|\\__|_|_|\n" +
                "     |_|                        ");
        System.setProperty("server.port", Port.getInstance().getPort() + "");
        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);

        after();
    }

    private static void after() {
//        System.out.println(new ConfigServiceImpl().getByCode(ConfigPO.Code.SWGD_DATABASE_IP));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

}