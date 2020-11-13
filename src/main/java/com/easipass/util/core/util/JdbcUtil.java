package com.easipass.util.core.util;

import com.easipass.util.core.exception.InfoException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
            close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * 关闭资源
     *
     * @param connection 连接
     * */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
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
            close(connection,null, resultSet);
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
            close(connection, null, resultSet);
        }
    }

    /**
     * 通过sql查询
     *
     * @param dataSource 数据源
     * @param sql sql
     *
     * @return 数据
     * */
    public static List<Map<String, Object>> queryForList(DataSource dataSource, String sql) {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(resultSetMetaData.getColumnName(i));
            }
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                for (String column : columns) {
                    map.put(column, resultSet.getObject(column));
                }
                result.add(map);
            }
            return result;
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage() + "：" + sql);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * 更新
     *
     * @param dataSource 数据源
     * @param sql sql
     * */
    public static void update(DataSource dataSource, String sql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (java.sql.SQLException e) {
            throw new InfoException(e.getMessage());
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    /**
     * 获取所有字段
     *
     * @param dataSource 数据源
     * @param tableName 表名
     *
     * @return 所有字段
     * */
    public static List<String> getFields(DataSource dataSource, String tableName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE ROWNUM = 1");
            resultSet = preparedStatement.executeQuery();
            List<String> result = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1 ; i <= metaData.getColumnCount(); i++) {
                result.add(metaData.getColumnName(i));
            }
            return result;
        } catch (SQLException e) {
            throw new InfoException(e.getMessage());
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * 插入数据
     *
     * @param dataSource 数据源
     * @param tableName 表名
     * @param data 数据
     * */
    public static void inert(DataSource dataSource, String tableName, List<Map<String, Object>> data) {
        if (data.size() == 0) {
            return;
        }

        String sql = "INSERT INTO " + tableName + "(";
        Set<String> fields = data.get(0).keySet();
        int fieldsSize = fields.size();
        String value = " VALUES (";
        int index = 0;

        for (String field : fields) {
            index++;

            String end;

            if (index == fieldsSize) {
                end = ")";
            } else {
                end = ", ";
            }

            sql = StringUtil.append(sql, field, end);
            value = StringUtil.append(value, "?", end);
        }
        sql = StringUtil.append(sql, value);

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            int dataSize = data.size();

            for (int i = 0; i < dataSize; i++) {
                Map<String, Object> map = data.get(i);
                index = 0;
                for (String field : fields) {
                    index++;
                    Object filedData = map.get(field);
                    if (filedData instanceof Date) {
                        Date date = (Date) filedData;
                        preparedStatement.setTimestamp(index, new Timestamp(date.getTime()));
                    } else {
                        preparedStatement.setObject(index, filedData);
                    }
                }
                preparedStatement.addBatch();
                if (i != 0 && i % 1000 == 0) {
                    preparedStatement.executeBatch();
                    connection.commit();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new InfoException(e.getMessage() + sql);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

}