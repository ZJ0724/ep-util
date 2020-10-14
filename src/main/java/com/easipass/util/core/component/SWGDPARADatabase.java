package com.easipass.util.core.component;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.BaseException;
import com.easipass.util.core.C3p0Config;
import com.easipass.util.core.Database;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ErrorException;
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
            Class.forName("oracle.jdbc.OracleDriver");
            C3p0Config.getInstance().setData(COMBO_POOLED_DATA_SOURCE);
            try {
                COMBO_POOLED_DATA_SOURCE.setDriverClass("oracle.jdbc.OracleDriver");
            } catch (PropertyVetoException e) {
                throw new ErrorException(e.getMessage());
            }
            COMBO_POOLED_DATA_SOURCE.setJdbcUrl("jdbc:oracle:thin:@192.168.130.216:1521:testeport");
            COMBO_POOLED_DATA_SOURCE.setUser("devtester");
            COMBO_POOLED_DATA_SOURCE.setPassword("easytester");

            return COMBO_POOLED_DATA_SOURCE.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BaseException(SCHEMA + "连接失败") {};
        }
    }

    /**
     * 组名是否存在
     *
     * @param groupName 组名
     *
     * @return 存在返回true
     * */
    public static boolean groupNameIsExist(String groupName) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SCHEMA + ".T_PARAMS_GROUP WHERE GROUP_NAME = ?", groupName);

        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }
    }

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
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
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

        return swgdparaDatabase.queryToJson("SELECT * FROM " + SCHEMA + "." + tableName + " WHERE PARAMS_VERSION = '" + version + "'");
    }

}