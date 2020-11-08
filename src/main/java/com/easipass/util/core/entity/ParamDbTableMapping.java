package com.easipass.util.core.entity;

import com.easipass.util.core.component.SWGDPARADatabase;
import java.util.*;

/**
 * 参数库表映射
 *
 * @author ZJ
 * */
public final class ParamDbTableMapping {

    /**
     * 数据库表名
     * */
    private final String dbTableName;

    /**
     * 资源表名
     * */
    private final String resourceTableName;

    /**
     * 字段
     *
     * key：数据库字段
     * value：资源字段
     * */
    private final Map<String, String> fieldMapping = new LinkedHashMap<>();

    /**
     * 字段类型映射
     */
    private final Map<String, String> dbFieldTypeMapping = new HashMap<>();

    /**
     * 字段是否是主键映射
     */
    private final Map<String, Boolean> dbFieldIsPrimaryKeyMapping = new HashMap<>();

    /**
     * 构造函数
     *
     * @param dbTableName 表名
     * @param resourceTableName 资源表名
     * @param fields 字段
     * */
    public ParamDbTableMapping(String dbTableName, String resourceTableName, LinkedHashMap<String, String> fields) {
        this.dbTableName = dbTableName;
        this.resourceTableName = resourceTableName;
        Set<Map.Entry<String, String>> entries = fields.entrySet();
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.fieldMapping.put(key, value);
            this.dbFieldTypeMapping.put(key, swgdparaDatabase.getFieldType(dbTableName, key));
            this.dbFieldIsPrimaryKeyMapping.put(key, swgdparaDatabase.isPrimaryKey(dbTableName, key));
        }
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public String getResourceTableName() {
        return resourceTableName;
    }

    public Map<String, String> getFieldMapping() {
        return fieldMapping;
    }

    public Map<String, String> getDbFieldTypeMapping() {
        return dbFieldTypeMapping;
    }

    public Map<String, Boolean> getDbFieldIsPrimaryKeyMapping() {
        return dbFieldIsPrimaryKeyMapping;
    }

}