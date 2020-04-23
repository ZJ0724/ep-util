package com.easipass.EpUtilServer.config;

import com.easipass.EpUtilServer.enumeration.SystemOSEnum;

import java.io.File;

public class projectConfig {

    /**
     * 项目名
     */
    public final static String PROJECT_NAME = "EpUtilServer";

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
    public final static SystemOSEnum SYSTEM_OS_ENUM =
            System.getProperty("os.name").contains("Windows") ?
                    SystemOSEnum.windows :
                    (System.getProperty("os.name").contains("Linux") ?
                            SystemOSEnum.linux :
                            null);

    /**
     * 谷歌驱动windows版
     * */
    public final static File WINDOWS_CHROME_DRIVER = new File(CONFIG_DIR, "chromedriver.exe");

    /**
     * 谷歌驱动linux版文件名
     * */
    public final static File LINUX_CHROME_DRIVER = new File(CONFIG_DIR, "chromedriver");

}
