package com.easipass.util.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.BaseException;
import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.ParamDbTableMappingConfig;
import com.easipass.util.core.entity.ParamDbTableMapping;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.ExcelUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.core.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 参数库服务
 *
 * @author ZJ
 * */
public final class ParamDbService {

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(ParamDbService.class);

    /**
     * 线程池
     * */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadUtil.getThreadPoolExecutor(200);

    /**
     * mdb导入比对
     *
     * @param mdbPath mdb文件路径
     *
     * @return 比对结果
     * */
    public Result mdbImportComparator(String mdbPath) {
        Result result = new Result();
        // mdb数据库
        AccessDatabase accessDatabase = new AccessDatabase(mdbPath);



//
//        // 获取mdb文件中的所有表
//        List<String> mdbTables = MdbDatabase.getTables(mdbPath);
//
//        // countDownLatch
//        CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
//
//        // 遍历mdb文件中的所有表
//        for (int i = 0; i < mdbTables.size(); i++) {
//            final int finalI = i;
//
//            THREAD_POOL_EXECUTOR.execute(() -> {
//                try {
//                    // mdb表名
//                    String mdbTable = mdbTables.get(finalI);
//
//                    // 表映射
//                    ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTable);
//
//                    if (paramDbTableMapping == null) {
//                        result.message.add(mdbTable + "表未在配置中找到");
//                        return;
//                    }
//
//                    // 数据库表名
//                    String dbTable = paramDbTableMapping.getDbTableName();
//                    // 版本
//                    String version = SWGDPARADatabase.getTableVersion(dbTable);
//
//                    if (StringUtil.isEmpty(version)) {
//                        result.message.add(dbTable + ": 无版本号");
//                        return;
//                    }
//
//                    // 字段比较
//                    boolean mdbTableFieldCompare = fieldCompare(paramDbTableMapping.getResourceTableFields(), MdbDatabase.MyGetFields(mdbPath, mdbTable), result, "mdb文件" + mdbTable);
//                    boolean dbTableFieldCompare = fieldCompare(paramDbTableMapping.getDbTableFields(), SWGDPARADatabase.MyGetFields(dbTable), result, "数据库表" + dbTable);
//                    if (!mdbTableFieldCompare || !dbTableFieldCompare) {
//                        return;
//                    }
//
//                    // mdb表数据
//                    List<JSONObject> mdbTableData = MdbDatabase.getTableData(mdbPath, mdbTable);
//
//                    // 遍历数据多线程控制
//                    CountDownLatch countDownLatch1 = new CountDownLatch(mdbTableData.size());
//
//                    for (int j = 0; j < mdbTableData.size(); j++) {
//                        int finalJ = j;
//
//                        THREAD_POOL_EXECUTOR.execute(() -> {
//                            try {
//                                // 单条数据
//                                JSONObject jsonObject = mdbTableData.get(finalJ);
//                                // sql
//                                String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + paramDbTableMapping.getDbTableName() + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";
//
//                                // 遍历字段映射
//                                Set<String> dbFields = paramDbTableMapping.getFields().keySet();
//                                for (String dbField : dbFields) {
//                                    // mdb字段
//                                    String mdbTableField = paramDbTableMapping.getFields().get(dbField);
//                                    // 字段值
//                                    String data = jsonObject.getString(mdbTableField);
//
//                                    // COMPLEX.CODE_S
//                                    if ("COMPLEX".equals(dbTable)) {
//                                        if ("CODE_S".equals(dbField)) {
//                                            if (StringUtil.isEmptyAll(data)) {
//                                                data = "__00";
//                                            }
//                                        }
//                                    }
//
//                                    // null
//                                    if (StringUtil.isEmptyAll(data)) {
//                                        continue;
//                                    }
//
//                                    // 单引号处理
//                                    data = data.replaceAll("'", "''");
//
//                                    // 字段类型
//                                    String fieldType = SWGDPARADatabase.getFieldType(dbTable, dbField);
//
//                                    // 兼容日期格式
//                                    if ("TIMESTAMP".equals(fieldType)) {
//                                        data = parseDate(data);
//                                        sql = StringUtil.append(sql, " AND ", dbField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
//                                        continue;
//                                    }
//
//                                    sql = StringUtil.append(sql, " AND ", dbField, " = '", data, "'");
//                                }
//
//                                log.info(sql);
//
//                                // 查找数据是否存在
//                                if (!SWGDPARADatabase.dataIsExist(sql)) {
//                                    result.message.add(sql);
//                                }
//                            } catch (Throwable e) {
//                                result.message.add(e.getMessage());
//                            } finally {
//                                countDownLatch1.countDown();
//                            }
//                        });
//                    }
//
//                    try {
//                        countDownLatch1.await();
//                    } catch (InterruptedException e) {
//                        throw new ErrorException(e.getMessage());
//                    }
//                } catch (Throwable e) {
//                    result.message.add(e.getMessage());
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//
//        // 等待执行完成
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            throw new ErrorException(e.getMessage());
//        }
//
//        if (result.message.size() == 0) {
//            result.flag = true;
//            result.message.add("比对完成，无差异");
//        }

        log.info(result.toString());

        return result;
    }

