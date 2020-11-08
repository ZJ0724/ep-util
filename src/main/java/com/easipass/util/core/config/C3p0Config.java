package com.easipass.util.core.config;

/**
 * c3p0连接池配置
 *
 * @author ZJ
 * */
public final class C3p0Config {

    /**
     * 初始连接数
     * */
    public static final int initialPoolSize = 0;

    /**
     * 最大连接数
     * */
    public static final int maxPoolSize = 100;

    /**
     * 最小连接数
     * */
    public static final int minPoolSize = 0;

    /**
     * 空闲多少时间不使用回收
     * */
    public static final int maxIdleTime = 60;

    /**
     * 获取连接超时时间
     * */
    public static final int checkoutTimeout = 60000;

}