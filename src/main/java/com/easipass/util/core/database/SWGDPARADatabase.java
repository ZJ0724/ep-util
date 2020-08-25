package com.easipass.util.core.database;

import com.easipass.util.core.C3p0Config;
import com.easipass.util.core.Database;
import com.easipass.util.core.config.SWGDDatabaseConfig;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.StringUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SWGDPARADatabase
 *
 * @author ZJ
 * */
public final class SWGDPARADatabase extends Database {

    /**
     * c3p0连接池
     * */
    private static final ComboPooledDataSource COMBO_POOLED_DATA_SOURCE = new ComboPooledDataSource();

    /**
     * SWGDDatabaseConfig
     * */
    private static final SWGDDatabaseConfig SWGD_DATABASE_CONFIG = SWGDDatabaseConfig.getInstance();

    /**
     * SWGDPARA
     * */
    public static final String SWGDPARA = "SWGDPARA";

    static {
        C3p0Config.getInstance().setData(COMBO_POOLED_DATA_SOURCE);
        try {
            COMBO_POOLED_DATA_SOURCE.setDriverClass(SWGD_DATABASE_CONFIG.driverClass);
        } catch (PropertyVetoException e) {
            throw new ErrorException(e.getMessage());
        }
        COMBO_POOLED_DATA_SOURCE.setJdbcUrl(SWGD_DATABASE_CONFIG.url);
        COMBO_POOLED_DATA_SOURCE.setUser(SWGD_DATABASE_CONFIG.username);
        COMBO_POOLED_DATA_SOURCE.setPassword(SWGD_DATABASE_CONFIG.password);
    }

    /**
     * 构造函数
     */
    public SWGDPARADatabase() throws WarningException {
        super(getConnection(), SWGDPARA);
    }

    /**
     * 获取连接
     *
     * @return Connection
     * */
    private static Connection getConnection() throws WarningException {
        try {
            Class.forName(SWGD_DATABASE_CONFIG.driverClass);
            return COMBO_POOLED_DATA_SOURCE.getConnection();
        } catch (SQLException |ClassNotFoundException e) {
            throw new WarningException(SWGDPARA + "连接失败");
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
    public static List<String> getGroupTables(String groupName, String fieldName) throws WarningException {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SWGDPARA + ".T_PARAMS_GROUP_TABLE WHERE GROUP_NAME = '" + groupName + "'");

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
    public static List<String> getTableFields(String tableName, String fieldName) throws WarningException {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        List<String> result = new ArrayList<>();
        ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SWGDPARA + ".T_PARAMS_MATCH WHERE TABLE_NAME = '" + tableName + "'");

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
    public static String getTableVersion(String tableName) throws WarningException {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        String version = "";

        try {
            ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SWGDPARA + ".T_PARAMS_VERSION_CURRENT WHERE TABLE_NAME = '"+ tableName + "'");

            if (resultSet.next()) {
                version = getFiledData(resultSet, "PARAMS_VERSION");
            } else {
                ResultSet resultSet1 = swgdparaDatabase.query("SELECT * FROM " + SWGDPARA + ".T_PARAMS_VERSION WHERE TABLE_NAME = '" + tableName + "'");
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
                throw new WarningException(tableName + "未找到对应版本");
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdparaDatabase.close();
        }

        return version;
    }

    /**
     * 获取参数库表数据量
     *
     * @param tableName 表名
     *
     * @return 数据量
     * */
    public static int getTableCount(String tableName) throws WarningException {
        String version = getTableVersion(tableName);

        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();

        try {
            // 数量
            String countS = getFiledData(swgdparaDatabase.query("SELECT COUNT(*) COUNT FROM " + SWGDPARA + "." + tableName + " WHERE PARAMS_VERSION = '" + version + "'"), "COUNT", true);

            if (StringUtil.isEmpty(countS)) {
                return 0;
            }

            return Integer.parseInt(countS);
        } finally {
            swgdparaDatabase.close();
        }
    }

    /**
     * 检查数据是否存在
     *
     * @param sql sql
     *
     * @return sql能查到数据返回true
     * */
    public static boolean dataIsExist(String sql) throws WarningException {
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
     * 获取字段类型
     *
     * @param tableName 表名
     * @param fieldName 字段名
     *
     * @return 字段类型
     * */
    public static String getFieldType(String tableName, String fieldName) throws WarningException {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
        String result;

        try {
            ResultSet resultSet = swgdparaDatabase.query("SELECT * FROM " + SWGDPARA + "." + tableName + " WHERE ROWNUM <= 1");
            result = Database.getFieldType(resultSet, fieldName);
        } finally {
            swgdparaDatabase.close();
        }

        if (StringUtil.isEmpty(result)) {
            throw new WarningException("表: " + tableName + " - " + fieldName + "未找到");
        }

        return result;
    }

//    /**
//     * 清空参数库表数据
//     *
//     * @param tableName 表名
//     * */
//    public static void deleteParamDbTable(String tableName) {
//        String sql = StringUtil.append("DELETE FROM SWGDPARA.", tableName);
//
//        LOGGER.info(sql);
//
//        this.update(sql);
//    }

}