    /**
     * mdb导出比对
     *
     * @param mdbPath mdb文件路径
     *
     * @return 比对结果
     * */
    public Result mdbExportComparator(String mdbPath) {
        Result result = new Result();
//
//        // 获取mdb文件中的所有表
//        List<String> mdbTables = MdbDatabase.getTables(mdbPath);
//
//        // countDownLatch
//        CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
//
//        // 遍历mdb文件中的所有表
//        for (int i = 0; i < mdbTables.size(); i++) {
//            final int finalI = i;
//
//            Project.THREAD_POOL_EXECUTOR.execute(() -> {
//                try {
//                    // mdb表名
//                    String mdbTable = mdbTables.get(finalI);
//                    // 表映射
//                    ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTable);
//
//                    if (paramDbTableMapping == null) {
//                        result.message.add(mdbTable + "表未在配置中找到");
//                        return;
//                    }
//
//                    // 数据库表名
//                    String dbTable = paramDbTableMapping.getDbTableName();
//                    // 版本
//                    String version = SWGDPARADatabase.getTableVersion(dbTable);
//
//                    if (StringUtil.isEmpty(version)) {
//                        result.message.add(dbTable + ": 无版本号");
//                        return;
//                    }
//
//                    // 字段比较
//                    boolean mdbTableFieldCompare = fieldCompare(paramDbTableMapping.getResourceTableFields(), MdbDatabase.MyGetFields(mdbPath, mdbTable), result, "mdb文件" + mdbTable);
//                    boolean dbTableFieldCompare = fieldCompare(paramDbTableMapping.getDbTableFields(), SWGDPARADatabase.MyGetFields(dbTable), result, "数据库表" + dbTable);
//                    if (!mdbTableFieldCompare || !dbTableFieldCompare) {
//                        return;
//                    }
//
//                    // 数据库表数据
//                    List<JSONObject> dbTableData = SWGDPARADatabase.getTableData(dbTable, version);
//                    // 遍历数据多线程控制
//                    CountDownLatch countDownLatch1 = new CountDownLatch(dbTableData.size());
//                    // 字段
//                    Set<Map.Entry<String, String>> entries = paramDbTableMapping.getFields().entrySet();
//
//                    for (int j = 0; j < dbTableData.size(); j++) {
//                        int finalJ = j;
//
//                        Project.THREAD_POOL_EXECUTOR.execute(() -> {
//                            try {
//                                // 单条数据
//                                JSONObject jsonObject = dbTableData.get(finalJ);
//                                // sql
//                                String sql = "SELECT * FROM " + mdbTable + " WHERE 1 = 1";
//
//                                for (Map.Entry<String, String> entry : entries) {
//                                    // 数据库字段
//                                    String dbTableField = entry.getKey();
//                                    // 字段值
//                                    String data = jsonObject.getString(dbTableField);
//                                    // mdb字段
//                                    String mdbTableField = entry.getValue();
//
//                                    // 主键处理
//                                    if (SWGDPARADatabase.myIsPrimaryKey(dbTable, dbTableField) && "__00".equals(data)) {
//                                        data = "";
//                                    }
//
//                                    // null
//                                    if (StringUtil.isEmptyAll(data)) {
//                                        continue;
//                                    }
//
//                                    // 单引号处理
//                                    data = data.replaceAll("'", "''");
//
//                                    // 字段类型
//                                    String fieldType = SWGDPARADatabase.getFieldType(dbTable, dbTableField);
//
//                                    // 兼容日期格式
//                                    if ("TIMESTAMP".equals(fieldType)) {
//                                        data = parseDate(data);
//
//                                        sql = StringUtil.append(sql, " AND ", mdbTableField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
//                                        continue;
//                                    }
//
//                                    // 兼容FLOAT
//                                    if ("NUMBER".equals(fieldType)) {
//                                        if (data.startsWith(".")) {
//                                            data = "0" + data;
//                                        }
//                                    }
//
//                                    sql = StringUtil.append(sql, " AND ", mdbTableField, " = '", data, "'");
//                                }
//
//                                log.debug(sql);
//
//                                // 查找数据是否存在
//                                if (!MdbDatabase.dataIsExist(mdbPath, sql)) {
//                                    result.message.add(sql);
//                                }
//                            } catch (Throwable e) {
//                                result.message.add(e.getMessage());
//                            } finally {
//                                countDownLatch1.countDown();
//                            }
//                        });
//                    }
//
//                    try {
//                        countDownLatch1.await();
//                    } catch (InterruptedException e) {
//                        throw new ErrorException(e.getMessage());
//                    }
//                } catch (Throwable e) {
//                    result.message.add(e.getMessage());
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//
//        // 等待执行完成
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            throw new ErrorException(e.getMessage());
//        }
//
        if (result.message.size() == 0) {
            result.flag = true;
            result.message.add("比对完成，无差异");
        }

        log.info(result.toString());

        return result;
    }

