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
    public String driverClass;

    /**
     * url
     * */
    @Key
    public String url;

    /**
     * username
     * */
    @Key
    public String username;

    /**
     * password
     * */
    @Key
    public String password;

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
     * 获取单例
     *
     * @return 单例
     * */
    public static SWGDDatabaseConfig getInstance() {
        return SWGD_DATABASE_CONFIG;
    }

    @Override
    protected void setDefaultData() {
        driverClass = "oracle.jdbc.OracleDriver";
        url = "jdbc:oracle:thin:@192.168.130.216:1521:testeport";
        username = "devtester";
        password = "easytester";
    }

}