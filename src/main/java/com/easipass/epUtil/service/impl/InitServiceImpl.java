package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.module.DaKaModule;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.service.InitService;

public class InitServiceImpl implements InitService {

    private final Log log = Log.getLog();

    @Override
    public void configLoad() {
        // 加载配置
        log.info(Config.getConfig().toString());
    }

    @Override
    public void chromeDriverLoad() {
        log.info("检查谷歌驱动...");
        // 检查谷歌驱动
        ChromeDriver.check();
        log.info("驱动正常！");
    }

    @Override
    public void daKaIsStart() {
        DaKaModule daKaModule = DaKaModule.getDaKa();
        if (daKaModule.check()) {
            daKaModule.start();
        }
    }

}