    /**
     * excel导入比对
     *
     * @param tableName 表名
     * @param excelPath excel路径
     * @param isDeleteFile 比对完是否删除文件
     *
     * @return 比对结果
     * */
    public Result excelImportComparator(String tableName, String excelPath, boolean isDeleteFile) {
//        Result result = new Result();
//
//        try {
//            if (StringUtil.isEmpty(tableName)) {
//                result.message.add("表名不能为空");
//                return result;
//            }
//
//            // 映射
//            ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByDbTableName(tableName);
//            if (paramDbTableMapping == null) {
//                result.message.add(tableName + "表未在配置中找到");
//                return result;
//            }
//
//            // 版本
//            String version = SWGDPARADatabase.getTableVersion(tableName);
//
//            if (StringUtil.isEmpty(version)) {
//                result.message.add(tableName + ": 无版本号");
//                return result;
//            }
//
//            // excel
//            ExcelUtil excelUtil;
//
//            // 加载excel
//            try {
//                excelUtil = new ExcelUtil(excelPath, 0);
//            } catch (BaseException e) {
//                result.message.add(e.getMessage());
//                return result;
//            }
//
//            // excel所有数据
//            List<List<String>> excelAllData = excelUtil.getAllData();
//
//            if (excelAllData.size() == 0) {
//                result.message.add("excel数据为空");
//                return result;
//            }
//
//            // 字段比较
//            List<String> excelFields = paramDbTableMapping.getResourceTableFields();
//            boolean excelFieldCompare = fieldCompare(excelFields, excelAllData.get(0), result, "excel文件");
//            boolean dbTableFieldCompare = fieldCompare(paramDbTableMapping.getDbTableFields(), SWGDPARADatabase.MyGetFields(tableName), result, "数据库表" + tableName);
//            if (!excelFieldCompare || !dbTableFieldCompare) {
//                return result;
//            }
//
//            // 多线程控制
//            CountDownLatch countDownLatch = new CountDownLatch(excelAllData.size() - 1);
//            // 字段映射集合
//            Set<Map.Entry<String, String>> entries = paramDbTableMapping.getFields().entrySet();
//
//            // 遍历数据
//            for (int i = 1; i < excelAllData.size(); i++) {
//                int finalI = i;
//
//                THREAD_POOL_EXECUTOR.execute(() -> {
//                    try {
//                        // sql
//                        String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + tableName + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";
//                        // 单行数据
//                        List<String> rowData = excelAllData.get(finalI);
//
//                        for (Map.Entry<String, String> entry : entries) {
//                            // excel字段
//                            String excelField = entry.getValue();
//                            // db字段
//                            String dbField = entry.getKey();
//                            // excel单格数据
//                            String data = null;
//
//                            // 获取单格数据
//                            for (int n = 0; n < excelFields.size(); n++) {
//                                if (excelFields.get(n).equals(excelField)) {
//                                    data = rowData.get(n);
//                                }
//                            }
//
//                            // null
//                            if (StringUtil.isEmptyAll(data)) {
//                                continue;
//                            }
//
//                            data = data.replaceAll("'", "''");
//
//                            // \N
//                            if ("\\N".equals(data)) {
//                                continue;
//                            }
//
//                            // 字段类型
//                            String fieldType = SWGDPARADatabase.getFieldType(tableName, dbField);
//
//                            // 兼容日期格式
//                            if ("TIMESTAMP".equals(fieldType)) {
//                                data = parseDate(data);
//
//                                sql = StringUtil.append(sql, " AND ", dbField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
//                                continue;
//                            }
//
//                            sql = StringUtil.append(sql, " AND ", dbField, " = '", data, "'");
//                        }
//
//                        log.info(sql);
//
//                        // 查找数据是否存在
//                        if (!SWGDPARADatabase.dataIsExist(sql)) {
//                            result.message.add(sql);
//                        }
//                    } catch (Throwable e) {
//                        result.message.add(e.getMessage());
//                    } finally {
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                throw new ErrorException(e.getMessage());
//            }
//
//            if (result.message.size() == 0) {
//                result.flag = true;
//                result.message.add("比对完成，无差异");
//            }
//
//            log.info(result.toString());
//
//            return result;
//        } finally {
//            // 判断是否删除文件
//            if (isDeleteFile) {
//                FileUtil.delete(excelPath);
//            }
//        }
        return null;
    }

