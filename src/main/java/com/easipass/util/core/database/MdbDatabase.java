package com.easipass.util.core.database;

import com.easipass.util.core.Database;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SqlException;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.StringUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    public MdbDatabase(String path) throws WarningException {
        super(getConnection(path));
    }

    /**
     * 获取驱动
     *
     * @param path 路径
     *
     * @return Connection
     * */
    private static Connection getConnection(String path) throws WarningException {
        try {
            return DriverManager.getConnection("jdbc:ucanaccess://" + path);
        } catch (SQLException e) {
            throw new WarningException("不是正确的mdb文件");
        }
    }

    /**
     * 获取表数据数量
     *
     * @param tableName 表名
     *
     * @return 数据数量
     * */
    public static int getTableCount(String path, String tableName) throws WarningException {
        MdbDatabase mdbDatabase = new MdbDatabase(path);

        try {
            String count = getFiledData(mdbDatabase.query("SELECT COUNT(*) COUNT FROM " + tableName), "COUNT", true);

            if (StringUtil.isEmpty(count)) {
                return 0;
            }

            return Integer.parseInt(count);
        } catch (SqlException e) {
            throw new WarningException(e.getMessage());
        } finally {
            mdbDatabase.close();
        }
    }

    /**
     * 检查数据是否存在
     *
     * @param sql sql
     *
     * @return sql能查到数据返回true
     * */
    public static boolean dataIsExist(String path, String sql) throws WarningException {
        MdbDatabase mdbDatabase = new MdbDatabase(path);

        try {
            ResultSet resultSet = mdbDatabase.query(sql);

            return resultSet.next();
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            mdbDatabase.close();
        }
    }

}