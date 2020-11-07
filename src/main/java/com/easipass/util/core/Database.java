package com.easipass.util.core;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SqlException;
import com.easipass.util.core.util.StringUtil;
import java.sql.*;
import java.util.*;

/**
 * 数据库
 *
 * @author ZJ
 * */
public abstract class Database {

    /**
     * 连接
     * */
    private final Connection connection;

    /**
     * PreparedStatement
     * */
    private final Set<PreparedStatement> preparedStatements = new HashSet<>();

    /**
     * ResultSet
     * */
    private final Set<ResultSet> resultSets = new HashSet<>();

    /**
     * 构造函数
     *
     * @param connection 连接
     * */
    protected Database(Connection connection) {
        this.connection = connection;
    }

    /**
     * 关闭连接
     */
    public final void close() {
        try {
            for (ResultSet resultSet : this.resultSets) {
                resultSet.close();
            }

            for (PreparedStatement preparedStatement : this.preparedStatements) {
                preparedStatement.close();
            }

            this.connection.close();
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

//        log.debug("数据库: {}, 已关闭", this.name);
    }

    /**
     * 增删改
     *
     * @param sql sql
     * @param params 参数
     */
    public final void update(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = getPreparedStatement(sql, params);
            preparedStatement.execute();
            preparedStatement.close();
            this.preparedStatements.remove(preparedStatement);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage() + sql);
        }
    }

    /**
     * 查
     *
     * @param sql sql
     * @param params 参数
     *
     * @return ResultSet
     */
    public final ResultSet query(String sql, Object... params) {
        try {
            ResultSet resultSet = getPreparedStatement(sql, params).executeQuery();

            this.resultSets.add(resultSet);

            return resultSet;
        }catch (SQLException e) {
            throw new ErrorException(e.getMessage() + sql);
        }
    }

    /**
     * 获取prepareStatement
     *
     * @param sql sql
     * @param params 参数
     *
     * @return PreparedStatement
     * */
    public PreparedStatement getPreparedStatement(String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (params != null) {
                int paramsLength = params.length;

                for(int i = 0; i < paramsLength; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }

            this.preparedStatements.add(preparedStatement);

            return preparedStatement;
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    /**
     * queryToJson
     *
     * @param sql sql
     *
     * @return List<JSONObject>
     * */
    public final List<JSONObject> queryToJson(String sql) {
        List<JSONObject> result = new ArrayList<>();
        ResultSet resultSet = this.query(sql);
        ResultSetMetaData resultSetMetaData;
        int columnCount;
        try {
            resultSetMetaData = resultSet.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
        List<String> columns = new ArrayList<>();

        // 获取所有字段
        for (int i = 1; i <= columnCount; i++) {
            try {
                columns.add(resultSetMetaData.getColumnName(i));
            } catch (SQLException e) {
                throw new ErrorException(e.getMessage());
            }
        }

        // 遍历数据
        try {
            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject(true);

                for (String column : columns) {
                    jsonObject.put(column, resultSet.getString(column));
                }

                result.add(jsonObject);
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return result;
    }

    /**
     * 获取字段内容
     *
     * @param resultSet resultSet
     * @param filedName 字段名
     * @param isNext 时候需要调用next()方法
     *
     * @return 字段值
     * */
    public static String getFiledData(ResultSet resultSet, String filedName, boolean isNext) {
        if (isNext) {
            try {
                if (!resultSet.next()) {
                    return null;
                }
            } catch (SQLException e) {
                throw new ErrorException(e.getMessage());
            }
        }

        try {
            return resultSet.getString(filedName);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取字段内容
     *
     * @param resultSet resultSet
     * @param filedName 字段名
     *
     * @return 字段值
     * */
    public static String getFiledData(ResultSet resultSet, String filedName) {
        return getFiledData(resultSet, filedName, false);
    }

    /**
     * 获取字段类型
     *
     * @param resultSet resultSet
     * @param fieldName 字段名
     *
     * @return 字段类型
     * */
    public static String getFieldType(ResultSet resultSet, String fieldName) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1 ; i <= metaData.getColumnCount(); i++) {
                if (fieldName.equals(metaData.getColumnName(i))) {
                    return metaData.getColumnTypeName(i);
                }
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        return null;
    }

    /**
     * 表字段是否是主键
     *
     * @param tableName 表名
     * @param column 字段名
     *
     * @return 是主键返回true
     * */
    public final boolean isPrimaryKey(String tableName, String column) {
        try {
            ResultSet resultSet = this.connection.getMetaData().getPrimaryKeys(null, null, tableName);

            this.resultSets.add(resultSet);

            while (resultSet.next()) {
                if (resultSet.getString(4).equals(column)) {
                    return true;
                }
            }

            return false;
        } catch (java.sql.SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取所有表名
     *
     * @return 所有表名
     * */
    public final List<String> getTables() {
        try {
            ResultSet resultSet = this.connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            List<String> result = new ArrayList<>();

            this.resultSets.add(resultSet);

            while (resultSet.next()) {
                result.add(resultSet.getString(3));
            }

            return result;
        } catch (java.sql.SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 获取所有字段
     *
     * @param tableName resultSet
     *
     * @return 所有字段
     * */
    public List<String> getFields(String tableName) {
        try {
            List<String> result = new ArrayList<>();
            ResultSet resultSet = this.query("SELECT * FROM " + tableName + " WHERE ROWNUM = 1");
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1 ; i <= metaData.getColumnCount(); i++) {
                result.add(metaData.getColumnName(i));
            }
            return result;
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param jsonObject 数据
     * */
    public final void insert(String tableName, JSONObject jsonObject) {
        String sql = "INSERT INTO " + tableName;
        String fields = "(";
        String values = " VALUES(";
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String field = entry.getKey();
            Object ObjectValue = entry.getValue();
            if (ObjectValue == null) {
                continue;
            }
            String value = ObjectValue.toString();
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            fields = StringUtil.append(fields, field, ", ");
            values = StringUtil.append(values, value, ", ");
        }
        fields = fields.substring(0, fields.length() - 2) + ")";
        values = values.substring(0, values.length() - 2) + ")";
        sql = sql + fields + values;
        this.update(sql);
    }

    /**
     * 插入数据2.0
     *
     * @param tableName 表名
     * @param data 数据
     * */
    public final void insertV2(String tableName, List<Map<String, Object>> data) {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        PreparedStatement preparedStatement = this.getPreparedStatement("");

        for (Map<String, Object> d : data) {
            String sql = "INSERT INTO " + tableName;

            String fields = "(";
            String values = " VALUES(";
            Set<Map.Entry<String, Object>> entries = d.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                String field = entry.getKey();
                Object ObjectValue = entry.getValue();
                if (ObjectValue == null) {
                    continue;
                }
                String value = ObjectValue.toString();
                if (StringUtil.isEmpty(value)) {
                    continue;
                }
                fields = StringUtil.append(fields, field, ", ");
                values = StringUtil.append(values, value, ", ");
            }
            fields = fields.substring(0, fields.length() - 2) + ")";
            values = values.substring(0, values.length() - 2) + ")";
            sql = sql + fields + values;
            try {
                preparedStatement.addBatch(sql);
            } catch (java.sql.SQLException e) {
                throw new ErrorException(e.getMessage());
            }
        }

        try {
            preparedStatement.executeBatch();
            this.connection.commit();
        } catch (java.sql.SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}