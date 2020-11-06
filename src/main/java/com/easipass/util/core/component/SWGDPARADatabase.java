package com.easipass.util.core.component;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.BaseException;
import com.easipass.util.core.C3p0Config;
import com.easipass.util.core.Database;
import com.easipass.util.core.config.SWGDPARAConfig;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.StringUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SWGDPARA数据库
 *
 * @author ZJ
 * */
public final class SWGDPARADatabase extends Database {

    /**
     * c3p0连接池
     * */
    private static final ComboPooledDataSource COMBO_POOLED_DATA_SOURCE = new ComboPooledDataSource();

    /**
     * SCHEMA
     * */
    public static final String SCHEMA = "SWGDPARA";

    /**
     * 构造函数
     */
    public SWGDPARADatabase() {
        super(getConnection());
    }

    /**
     * 获取连接
     *
     * @return Connection
     * */
    private static Connection getConnection() {
        try {
            Class.forName(SWGDPARAConfig.CLASS_NAME);
            C3p0Config.getInstance().setData(COMBO_POOLED_DATA_SOURCE);
            try {
                COMBO_POOLED_DATA_SOURCE.setDriverClass(SWGDPARAConfig.CLASS_NAME);
            } catch (PropertyVetoException e) {
                throw new ErrorException(e.getMessage());
            }
            COMBO_POOLED_DATA_SOURCE.setJdbcUrl(SWGDPARAConfig.URL);
            COMBO_POOLED_DATA_SOURCE.setUser(SWGDPARAConfig.USER_NAME);
            COMBO_POOLED_DATA_SOURCE.setPassword(SWGDPARAConfig.PASSWORD);

            return COMBO_POOLED_DATA_SOURCE.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BaseException(SCHEMA + "连接失败") {};
        }
    }

//    /**
//     * 组名是否存在
//     *
//     * @param groupName 组名
//     *
//     * @return 存在返回true
//     * */
//    public static boolean groupNameIsExist(String groupName) {
//        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
//        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_GROUP WHERE GROUP_NAME = ?", groupName);
//
//        try {
//            return resultSet.next();
//        } catch (SQLException e) {
//            throw new ErrorException(e.getMessage());
//        } finally {
//            swgdparaDatabase.close();
//        }
//    }

