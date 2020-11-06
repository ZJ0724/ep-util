package com.easipass.util.core.config;

/**
 * SWGDPARA数据库配置
 *
 * @author ZJ
 * */
public final class SWGDPARAConfig {

    /**
     * 环境
     * */
    public static final String ENV = "";

    /**
     * 驱动类
     * */
    public static final String CLASS_NAME;


    /**
     * 地址
     * */
    public static final String URL;

    /**
     * 用户名
     * */
    public static final String USER_NAME;

    /**
     * 密码
     * */
    public static final String PASSWORD;

    static {
        if (ENV.equals("DEV")) {
            CLASS_NAME = "oracle.jdbc.OracleDriver";
            URL = "jdbc:oracle:thin:@192.168.131.52:1521:dev12c";
            USER_NAME = "SWGDPARA";
            PASSWORD = "easiapss";
        } else {
            CLASS_NAME = "oracle.jdbc.OracleDriver";
            URL = "jdbc:oracle:thin:@192.168.130.216:1521:testeport";
            USER_NAME = "devtester";
            PASSWORD = "easytester";
        }
    }

}