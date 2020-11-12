package com.easipass.util.core.entity;

import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.StringUtil;
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
     * 是否需要导出
     * */
    private final boolean isExport;

    /**
     * 构造函数
     *
     * @param dbTableName 表名
     * @param resourceTableName 资源表名
     * @param fields 字段
     * @param isExport 是否需要导出
     * */
    public ParamDbTableMapping(String dbTableName, String resourceTableName, LinkedHashMap<String, String> fields, boolean isExport) {
        this.dbTableName = dbTableName;
        this.resourceTableName = resourceTableName;
        this.isExport = isExport;
        fields.put("PARAMS_VERSION", null);
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

    /**
     * 构造函数
     *
     * @param dbTableName 表名
     * @param resourceTableName 资源表名
     * @param fields 字段
     * */
    public ParamDbTableMapping(String dbTableName, String resourceTableName, LinkedHashMap<String, String> fields) {
        this(dbTableName, resourceTableName, fields, true);
    }

    /**
     * 获取所有资源字段
     *
     * @return 资源字段
     * */
    public List<String> getResourceFields() {
        Set<Map.Entry<String, String>> entries = this.fieldMapping.entrySet();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            String value = entry.getValue();
            if (!StringUtil.isEmpty(value)) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * 获取所有数据库字段
     *
     * @return 数据库字段
     * */
    public List<String> getDbFields() {
        Set<Map.Entry<String, String>> entries = this.fieldMapping.entrySet();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            if (!StringUtil.isEmpty(key)) {
                result.add(key);
            }
        }
        return result;
    }

    /**
     * 根据资源字段名，获取数据库字段名
     *
     * @param resourceField 资源字段名
     * */
    public String getDbFieldByResource(String resourceField) {
        Set<Map.Entry<String, String>> entries = this.fieldMapping.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String value = entry.getValue();
            if (!StringUtil.isEmpty(value) && value.equals(resourceField)) {
                return entry.getKey();
            }
        }
        throw new InfoException("未找到对应数据库字段名");
    }

    /**
     * 根据数据库字段名，获取资源字段名
     *
     * @param dbField 数据库字段名
     * */
    public String getResourceFieldByDb(String dbField) {
        Set<Map.Entry<String, String>> entries = this.fieldMapping.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            if (!StringUtil.isEmpty(key) && key.equals(dbField)) {
                return entry.getValue();
            }
        }
        throw new InfoException("未找到对应资源字段名");
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public String getResourceTableName() {
        return resourceTableName;
    }

    public Map<String, String> getDbFieldTypeMapping() {
        return dbFieldTypeMapping;
    }

    public Map<String, Boolean> getDbFieldIsPrimaryKeyMapping() {
        return dbFieldIsPrimaryKeyMapping;
    }

    public boolean isExport() {
        return isExport;
    }

}