    /**
     * excel导入
     * @param tableName 表名
     * @param excelPath excel路径
     * @param isDeleteFile 比对完是否删除文件
     *
     * @return 比对结果
     * */
    public Result excelImport(String tableName, String excelPath, boolean isDeleteFile) {
//        Result result = new Result();
//
//        try {
//            if (StringUtil.isEmpty(tableName)) {
//                result.message.add("表名不能为空");
//                return result;
//            }
//
//            // 映射
//            ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByDbTableName(tableName);
//            if (paramDbTableMapping == null) {
//                result.message.add(tableName + "表未在配置中找到");
//                return result;
//            }
//
//            // 获取新版本
//            String version;
//
//            try {
//                version = SWGDPARADatabase.versionAddOne(tableName);
//            } catch (ErrorException e) {
//                result.message.add(tableName + ": " + e.getMessage());
//                return result;
//            }
//
//            // excel
//            ExcelUtil excelUtil;
//
//            // 加载excel
//            try {
//                excelUtil = new ExcelUtil(excelPath, 0);
//            } catch (BaseException e) {
//                result.message.add(e.getMessage());
//                return result;
//            }
//
//            // excel所有数据
//            List<List<String>> excelAllData = excelUtil.getAllData();
//
//            if (excelAllData.size() == 0) {
//                result.message.add("excel数据为空");
//                return result;
//            }
//
//            // 字段比较
//            List<String> excelFields = paramDbTableMapping.getResourceTableFields();
//            boolean excelFieldCompare = fieldCompare(excelFields, excelAllData.get(0), result, "excel文件");
//            boolean dbTableFieldCompare = fieldCompare(paramDbTableMapping.getDbTableFields(), SWGDPARADatabase.MyGetFields(tableName), result, "数据库表" + tableName);
//            if (!excelFieldCompare || !dbTableFieldCompare) {
//                return result;
//            }
//
//            // 多线程控制
//            CountDownLatch countDownLatch = new CountDownLatch(excelAllData.size() - 1);
//            // 字段映射集合
//            Set<Map.Entry<String, String>> entries = paramDbTableMapping.getFields().entrySet();
//
//            // 遍历数据
//            for (int i = 1; i < excelAllData.size(); i++) {
//                int finalI = i;
//
//                THREAD_POOL_EXECUTOR.execute(() -> {
//                    try {
//                        // sql
//                        String sql = "INSERT INTO " + SWGDPARADatabase.SCHEMA + "." + tableName;
//                        // sql字段
//                        String sqlField = "(PARAMS_VERSION, ";
//                        // sql值
//                        String sqlData = "(" + version + ", ";
//                        // 单行数据
//                        List<String> rowData = excelAllData.get(finalI);
//
//                        for (Map.Entry<String, String> entry : entries) {
//                            // excel字段
//                            String excelField = entry.getValue();
//                            // db字段
//                            String dbField = entry.getKey();
//                            // excel单格数据
//                            String data = null;
//
//                            // 获取单格数据
//                            for (int n = 0; n < excelFields.size(); n++) {
//                                if (excelFields.get(n).equals(excelField)) {
//                                    data = rowData.get(n);
//                                }
//                            }
//
//                            sqlField = StringUtil.append(sqlField, dbField, ", ");
//
//                            if (StringUtil.isEmpty(data)) {
//                                // 如果数据为空，又是主键，则补__00
//                                if (SWGDPARADatabase.myIsPrimaryKey(tableName, dbField)) {
//                                    sqlData = StringUtil.append(sqlData, "'__00'", ", ");
//                                } else {
//                                    sqlData = StringUtil.append(sqlData, "NULL", ", ");
//                                }
//                                continue;
//                            }
//
//                            // 字段类型
//                            String fieldType = SWGDPARADatabase.getFieldType(tableName, dbField);
//
//                            // 兼容日期格式
//                            if ("TIMESTAMP".equals(fieldType)) {
//                                data = parseDate(data);
//                                sqlData = StringUtil.append(sqlData, "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')", ", ");
//                                continue;
//                            }
//
//                            data = data.replaceAll("'", "''");
//
//                            sqlData = StringUtil.append(sqlData, "'", data, "', ");
//                        }
//
//                        sqlField = sqlField.substring(0, sqlField.length() - 2) + ")";
//                        sqlData = sqlData.substring(0, sqlData.length() - 2) + ")";
//                        sql = StringUtil.append(sql, sqlField, " VALUES", sqlData);
//
//                        log.info(sql);
//
//                        // 插入数据
//                        if (!SWGDPARADatabase.insert(sql)) {
//                            result.message.add(sql);
//                        }
//                    } catch (Throwable e) {
//                        result.message.add(e.getMessage());
//                    } finally {
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                throw new ErrorException(e.getMessage());
//            }
//
//            if (result.message.size() == 0) {
//                result.flag = true;
//                result.message.add("完成");
//            }
//
//            log.info(result.toString());
//
//            return result;
//        } finally {
//            // 判断是否删除文件
//            if (isDeleteFile) {
//                FileUtil.delete(excelPath);
//            }
//        }
        return null;
    }

