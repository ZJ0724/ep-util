package com.easipass.util.core.config;

import com.easipass.util.core.Config;
import com.easipass.util.core.Resource;

/**
 * SWGD数据库配置
 *
 * @author ZJ
 * */
public final class SWGDDatabaseConfig extends Config {

    /**
     * 驱动类
     * */
    @Key
    private String driverClass = "oracle.jdbc.OracleDriver";

    /**
     * url
     * */
    @Key
    private String url = "jdbc:oracle:thin:@192.168.130.216:1521:testeport";

    /**
     * username
     * */
    @Key
    private String username = "devtester";

    /**
     * password
     * */
    @Key
    private String password = "easytester";

    /**
     * 单例
     * */
    private static final SWGDDatabaseConfig SWGD_DATABASE_CONFIG = new SWGDDatabaseConfig();

    /**
     * 构造函数
     */
    private SWGDDatabaseConfig() {
        super(Resource.SWGD_DATABASE);
    }

    /**
     * getter
     * */
    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static SWGDDatabaseConfig getInstance() {
        return SWGD_DATABASE_CONFIG;
    }

}