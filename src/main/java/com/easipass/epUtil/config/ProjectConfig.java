package com.easipass.epUtil.config;

import java.io.File;

public class ProjectConfig {

    /**
     * 项目名
     */
    public final static String PROJECT_NAME = "epUtil";

    /**
     * 配置文件目录
     * */
    public final static String CONFIG_DIR = System.getProperty("user.home") + "/." + PROJECT_NAME;

    /**
     * 配置文件
     * */
    public final static File CONFIG_FILE = new File(CONFIG_DIR, "config");

    /**
     * 系统类型
     * */
    public final static SystemTypeConfig SYSTEM_TYPE =
            System.getProperty("os.name").contains("Windows") ?
                    SystemTypeConfig.windows :
                    (System.getProperty("os.name").contains("Linux") ?
                            SystemTypeConfig.linux :
                            null);

    /**
     * 谷歌驱动windows版
     * */
    public final static File WINDOWS_CHROME_DRIVER = new File(CONFIG_DIR, "epUtilChromeDriver.exe");

    /**
     * 谷歌驱动linux版文件名
     * */
    public final static File LINUX_CHROME_DRIVER = new File(CONFIG_DIR, "epUtilChromeDriver");

    /**
     * 当前使用的驱动
     * */
    public final static File CHROME_DRIVER = SYSTEM_TYPE == SystemTypeConfig.windows ? WINDOWS_CHROME_DRIVER : LINUX_CHROME_DRIVER;

    /**
     * 当前使用的默认驱动
     * */
    public final static String RESOURCE_CHROME_DRIVER = SYSTEM_TYPE == SystemTypeConfig.windows ? ResourcePathConfig.WINDOWS_CHROME_DRIVER_PATH : ResourcePathConfig.LINUX_CHROME_DRIVER_PATH;

    /**
     * 日志输出文件
     * */
    public final static File LOG_FILE = new File(System.getProperty("user.dir"), "/log/info.log");

}