    /**
     * mdb导入
     *
     * @param mdbPath mdb文件路径
     * @param isDeleteFile 比对完是否删除文件
     *
     * @return 比对结果
     * */
    public Result mdbImport(String mdbPath, boolean isDeleteFile) {
//        Result result = new Result();
//
//        try {
//            // 校验mdb文件是否正确
//            try {
//                MdbDatabase mdbDatabase = new MdbDatabase(mdbPath);
//                mdbDatabase.close();
//            } catch (WarningException e) {
//                result.message.add(e.getMessage());
//                return result;
//            }
//
//            // 获取mdb文件中的所有表
//            List<String> mdbTables = MdbDatabase.getTables(mdbPath);
//
//            // countDownLatch
//            CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
//
//            // 遍历mdb文件中的所有表
//            for (int i = 0; i < mdbTables.size(); i++) {
//                final int finalI = i;
//
//                THREAD_POOL_EXECUTOR.execute(() -> {
//                    try {
//                        // mdb表名
//                        String mdbTable = mdbTables.get(finalI);
//
//                        // 表映射
//                        ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTable);
//
//                        if (paramDbTableMapping == null) {
//                            result.message.add(mdbTable + "表未在配置中找到");
//                            return;
//                        }
//
//                        // 数据库表名
//                        String dbTable = paramDbTableMapping.getDbTableName();
//                        // 版本
//                        String version = SWGDPARADatabase.versionAddOne(dbTable);
//
//                        // 字段比较
//                        List<String> resourceTableFields = paramDbTableMapping.getResourceTableFields();
//                        List<String> dbTableFields = paramDbTableMapping.getDbTableFields();
//                        boolean mdbTableFieldCompare = fieldCompare(resourceTableFields, MdbDatabase.MyGetFields(mdbPath, mdbTable), result, "mdb文件" + mdbTable);
//                        boolean dbTableFieldCompare = fieldCompare(dbTableFields, SWGDPARADatabase.MyGetFields(dbTable), result, "数据库表" + dbTable);
//                        if (!mdbTableFieldCompare || !dbTableFieldCompare) {
//                            return;
//                        }
//
//                        // mdb表数据
//                        List<JSONObject> mdbTableData = MdbDatabase.getTableData(mdbPath, mdbTable);
//                        // db表数据
//                        List<Map<String, Object>> dbTableData = new ArrayList<>();
//                        // 遍历数据多线程控制
//                        CountDownLatch countDownLatch1 = new CountDownLatch(mdbTableData.size());
//                        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
//
//                        for (int j = 0; j < mdbTableData.size(); j++) {
//                            final int finalJ = j;
//                            threadPoolExecutor1.execute(() -> {
//                                try {
//                                    // 单条mdb数据
//                                    JSONObject mdbData = mdbTableData.get(finalJ);
//
//                                    // 单条db数据
//                                    Map<String, Object> dbData = dataTransformation(mdbData, paramDbTableMapping.getFields(), "key");
//
//                                    // 设置版本号
//                                    dbData.put("PARAMS_VERSION", "'" + version + "'");
//
//                                    // 遍历数据多线程控制
//                                    CountDownLatch countDownLatch2 = new CountDownLatch(dbTableFields.size());
//                                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(80, 80, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
//                                    for (String dbField : dbTableFields) {
//                                        threadPoolExecutor.execute(() -> {
//                                            try {
//                                                String data = dbData.get(dbField).toString();
//                                                // 字段类型
//                                                String fieldType = SWGDPARADatabase.getFieldType(dbTable, dbField);
//
//                                                // 如果是主键又是空，则补__00
//                                                if (SWGDPARADatabase.myIsPrimaryKey(dbTable, dbField) && StringUtil.isEmpty(data)) {
//                                                    data = "'__00'";
//                                                } else if ("TIMESTAMP".equals(fieldType) && !StringUtil.isEmpty(data)) {
//                                                    data = "TO_DATE('" + parseDate(data) + "','yyyy-mm-dd hh24:mi:ss')";
//                                                } else {
//                                                    if (StringUtil.isEmpty(data)) {
//                                                        return;
//                                                    }
//                                                    data = "'" + data.replaceAll("'", "''") + "'";
//                                                }
//                                                dbData.put(dbField, data);
//                                            } finally {
//                                                countDownLatch2.countDown();
//                                            }
//                                        });
//                                    }
//                                    try {
//                                        countDownLatch2.await();
//                                    } catch (InterruptedException e) {
//                                        throw new ErrorException(e.getMessage());
//                                    }
//
//                                    log.info(dbData.toString());
//
//                                    dbTableData.add(dbData);
//                                } catch (Throwable e) {
//                                    result.message.add(e.getMessage());
//                                } finally {
//                                    countDownLatch1.countDown();
//                                }
//                            });
//                        }
//
//                        try {
//                            countDownLatch1.await();
//                        } catch (InterruptedException e) {
//                            throw new ErrorException(e.getMessage());
//                        }
//
//                        // 插入数据
//                        SWGDPARADatabase.myInsert(dbTable, dbTableData);
//                    } catch (Throwable e) {
//                        result.message.add(e.getMessage());
//                    } finally {
//                        countDownLatch.countDown();
//                    }
//                });
//            }
//
//            // 等待执行完成
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                throw new ErrorException(e.getMessage());
//            }
//
//            if (result.message.size() == 0) {
//                result.flag = true;
//                result.message.add("导入完成");
//            }
//
//            log.info(result.toString());
//
//            return result;
//        } finally {
//            if (isDeleteFile) {
//                FileUtil.delete(mdbPath);
//            }
//        }
        return null;
    }

