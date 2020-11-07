package com.easipass.util.core.entity;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库信息
 *
 * @author ZJ
 * */
public final class DatabaseInfo {

    /**
     * 驱动
     * */
    private final String driverClass;

    /**
     * jdbcUrl
     * */
    private final String jdbcUrl;

    /**
     * 用户名
     * */
    private final String user;

    /**
     * 密码
     * */
    private final String password;

    /**
     * 构造函数
     *
     * @param driverClass 驱动
     * @param jdbcUrl jdbcUrl
     * @param user 用户名
     * @param password 密码
     * */
    public DatabaseInfo(String driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    /**
     * 构造函数
     *
     * @param file 文件
     * */
    public DatabaseInfo(File file) {
        if (!file.exists()) {
            throw new InfoException("文件不存在");
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            this.driverClass = properties.getProperty("driverClass");
            this.jdbcUrl = properties.getProperty("jdbcUrl");
            this.user = properties.getProperty("user");
            this.password = properties.getProperty("password");
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 构造函数
     *
     * @param jdbcUrl jdbcUrl
     * */
    public DatabaseInfo(String jdbcUrl) {
        this(null, jdbcUrl, null, null);
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "DatabaseInfo{" +
                "driverClass='" + driverClass + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}