package com.easipass.util.core.component;

import com.easipass.util.core.config.C3p0Config;
import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 数据库连接池
 *
 * @author ZJ
 * */
public final class DataBaseConnectionPool {

    /**
     * c3p0连接池
     * */
    private final ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

    /**
     * 构造函数
     *
     * @param databaseInfo 数据库信息
     * */
    public DataBaseConnectionPool(DatabaseInfo databaseInfo) {
        try {
            comboPooledDataSource.setInitialPoolSize(C3p0Config.initialPoolSize);
            comboPooledDataSource.setMinPoolSize(C3p0Config.minPoolSize);
            comboPooledDataSource.setMaxPoolSize(C3p0Config.maxPoolSize);
            comboPooledDataSource.setMaxIdleTime(C3p0Config.maxIdleTime);
            comboPooledDataSource.setCheckoutTimeout(C3p0Config.checkoutTimeout);
            comboPooledDataSource.setDriverClass(databaseInfo.getDriverClass());
            comboPooledDataSource.setJdbcUrl(databaseInfo.getJdbcUrl());
            comboPooledDataSource.setUser(databaseInfo.getUser());
            comboPooledDataSource.setPassword(databaseInfo.getPassword());
        } catch (java.beans.PropertyVetoException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取DataSource
     *
     * @return DataSource
     * */
    public DataSource getDataSource(){
        return this.comboPooledDataSource;
    }

    /**
     * 获取连接
     *
     * @return Connection
     * */
    public Connection getConnection() {
        try {
            return this.comboPooledDataSource.getConnection();
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        }
    }

}