package com.easipass.util.core.component;

import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.util.JdbcUtil;
import java.util.List;
import java.util.Map;

/**
 * access数据库
 *
 * @author ZJ
 * */
public final class AccessDatabase {

    /**
     * 连接池
     * */
    private final DataBaseConnectionPool dataBaseConnectionPool;

    /**
     * 构造函数
     *
     * @param accessFilePath access文件路径
     * */
    public AccessDatabase(String accessFilePath) {
        dataBaseConnectionPool = new DataBaseConnectionPool(new DatabaseInfo("jdbc:ucanaccess://" + accessFilePath));
        dataBaseConnectionPool.getConnection();
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

}