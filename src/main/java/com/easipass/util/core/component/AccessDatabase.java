package com.easipass.util.core.component;

import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.JdbcUtil;
import com.healthmarketscience.jackcess.*;
import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.*;

/**
 * access数据库
 *
 * @author ZJ
 * */
public final class AccessDatabase {

    /**
     * 连接池
     * */
    private DataBaseConnectionPool dataBaseConnectionPool;

    /**
     * 文件
     * */
    private final String accessFilePath;

    /**
     * Database
     */
    private final Database database;

    /**
     * 构造函数
     *
     * @param accessFilePath access文件路径
     * */
    public AccessDatabase(String accessFilePath) {
        this.accessFilePath = accessFilePath;
        DatabaseBuilder builder = new DatabaseBuilder(new File(accessFilePath));
        try {
            this.database = builder.open();
        } catch (java.io.IOException e) {
            throw new InfoException(e.getMessage());
        }
        reload();
    }

    /**
     * 检查数据是否存在
     *
     * @param sql sql
     *
     * @return sql能查到数据返回true
     * */
    public boolean dataIsExist(String sql) {
        return JdbcUtil.queryForList(dataBaseConnectionPool.getDataSource(), sql).size() != 0;
    }

    /**
     * 获取表所有数据
     *
     * @param tableName 表名
     *
     * @return 数据
     * */
    public List<Map<String, Object>> getTableData(String tableName) {
        return JdbcUtil.queryForList(dataBaseConnectionPool.getDataSource(), "SELECT * FROM " + tableName);
    }

    /**
     * 获取所有表
     *
     * @return 所有表
     * */
    public List<String> getTables() {
        return JdbcUtil.getTables(this.dataBaseConnectionPool.getDataSource());
    }

    /**
     * 获取表的所有字段
     *
     * @param tableName 表名
     *
     * @return 表的所有字段
     * */
    public List<String> getFields(String tableName) {
        return JdbcUtil.getFields(this.dataBaseConnectionPool.getDataSource(), tableName);
    }

    /**
     * 建表
     *
     * @param tableName 表名
     * @param fields 字段
     * */
    public synchronized void createTable(String tableName, Map<String, Class<?>> fields) {
        try {
            Set<Map.Entry<String, Class<?>>> entries = fields.entrySet();
            TableBuilder tableBuilder = new TableBuilder(tableName);
            for (Map.Entry<String, Class<?>> entry : entries) {
                Class<?> c = entry.getValue();
                int dataType;
                if (c == String.class) {
                    dataType = Types.LONGNVARCHAR;
                } else {
                    throw new ErrorException("未匹配到类型");
                }
                tableBuilder = tableBuilder.addColumn(new ColumnBuilder(entry.getKey()).setSQLType(dataType));
            }
            tableBuilder.toTable(this.database);
            reload();
        } catch (java.io.IOException | java.sql.SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 重新加载连接
     * */
    public void reload() {
        this.dataBaseConnectionPool = new DataBaseConnectionPool(new DatabaseInfo("jdbc:ucanaccess://" + this.accessFilePath));
        this.dataBaseConnectionPool.getConnection();
    }

    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param data 数据
     * */
    public synchronized void insert(String tableName, List<Object[]> data) {
        try {
            Table table = this.database.getTable(tableName);
            if (table == null) {
                throw new InfoException("未找到表：" + tableName);
            }
            table.addRows(data);
        } catch (java.io.IOException e) {
            throw new InfoException(e.getMessage());
        }
    }

    /**
     * 创建mdb文件
     *
     * @param accessFilePath mdb文件路径
     *
     * @return AccessDatabase
     * */
    public static AccessDatabase create(String accessFilePath) {
        try {
            DatabaseBuilder.create(Database.FileFormat.V2003, new File(accessFilePath));
            return new AccessDatabase(accessFilePath);
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}