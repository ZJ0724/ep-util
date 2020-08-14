package com.easipass.util.core;

import com.easipass.util.core.exception.ErrorException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * c3p0配置文件
 *
 * @author ZJ
 * */
public final class C3p0Config {

    /**
     * 初始连接数
     * */
    private final int initialPoolSize;

    /**
     * 最大超时时间
     * */
    private final int maxIdleTime;

    /**
     * 最大连接数
     * */
    private final int maxPoolSize;

    /**
     * 最小连接数
     * */
    private final int minPoolSize;

    /**
     * 最大Statements数
     * */
    private final int maxStatements;

    /**
     * 获取连接超时时间
     * */
    private final int checkoutTimeout;

    /**
     * 配置文件
     * */
    private static final File FILE = new File(Resource.C3P0.getPath());

    /**
     * 单例
     * */
    private static final C3p0Config C_3_P_0_CONFIG = new C3p0Config();

    /**
     * 构造函数
     * */
    private C3p0Config() {
        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream(FILE);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
            inputStreamReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        this.initialPoolSize = Integer.parseInt(properties.get("initialPoolSize").toString());
        this.maxPoolSize = Integer.parseInt(properties.get("maxPoolSize").toString());
        this.minPoolSize = Integer.parseInt(properties.get("minPoolSize").toString());
        this.maxIdleTime = Integer.parseInt(properties.get("maxIdleTime").toString());
        this.maxStatements = Integer.parseInt(properties.get("maxStatements").toString());
        this.checkoutTimeout = Integer.parseInt(properties.get("checkoutTimeout").toString());
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static C3p0Config getInstance() {
        return C_3_P_0_CONFIG;
    }

    /**
     * 设置c3p0连接池数据
     *
     * @param comboPooledDataSource c3p0连接池
     * */
    public void setData(ComboPooledDataSource comboPooledDataSource) {
        comboPooledDataSource.setInitialPoolSize(this.initialPoolSize);
        comboPooledDataSource.setMaxPoolSize(this.maxPoolSize);
        comboPooledDataSource.setMinPoolSize(this.minPoolSize);
        comboPooledDataSource.setMaxIdleTime(this.maxIdleTime);
        comboPooledDataSource.setMaxStatements(this.maxStatements);
        comboPooledDataSource.setCheckoutTimeout(this.checkoutTimeout);
    }

}