    /**
     * 兼容日期
     *
     * @param date date
     *
     * @return date
     * */
    private static String parseDate(String date) {
        String[] dates = new String[]{"yyyy-MM-dd HH:mm:ss.000000", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.0"};
        Date date1 = null;

        for (String d : dates) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(d);
                date1 = simpleDateFormat.parse(date);
                break;
            } catch (java.text.ParseException ignored) {}
        }

        if (date1 == null) {
            throw new ErrorException("日期格式不存在: " + date);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date1);
    }

    /**
     * 字段比较
     *
     * @param config config配置中的字段
     * @param target 原字段
     * @param result 比对结果
     * @param tableName 表名
     *
     * @return 比对正确，返回true
     * */
    private static boolean fieldCompare(List<String> config, List<String> target, Result result, String tableName) {
        List<String> error = new ArrayList<>();
        for (String t : target) {
            if ("PARAMS_VERSION".equals(t)) {
                continue;
            }
            boolean b = false;
            for (String c : config) {
                if (c.equals(t)) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                error.add(t);
            }
        }
        if (error.size() != 0) {
            result.message.add(tableName + "缺少字段配置：" + error);
            return false;
        }

        return true;
    }

    /**
     * 将数据字段进行转换
     *
     * @param jsonObject 被转换的数据
     * @param fieldMap 要替换的字段映射
     * @param type 基于key还是基于value
     * */
    private static Map<String, Object> dataTransformation(JSONObject jsonObject, Map<String, String> fieldMap, String type) {
        Map<String, Object> result = new HashMap<>();

        if (StringUtil.isEmpty(type)) {
            type = "key";
        }
        if (!type.equals("key") && !type.equals("value")) {
            type = "key";
        }

        Set<Map.Entry<String, String>> entries = fieldMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String field = type.equals("key") ? entry.getKey() : entry.getValue();
            String data = jsonObject.getString(type.equals("key") ? entry.getValue() : entry.getKey());
            result.put(field, data);
        }

        return result;
    }

    /**
     * 比对结果
     *
     * @author ZJ
     * */
    public static final class Result {

        /**
         * 标识
         * */
        public boolean flag = false;

        /**
         * 信息
         * */
        public List<String> message = new ArrayList<>();

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

    }

}