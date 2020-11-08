package com.easipass.util.core.component;

import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

/**
 * SWGDPARA数据库
 *
 * @author ZJ
 * */
public final class SWGDPARADatabase {

    /**
     * 连接池
     * */
    private static final DataBaseConnectionPool DATA_BASE_CONNECTION_POOL;

    /**
     * 单例
     * */
    private static final SWGDPARADatabase SWGDPARA_DATABASE = new SWGDPARADatabase();

//    /**
//     * JdbcTemplate
//     * */
//    private static final JdbcTemplate JDBC_TEMPLATE;

    /**
     * SCHEMA
     * */
    public static final String SCHEMA = "SWGDPARA";

    static {
        DATA_BASE_CONNECTION_POOL = new DataBaseConnectionPool(new DatabaseInfo(SWGDPARAFileConfig.currentFile));
//        JDBC_TEMPLATE = new JdbcTemplate(DATA_BASE_CONNECTION_POOL.getDataSource());
    }

    /**
     * 构造函数
     */
    private SWGDPARADatabase() {}

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static SWGDPARADatabase getInstance() {
        return SWGDPARA_DATABASE;
    }

    /**
     * 获取表版本
     *
     * @param tableName 表名
     *
     * @return 版本
     * */
    public String getTableVersion(String tableName) {
        String result;
        JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(DATA_BASE_CONNECTION_POOL.getDataSource());
        List<Map<String, Object>> versions = JDBC_TEMPLATE.queryForList("SELECT PARAMS_VERSION FROM (SELECT * FROM " + SCHEMA + ".T_PARAMS_VERSION WHERE TABLE_NAME = ? AND STATUS = '1' ORDER BY PARAMS_VERSION DESC) WHERE ROWNUM = 1", tableName);

        if (versions.size() == 1) {
            result = versions.get(0).get("PARAMS_VERSION").toString();
        } else  {
            throw new InfoException("未找到版本号：" + tableName);
        }

        return result;
    }

    /**
     * 获取字段类型
     *
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段类型
     * */
    public String getFieldType(String tableName, String fieldName) {
        return JdbcUtil.getFieldType(DATA_BASE_CONNECTION_POOL.getDataSource(), SCHEMA + "." + tableName, fieldName);
    }

    /**
     * 检查数据是否存在
     *
     * @param sql sql
     *
     * @return sql能查到数据返回true
     * */
    public boolean dataIsExist(String sql) {
        JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(DATA_BASE_CONNECTION_POOL.getDataSource());
        List<Map<String, Object>> list = JDBC_TEMPLATE.queryForList(sql);
        return list.size() != 0;
    }

    /**
     * 获取表数据
     *
     * @param tableName 表名
     * @param version 版本
     *
     * @return 表数据
     * */
    public List<Map<String, Object>> getTableData(String tableName, String version) {
        JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(DATA_BASE_CONNECTION_POOL.getDataSource());
        return JDBC_TEMPLATE.queryForList("SELECT * FROM " + SCHEMA + "." + tableName + " WHERE PARAMS_VERSION = ?", version);
    }

    /**
     * 版本加1
     *
     * @param tableName 表名
     *
     * @return 加1后的版本
     * */
    public synchronized String versionAddOne(String tableName) {
        String currentVersion = null;
        String version;
        try {
            version = getTableVersion(tableName);
        } catch (Exception e) {
            version = null;
        }
        JdbcTemplate JDBC_TEMPLATE = new JdbcTemplate(DATA_BASE_CONNECTION_POOL.getDataSource());
        List<Map<String, Object>> currentVersionList = JDBC_TEMPLATE.queryForList("SELECT PARAMS_VERSION FROM " + SCHEMA + "." + "T_PARAMS_VERSION_CURRENT WHERE TABLE_NAME = ?", tableName);
        if (currentVersionList.size() == 1) {
            currentVersion = currentVersionList.get(0).get("PARAMS_VERSION").toString();
        }
        Integer currentVersionInteger = null;
        Integer versionInteger = null;
        Integer newVersion = null;
        if (currentVersion != null) {
            currentVersionInteger = Integer.parseInt(currentVersion);
        }
        if (version != null) {
            versionInteger = Integer.parseInt(version);
        }
        if (currentVersionInteger == null && versionInteger == null) {
            newVersion = 10000;
        } else if (currentVersionInteger == null) {
            newVersion = versionInteger + 1;
        } else if (versionInteger == null) {
            newVersion = currentVersionInteger + 1;
        } else if (currentVersionInteger > versionInteger) {
            newVersion = currentVersionInteger + 1;
        }
        if (newVersion == null) {
            throw new InfoException("版本为null");
        }


        List<Map<String, Object>> idList = JDBC_TEMPLATE.queryForList("SELECT ID FROM (SELECT * FROM " + SCHEMA + ".T_PARAMS_VERSION ORDER BY ID DESC) WHERE ROWNUM = 1");
        long newId;
        if (idList.size() == 1) {
            newId = Long.parseLong(idList.get(0).get("ID").toString());
        } else {
            newId = 100L;
        }
        newId = newId + 1;

        JDBC_TEMPLATE.update("INSERT INTO "+ SCHEMA + ".T_PARAMS_VERSION(\"ID\", \"CREATE_TIME\", \"GROUP_NAME\", \"PARAMS_VERSION\", \"TABLE_NAME\", \"STATUS\") VALUES ('" + newId + "', TO_TIMESTAMP('" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + "', 'SYYYY-MM-DD HH24:MI:SS:FF6'), 'auto', '" + newVersion + "', '" + tableName + "', '1')");

        return newVersion + "";
    }

    /**
     * 是否是主键
     *
     * @param tableName 表名
     * @param filedName 字段名
     * */
    public boolean isPrimaryKey(String tableName, String filedName) {
        return JdbcUtil.isPrimaryKey(DATA_BASE_CONNECTION_POOL.getDataSource(), tableName, filedName);
    }

}