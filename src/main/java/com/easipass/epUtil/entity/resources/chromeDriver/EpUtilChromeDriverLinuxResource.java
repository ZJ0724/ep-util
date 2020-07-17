package com.easipass.epUtil.entity.resources.chromeDriver;

import com.easipass.epUtil.entity.Resource;

/**
 * linux谷歌驱动资源
 *
 * @author ZJ
 * */
public class EpUtilChromeDriverLinuxResource extends Resource {

    /**
     * 单例
     * */
    private static final EpUtilChromeDriverLinuxResource EP_UTIL_CHROME_DRIVER_LINUX_RESOURCE = new EpUtilChromeDriverLinuxResource();

    /**
     * 构造函数
     */
    private EpUtilChromeDriverLinuxResource() {
        super(CHROME_DRIVER + "epUtilChromeDriver-linux");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static EpUtilChromeDriverLinuxResource getInstance() {
        return EP_UTIL_CHROME_DRIVER_LINUX_RESOURCE;
    }

}