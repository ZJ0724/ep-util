package com.easipass.epUtil.entity.resources.chromeDriver;

import com.easipass.epUtil.entity.Resource;

/**
 * windows谷歌驱动资源
 *
 * @author ZJ
 * */
public class EpUtilChromeDriverWindowsResource extends Resource {

    /**
     * 单例
     * */
    private static final EpUtilChromeDriverWindowsResource EP_UTIL_CHROME_DRIVER_WINDOWS_RESOURCE = new EpUtilChromeDriverWindowsResource();

    /**
     * 构造函数
     */
    private EpUtilChromeDriverWindowsResource() {
        super(CHROME_DRIVER + "epUtilChromeDriver84-windows.exe");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static EpUtilChromeDriverWindowsResource getInstance() {
        return EP_UTIL_CHROME_DRIVER_WINDOWS_RESOURCE;
    }

}