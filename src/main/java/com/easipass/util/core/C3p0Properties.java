package com.easipass.util.core;

import com.easipass.util.exception.ErrorException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * c3p0配置文件
 *
 * @author ZJ
 * */
public final class C3p0Properties {

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
     * 配置文件
     * */
    private static final File C3P0_PROPERTIES_FILE = new File(Project.getInstance().getProjectRootPath(), "config/c3p0.properties");

    /**
     * 单例
     * */
    private static final C3p0Properties C_3_P_0_PROPERTIES = new C3p0Properties();

    /**
     * 构造函数
     * */
    private C3p0Properties() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream(C3P0_PROPERTIES_FILE);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        this.initialPoolSize = (int) properties.get("initialPoolSize");
        this.maxPoolSize = (int) properties.get("maxPoolSize");
        this.minPoolSize = (int) properties.get("minPoolSize");
        this.maxIdleTime = (int) properties.get("maxIdleTime");
        this.maxStatements = (int) properties.get("maxStatements");
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static C3p0Properties getC3P0Properties() {
        return C_3_P_0_PROPERTIES;
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
    }

}