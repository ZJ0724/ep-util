package com.easipass.epUtil;

import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.entity.Version;
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

    /** 日志 */
    private final static Log LOG = Log.getLog();

    /** 版本 */
    private final static Version VERSION = Version.getVersionInstance();

    public static void main(String[] args) {
        try {
            LOG.info("---------- < 正在启动 > ----------");
            LOG.info("version:" + VERSION.getVersion());
            InitService initService = new InitServiceImpl();
            initService.configLoad();
            initService.chromeDriverLoad();
            initService.daKaIsStart();
            SpringApplication.run(Main.class, args);
            LOG.info("---------- < 已启动 > ----------");
        } catch (ConfigException | ChromeDriverException e) {
            LOG.error(e.getMessage());
        }
    }

}