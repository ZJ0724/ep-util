package com.easipass.util.core.util;

import com.easipass.util.core.exception.InfoException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc工具类
 *
 * @author ZJ
 * */
public final class JdbcUtil {

    /**
     * 获取字段类型
     *
     * @param dataSource 数据源
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段类型
     * */
    public static String getFieldType(DataSource dataSource, String tableName, String fieldName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE ROWNUM = 1");
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1 ; i <= metaData.getColumnCount(); i++) {
                if (metaData.getColumnName(i).equals(fieldName)) {
                    return metaData.getColumnTypeName(i);
                }
            }
            throw new InfoException("字段未找到：" + fieldName);
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        } finally {
            close(connection);
            close(preparedStatement);
            close(resultSet);
        }
    }

    /**
     * 关闭资源
     *
     * @param connection 连接
     * */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * 关闭资源
     *
     * @param preparedStatement preparedStatement
     * */
    public static void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * 关闭资源
     *
     * @param resultSet preparedStatement
     * */
    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * 表字段是否是主键
     *
     * @param dataSource 数据源
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 是主键返回true
     * */
    public static boolean isPrimaryKey(DataSource dataSource, String tableName, String fieldName) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
            while (resultSet.next()) {
                if (resultSet.getString(4).equals(fieldName)) {
                    return true;
                }
            }
            return false;
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        } finally {
            close(connection);
            close(resultSet);
        }
    }

    /**
     * 获取所有表名
     *
     * @param dataSource 数据源
     *
     * @return 所有表名
     * */
    public static List<String> getTables(DataSource dataSource) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            resultSet = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString(3));
            }
            return result;
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        } finally {
            close(connection);
            close(resultSet);
        }
    }

}