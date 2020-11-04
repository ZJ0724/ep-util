package com.easipass.util.core.entity;

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
    private final Map<String, String> fields = new LinkedHashMap<>();

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
        for (Map.Entry<String, String> entry : entries) {
            this.fields.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取数据库表所有字段
     *
     * @return 数据库表所有字段
     * */
    public List<String> getDbTableFields() {
        List<String> result = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = this.fields.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result.add(entry.getKey());
        }
        return result;
    }

    /**
     * 获取资源表所有字段
     *
     * @return 资源表所有字段
     * */
    public List<String> getResourceTableFields() {
        List<String> result = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = this.fields.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result.add(entry.getValue());
        }
        return result;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public String getResourceTableName() {
        return resourceTableName;
    }

    public Map<String, String> getFields() {
        return fields;
    }

}