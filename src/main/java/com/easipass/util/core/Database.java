package com.easipass.util.core;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.SqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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
     * 数据库名
     * */
    private final String name;

    /**
     * PreparedStatement
     * */
    private final Set<PreparedStatement> preparedStatements = new HashSet<>();

    /**
     * ResultSet
     * */
    private final Set<ResultSet> resultSets = new HashSet<>();

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(Database.class);

    /**
     * 构造函数
     *
     * @param connection 连接
     * */
    protected Database(Connection connection, String name) {
        this.connection = connection;
        this.name = name;

        log.debug("数据库: {}, 已连接", this.name);
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

        log.debug("数据库: {}, 已关闭", this.name);
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
            throw new ErrorException(e.getMessage());
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
            throw new ErrorException(e.getMessage());
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

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

}