package com.easipass.util.core.component;

import com.easipass.util.core.entity.DatabaseInfo;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * access数据库
 *
 * @author ZJ
 * */
public final class AccessDatabase {

    /**
     * 连接池
     * */
    public final DataBaseConnectionPool dataBaseConnectionPool;

    /**
     * JdbcTemplate
     * */
    public final JdbcTemplate jdbcTemplate;

    /**
     * 数据库信息
     * */
    public final DatabaseInfo databaseInfo;

    /**
     * 构造函数
     *
     * @param accessFilePath access文件路径
     * */
    public AccessDatabase(String accessFilePath) {
        databaseInfo = new DatabaseInfo("jdbc:ucanaccess://" + accessFilePath);
        dataBaseConnectionPool = new DataBaseConnectionPool(databaseInfo);
        jdbcTemplate = new JdbcTemplate(dataBaseConnectionPool.getDataSource());
    }

}