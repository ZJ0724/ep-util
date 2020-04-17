package com.easipass.EpUtilServer.config;

public class BaseConfig {

    /**
     * 项目名
     */
    public final static String PROJECT_NAME = "EpUtilServer";

    /**
     * 配置文件目录
     * */
    public final static String CONFIG_DIR = System.getProperty("user.home") + "/." + PROJECT_NAME;

    /**
     * 配置文件名
     * */
    public final static String CONFIG_FILE_NAME = "config";

    /**
     * 系统类型
     * */
    public final static String SYSTEM_TYPE = System.getProperty("os.name");

    /**
     * 谷歌驱动windows版文件名
     * */
    public final static String WINDOWS_CHROME_DRIVER_NAME = "chromedriver.exe";

    /**
     * 谷歌驱动linux版文件名
     * */
    public final static String LINUX_CHROME_DRIVER_NAME = "chromedriver";

    /**
     * linux谷歌驱动软连接路径
     * */
    public final static String LINUX_CHROME_DRIVER_LNK_PATH = "/usr/bin";

}