    /**
     * 获取分组上所有的表名
     *
     * @param groupName 组名
     * @param fieldName 要查找的字段
     *
     * @return 所有的表名
     * */
    public static List<String> getGroupTables(String groupName, String fieldName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_GROUP_TABLE WHERE GROUP_NAME = '" + groupName + "'");

        try {
            while (resultSet.next()) {
                String tableName = SWGDDatabase.getFiledData(resultSet, fieldName);
                result.add(tableName);
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }

        return result;
    }

    /**
     * 获取表所有映射字段
     *
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段集合
     * */
    public static List<String> getTableFields(String tableName, String fieldName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_MATCH WHERE TABLE_NAME = '" + tableName + "'");

        try {
            while (resultSet.next()) {
                String filed = SWGDDatabase.getFiledData(resultSet, fieldName);
                result.add(filed);
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }

        return result;
    }

    /**
     * 获取表版本
     *
     * @param tableName 表名
     *
     * @return 版本
     * */
    public static String getTableVersion(String tableName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        String version = "";

        try {
            ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_VERSION_CURRENT WHERE TABLE_NAME = '"+ tableName + "'");

            if (resultSet.next()) {
                version = getFiledData(resultSet, "PARAMS_VERSION");
            } else {
                ResultSet resultSet1 = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_VERSION WHERE TABLE_NAME = '" + tableName + "'");
                int v = 0;

                while (resultSet1.next()) {
                    int v1 = Integer.parseInt(getFiledData(resultSet1, "PARAMS_VERSION"));

                    if (v1 > v) {
                        v = v1;
                    }
                }

                if (v != 0) {
                    version = v + "";
                }
            }

            if (StringUtil.isEmpty(version)) {
                return null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }

        return version;
    }

    /**
     * 获取字段类型
     *
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段类型
     * */
    public static String getFieldType(String tableName, String fieldName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        String result;

        try {
            ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + "." + tableName + " WHERE ROWNUM <= 1");
            result = Database.getFieldType(resultSet, fieldName);
        } finally {
            swgdparaDatabase.close();
        }

        if (StringUtil.isEmpty(result)) {
            throw new ErrorException("表: " + tableName + " - " + fieldName + "未找到");
        }

        return result;
    }

    /**
     * 检查数据是否存在
     *
     * @param sql sql
     *
     * @return sql能查到数据返回true
     * */
    public static boolean dataIsExist(String sql) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();

        try {
            ResultSet resultSet = swgdparaDatabase.query(sql);
            return resultSet.next();
        }

        catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }

        catch (BaseException e) {
            return false;
        }

        finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 获取表数据
     *
     * @param tableName 表名
     * @param version 版本
     *
     * @return 表数据
     * */
    public static List<JSONObject> getTableData(String tableName, String version) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        try {
            return swgdparaDatabase.queryToJson("SELECT * FROM " + SCHEMA + "." + tableName + " WHERE PARAMS_VERSION = '" + version + "'");
        } catch (BaseException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 表是否存在
     *
     * @param tableName 表名
     *
     * @return 存在返回true
     * */
    public static boolean tableIsExist(String tableName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();

        try {
            swgdparaDatabase.query("SELECT * FROM " + SCHEMA + "." + tableName + " WHERE ROWNUM <= 1");
            return true;
        }
        catch (BaseException e) {
            return false;
        }
        finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 版本加1
     *
     * @param tableName 表名
     *
     * @return 加1后的版本
     * */
    public synchronized static String versionAddOne(String tableName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();

        try {
            String newVersion;

            // 版本数据集合
            List<JSONObject> versionDataList = swgdparaDatabase.queryToJson("SELECT * FROM (SELECT * FROM " + SWGDPARADatabase.SCHEMA + ".T_PARAMS_VERSION WHERE TABLE_NAME = '" + tableName + "' ORDER BY PARAMS_VERSION DESC) WHERE ROWNUM <= 1");

            if (versionDataList.size() == 0) {
                newVersion = "10000";
            } else {
                // 版本数据
                JSONObject versionData = versionDataList.get(0);
                // 版本
                String version = versionData.get("PARAMS_VERSION") + "";

                if (StringUtil.isEmpty(version)) {
                    newVersion = "10000";
                } else {
                    int versionInt;
                    try {
                        versionInt = Integer.parseInt(version);
                        newVersion = (versionInt + 1) + "";
                    } catch (NumberFormatException e) {
                        newVersion = "10000";
                    }
                }
            }

            // 获取下一个id
            Long newId = null;
            List<JSONObject> data = swgdparaDatabase.queryToJson("SELECT ID FROM (SELECT * FROM " + SCHEMA + ".T_PARAMS_VERSION ORDER BY ID DESC) WHERE ROWNUM = 1");
            if (data.size() == 1) {
                String id = data.get(0).getString("ID");
                newId = Long.parseLong(id) + 1;
            }
            if (newId == null) {
                newId = 100L;
            }

            swgdparaDatabase.update("INSERT INTO "+ SCHEMA +".T_PARAMS_VERSION(\"ID\", \"CREATE_TIME\", \"GROUP_NAME\", \"PARAMS_VERSION\", \"TABLE_NAME\", \"STATUS\") VALUES ('" + newId + "', TO_TIMESTAMP('" + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + "', 'SYYYY-MM-DD HH24:MI:SS:FF6'), 'auto', '" + newVersion + "', '" + tableName + "', '1')");

            return newVersion;
        } finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 插入数据
     *
     * @param sql sql
     *
     * @return 插入成功返回true
     * */
    public static boolean insert(String sql) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();

        try {
            swgdparaDatabase.update(sql);
            return true;
        } catch (BaseException e) {
            return false;
        }
        finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 是否是主键
     *
     * @param tableName 表名
     * @param column 字段名
     * */
    public static boolean myIsPrimaryKey(String tableName, String column) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        boolean result = swgdparaDatabase.isPrimaryKey(tableName, column);
        swgdparaDatabase.close();
        return result;
    }

    /**
     * 获取所有字段
     *
     * @param tableName 表名
     *
     * @return 所有字段
     * */
    public static List<String> MyGetFields(String tableName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        List<String> fields = swgdparaDatabase.getFields(SCHEMA + "." + tableName);
        swgdparaDatabase.close();
        return fields;
    }

    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param jsonObject 数据
     * */
    public static void myInsert(String tableName, JSONObject jsonObject) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        swgdparaDatabase.insert(SCHEMA + "." + tableName, jsonObject);
        swgdparaDatabase.close();
    }

}