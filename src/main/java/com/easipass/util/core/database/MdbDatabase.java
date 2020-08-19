package com.easipass.util.core.database;

import com.easipass.util.core.Database;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SqlException;
import com.easipass.util.core.util.StringUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * mdb数据库
 *
 * @author ZJ
 * */
public final class MdbDatabase extends Database {

    /**
     * 构造函数
     *
     * @param path 路径
     */
    public MdbDatabase(String path) {
        super(getConnection(path), path);
    }

    /**
     * 获取驱动
     *
     * @param path 路径
     *
     * @return Connection
     * */
    private static Connection getConnection(String path) {
        try {
            return DriverManager.getConnection("jdbc:ucanaccess://" + path);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取数据数量
     *
     * @param tableName 表名
     *
     * @return 数据数量
     * */
    public Integer getTableCount(String tableName) {
        try {
            String count = getFiledData(this.query("SELECT COUNT(*) COUNT FROM " + tableName), "COUNT", true);

            if (StringUtil.isEmpty(count)) {
                return 0;
            }

            return Integer.parseInt(count);
        } catch (SqlException e) {
            return null;
        }
    }

}