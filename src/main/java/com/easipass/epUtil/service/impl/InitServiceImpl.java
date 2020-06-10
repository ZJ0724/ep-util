package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.module.ChromeDriver;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.module.Log;
import com.easipass.epUtil.service.InitService;

public class InitServiceImpl implements InitService {

    private final Log log = Log.getLog();

    @Override
    public void configLoad() {
        log.info("加载配置文件...");

        // 加载配置
        Config.getConfig().loadData();
        log.info(Config.getConfig().toString());
    }

    @Override
    public void chromeDriverLoad() {
        log.info("检查谷歌驱动...");
        // 检查谷歌驱动
        ChromeDriver.check();
        log.info("驱动正常！");
    }

}