package com.easipass.util.core.service;

import com.alibaba.fastjson.JSON;
import com.easipass.util.core.component.AccessDatabase;
import com.easipass.util.core.component.Excel;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.config.ParamDbTableMappingConfig;
import com.easipass.util.core.entity.ParamDbTableMapping;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.InfoException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.ListUtil;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.core.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

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
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadUtil.getThreadPoolExecutor(100);

    /**
     * mdb导入比对
     *
     * @param mdbPath mdb文件路径
     *
     * @return 比对结果
     * */
    public Result mdbImportComparator(String mdbPath) {
        // 结果
        Result result = new Result();
        // mdb数据库
        AccessDatabase accessDatabase = new AccessDatabase(mdbPath);
        // mdb文件中的所有表
        List<String> mdbTables = accessDatabase.getTables();
        // 多线程控制
        CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
        // SWGDPARA数据库
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();

        for (String mdbTableName : mdbTables) {
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    // 验证表是否还在使用
                    if (ParamDbTableMappingConfig.tableIsNotUse(mdbTableName)) {
                        return;
                    }

                    // 表映射配置
                    ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTableName);
                    // 数据库表名
                    String dbTableName = paramDbTableMapping.getDbTableName();
                    // 版本
                    String version = swgdparaDatabase.getTableVersion(dbTableName);
                    // mdb表数据
                    List<Map<String, Object>> mdbTableData = accessDatabase.getTableData(mdbTableName);
                    // 多线程遍历单条数据控制
                    CountDownLatch countDownLatch1 = new CountDownLatch(mdbTableData.size());
                    // 字段类型
                    Map<String, String> dbFieldTypeMapping = paramDbTableMapping.getDbFieldTypeMapping();
                    // 是否是主键
                    Map<String, Boolean> dbFieldIsPrimaryKeyMapping = paramDbTableMapping.getDbFieldIsPrimaryKeyMapping();
                    // mdb表所有字段
                    List<String> mdbTableFields = accessDatabase.getFields(mdbTableName);

                    // 验证字段
                    fieldCompare(mdbTableFields, paramDbTableMapping.getResourceFields());
                    fieldCompare(swgdparaDatabase.getFields(dbTableName), paramDbTableMapping.getDbFields());

                    for (Map<String, Object> data : mdbTableData) {
                        THREAD_POOL_EXECUTOR.execute(() -> {
                            try {
                                // sql
                                String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + paramDbTableMapping.getDbTableName() + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                                for (String mdbTableField : mdbTableFields) {
                                    // 数据库字段名
                                    String dbFieldName = paramDbTableMapping.getDbFieldByResource(mdbTableField);
                                    // mdb字段对应的数据
                                    Object mdbFieldData = data.get(mdbTableField);
                                    // 数据库字段类型
                                    String dbFieldType = dbFieldTypeMapping.get(dbFieldName);
                                    // 数据库字段是否是主键
                                    boolean dbFieldIsPrimaryKey = dbFieldIsPrimaryKeyMapping.get(dbFieldName);
                                    // 数据库字段值
                                    String dbFieldData = mdbFieldData == null ? null : mdbFieldData.toString();

                                    if (StringUtil.isEmptyAll(dbFieldData)) {
                                        // 如果数据库字段是主键，并且字段值是空，这补__00
                                        if (dbFieldIsPrimaryKey) {
                                            dbFieldData = "'__00'";
                                        }
                                    } else {
                                        // 单引号处理
                                        dbFieldData = dbFieldData.replaceAll("'", "''");

                                        // 兼容日期格式
                                        if ("TIMESTAMP".equals(dbFieldType)) {
                                            dbFieldData = "TO_DATE('" + parseDate(dbFieldData) + "','yyyy-mm-dd hh24:mi:ss')";
                                        } else {
                                            dbFieldData = "'" + dbFieldData + "'";
                                        }
                                    }

                                    sql = StringUtil.append(sql, " AND ", dbFieldName);
                                    if (StringUtil.isEmptyAll(dbFieldData)) {
                                        sql = StringUtil.append(sql, " IS NULL");
                                    } else {
                                        sql = StringUtil.append(sql, " = ", dbFieldData);
                                    }
                                }

                                log.info(sql);

                                // 查找数据是否存在
                                if (!swgdparaDatabase.dataIsExist(sql)) {
                                    result.addMessage(sql);
                                }
                            } catch (Throwable e) {
                                result.addMessage(e.getMessage());
                            } finally {
                                countDownLatch1.countDown();
                            }
                        });
                    }

                    // 等待
                    ThreadUtil.await(countDownLatch1);
                } catch (Throwable e) {
                    result.addMessage(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        // 等待
        ThreadUtil.await(countDownLatch);

        if (result.message.size() == 0) {
            result.flag = true;
            result.addMessage("比对完成，无差异");
        }

        log.info(result.toString());
        return result;
    }

    /**
     * excel导入比对
     *
     * @param tableName 表名
     * @param excelPath excel路径
     *
     * @return 比对结果
     * */
    public Result excelImportComparator(String tableName, String excelPath) {
        // 结果
        Result result = new Result();

        // 表是否还在使用
        if (ParamDbTableMappingConfig.tableIsNotUse(tableName)) {
            result.addMessage(tableName + "已弃用");
            return result;
        }

        // excel
        Excel excel = new Excel(excelPath, 0);
        // excel所有数据
        List<List<String>> excelData = excel.getAllData();

        // 验证excel数据不能为空
        if (excelData.size() == 0) {
            result.addMessage("excel数据为空");
            return result;
        }

        // 映射
        ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByDbTableName(tableName);
        // excel字段
        List<String> excelFields = excelData.get(0);
        // SWGDPARA数据库
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();

        // 字段验证
        fieldCompare(excelFields, paramDbTableMapping.getResourceFields());
        fieldCompare(swgdparaDatabase.getFields(tableName), paramDbTableMapping.getDbFields());

        // 版本
        String version = swgdparaDatabase.getTableVersion(tableName);
        // 多线程控制
        CountDownLatch countDownLatch = new CountDownLatch(excelData.size() - 1);
        // 数据库字段类型映射
        Map<String, String> dbFieldTypeMapping = paramDbTableMapping.getDbFieldTypeMapping();
        // 数据库字段是否是主键映射
        Map<String, Boolean> dbFieldIsPrimaryKeyMapping = paramDbTableMapping.getDbFieldIsPrimaryKeyMapping();

        for (int i = 1; i < excelData.size(); i++) {
            final int finalI = i;
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    // 单条excel数据
                    List<String> data = excelData.get(finalI);
                    // sql
                    String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + paramDbTableMapping.getDbTableName() + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                    for (String excelField : excelFields) {
                        // 数据库字段名
                        String dbFieldName = paramDbTableMapping.getDbFieldByResource(excelField);
                        // mdb字段对应的数据
                        Object mdbFieldData = data.get(ListUtil.getIndex(excelFields, excelField));
                        // 数据库字段类型
                        String dbFieldType = dbFieldTypeMapping.get(dbFieldName);
                        // 数据库字段是否是主键
                        boolean dbFieldIsPrimaryKey = dbFieldIsPrimaryKeyMapping.get(dbFieldName);
                        // 数据库字段值
                        String dbFieldData = mdbFieldData.toString();

                        if (StringUtil.isEmpty(dbFieldData)) {
                            // 如果数据库字段是主键，并且字段值是空，这补__00
                            if (dbFieldIsPrimaryKey) {
                                dbFieldData = "'__00'";
                            }
                        } else {
                            // 单引号处理
                            dbFieldData = dbFieldData.replaceAll("'", "''");

                            // 兼容日期格式
                            if ("TIMESTAMP".equals(dbFieldType)) {
                                dbFieldData = "TO_DATE('" + parseDate(dbFieldData) + "','yyyy-mm-dd hh24:mi:ss')";
                            }

                            // \N处理
                            else if ("\\N".equals(dbFieldData)) {
                                dbFieldData = "";
                            }

                            else {
                                dbFieldData = "'" + dbFieldData + "'";
                            }
                        }

                        sql = StringUtil.append(sql, " AND ", dbFieldName);
                        if (StringUtil.isEmpty(dbFieldData)) {
                            sql = StringUtil.append(sql, " IS NULL");
                        } else {
                            sql = StringUtil.append(sql, " = ", dbFieldData);
                        }
                    }

                    log.info(sql);

                    // 查找数据是否存在
                    if (!swgdparaDatabase.dataIsExist(sql)) {
                        result.addMessage(sql);
                    }
                } catch (Throwable e) {
                    result.addMessage(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        ThreadUtil.await(countDownLatch);

        if (result.message.size() == 0) {
            result.flag = true;
            result.addMessage("比对完成，无差异");
        }

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
        // 结果
        Result result = new Result();
        // mdb数据库
        AccessDatabase accessDatabase = new AccessDatabase(mdbPath);
        // mdb文件中的所有表
        List<String> mdbTables = accessDatabase.getTables();
        // 多线程控制
        CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
        // SWGDPARA数据库
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();

        for (String mdbTableName : mdbTables) {
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    // 验证表是否还在使用
                    if (ParamDbTableMappingConfig.tableIsNotUse(mdbTableName)) {
                        return;
                    }

                    // 表映射配置
                    ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTableName);
                    // 数据库表名
                    String dbTableName = paramDbTableMapping.getDbTableName();
                    // 数据库字段
                    List<String> dbFields = paramDbTableMapping.getDbFields();

                    // 验证字段
                    fieldCompare(swgdparaDatabase.getFields(dbTableName), dbFields);
                    fieldCompare(accessDatabase.getFields(mdbTableName), paramDbTableMapping.getResourceFields());

                    // 版本
                    String version = swgdparaDatabase.getTableVersion(dbTableName);
                    // 数据库数据
                    List<Map<String, Object>> dbTableData = swgdparaDatabase.getTableData(dbTableName, version);
                    // 多线程遍历单条数据控制
                    CountDownLatch countDownLatch1 = new CountDownLatch(dbTableData.size());
                    // 字段类型
                    Map<String, String> dbFieldTypeMapping = paramDbTableMapping.getDbFieldTypeMapping();
                    // 是否是主键
                    Map<String, Boolean> dbFieldIsPrimaryKeyMapping = paramDbTableMapping.getDbFieldIsPrimaryKeyMapping();

                    for (Map<String, Object> data : dbTableData) {
                        THREAD_POOL_EXECUTOR.execute(() -> {
                            try {
                                // sql
                                String sql = "SELECT * FROM " + mdbTableName + " WHERE 1 = 1";

                                for (String dbField : dbFields) {
                                    // 过滤PARAMS_VERSION
                                    if ("PARAMS_VERSION".equals(dbField)) {
                                        continue;
                                    }

                                    // mdb字段名
                                    String mdbFieldName = paramDbTableMapping.getResourceFieldByDb(dbField);
                                    // 数据库字段类型
                                    String dbFieldType = dbFieldTypeMapping.get(dbField);
                                    // 数据库字段是否是主键
                                    boolean dbFieldIsPrimaryKey = dbFieldIsPrimaryKeyMapping.get(dbField);
                                    // 数据库字段值
                                    Object dbFieldData = data.get(dbField);
                                    // mdb字段对应的数据
                                    String mdbFieldData = dbFieldData == null ? "" : dbFieldData.toString();
                                    // 是否需要加''
                                    boolean isAdd = true;

                                    if (!StringUtil.isEmpty(mdbFieldData)) {
                                        // 主键__00处理
                                        if (dbFieldIsPrimaryKey && "__00".equals(mdbFieldData)) {
                                            mdbFieldData = "";
                                        }

                                        // 单引号处理
                                        mdbFieldData = mdbFieldData.replaceAll("'", "''");

                                        // 兼容日期格式
                                        if ("TIMESTAMP".equals(dbFieldType)) {
                                            mdbFieldData = "TO_DATE('" + parseDate(mdbFieldData) + "','yyyy-mm-dd hh24:mi:ss')";
                                            isAdd = false;
                                        }

                                        // 兼容FLOAT
                                        if ("NUMBER".equals(dbFieldType)) {
                                            if (mdbFieldData.startsWith(".")) {
                                                mdbFieldData = "0" + mdbFieldData;
                                            }
                                        }
                                    }

                                    if (isAdd) {
                                        mdbFieldData = "'" + mdbFieldData + "'";
                                    }
                                    sql = StringUtil.append(sql, " AND ", mdbFieldName, " = ", mdbFieldData);
                                }

                                log.info(sql);

                                // 查找数据是否存在
                                if (!accessDatabase.dataIsExist(sql)) {
                                    result.addMessage(sql);
                                }
                            } catch (Throwable e) {
                                result.addMessage(e.getMessage());
                            } finally {
                                countDownLatch1.countDown();
                            }
                        });
                    }

                    // 等待
                    ThreadUtil.await(countDownLatch1);
                } catch (Throwable e) {
                    result.addMessage(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        // 等待
        ThreadUtil.await(countDownLatch);

        if (result.message.size() == 0) {
            result.flag = true;
            result.message.add("比对完成，无差异");
        }

        log.info(result.toString());
        return result;
    }

    /**
     * excel导入
     * @param tableName 表名
     * @param excelPath excel路径
     *
     * @return 比对结果
     * */
    public Result excelImport(String tableName, String excelPath) {
        // 结果
        Result result = new Result();

        // 表是否还在使用
        if (ParamDbTableMappingConfig.tableIsNotUse(tableName)) {
            result.addMessage(tableName + "已弃用");
            return result;
        }

        // excel
        Excel excel = new Excel(excelPath, 0);
        // excel所有数据
        List<List<String>> excelData = excel.getAllData();

        // 验证excel数据不能为空
        if (excelData.size() == 0) {
            result.addMessage("excel数据为空");
            return result;
        }

        // 映射
        ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByDbTableName(tableName);
        // excel字段
        List<String> excelFields = excelData.get(0);
        // SWGDPARA数据库
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();

        // 字段验证
        fieldCompare(excelFields, paramDbTableMapping.getResourceFields());
        fieldCompare(swgdparaDatabase.getFields(tableName), paramDbTableMapping.getDbFields());

        // 版本
        String version = swgdparaDatabase.versionAddOne(tableName);
        // 多线程控制
        CountDownLatch countDownLatch = new CountDownLatch(excelData.size() - 1);
        // 数据库字段类型映射
        Map<String, String> dbFieldTypeMapping = paramDbTableMapping.getDbFieldTypeMapping();
        // 数据库字段是否是主键映射
        Map<String, Boolean> dbFieldIsPrimaryKeyMapping = paramDbTableMapping.getDbFieldIsPrimaryKeyMapping();
        // excel字段长度
        int excelFieldsSize = excelFields.size();
        // 要插入的数据
        List<Map<String, Object>> insertDataList = new Vector<>();

        for (int i = 1; i < excelData.size(); i++) {
            final int finalI = i;
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    // 单条excel数据
                    List<String> data = excelData.get(finalI);
                    // 单条要插入的数据
                    Map<String, Object> insertData = new HashMap<>();

                    // 版本
                    insertData.put("PARAMS_VERSION", version);

                    for (int j = 0; j < excelFieldsSize; j++) {
                        // excel字段名
                        String excelFieldName = excelFields.get(j);
                        // 数据库字段名
                        String dbFieldName = paramDbTableMapping.getDbFieldByResource(excelFieldName);
                        // 数据库字段值
                        Object dbFieldData = data.get(j);
                        // 数据库字段类型
                        String dbFieldType = dbFieldTypeMapping.get(dbFieldName);
                        // 数据库字段是否是主键
                        boolean dbFieldIsPrimaryKey = dbFieldIsPrimaryKeyMapping.get(dbFieldName);

                        // 如果是主键，并且数据为空，则补__00
                        if (dbFieldIsPrimaryKey && StringUtil.isEmpty(dbFieldData)) {
                            dbFieldData = "__00";
                        }

                        if (!StringUtil.isEmpty(dbFieldData)) {
                            // \N默认为NULL
                            if ("\\N".equals(dbFieldData)) {
                                dbFieldData = null;
                            }

                            // 兼容日期字段
                            if ("TIMESTAMP".equals(dbFieldType)) {
                                dbFieldData = parseDateToDate(dbFieldData);
                            }
                        } else {
                            dbFieldData = null;
                        }

                        synchronized (this) {
                            insertData.put(dbFieldName, dbFieldData);
                        }
                    }

                    synchronized (this) {
                        if (!dataIsExist(insertDataList, insertData)) {
                            insertDataList.add(insertData);
                        }
                    }
                } catch (Throwable e) {
                    log.info(e.getMessage(), e);
                    result.addMessage(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        // 等待
        ThreadUtil.await(countDownLatch);

        // 插入数据
        swgdparaDatabase.insert(tableName, insertDataList);

        // 比对数据数量
        if (insertDataList.size() != excelData.size() - 1) {
            result.addMessage("导入完成，但数量不一致；已导入：" + insertDataList.size() + "，总共：" + (excelData.size() - 1));
        }

        if (result.message.size() == 0) {
            result.flag = true;
            result.addMessage("导入完成");
        }

        log.info(result.toString());
        return result;
    }

    /**
     * mdb导入
     *
     * @param mdbPath mdb文件路径
     *
     * @return 比对结果
     * */
    public Result mdbImport(String mdbPath) {
        // 结果
        Result result = new Result();

        // mdb数据库
        AccessDatabase accessDatabase = new AccessDatabase(mdbPath);
        // mdb文件中的所有表
        List<String> mdbTables = accessDatabase.getTables();
        // 多线程控制
        CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());
        // SWGDPARA数据库
        SWGDPARADatabase swgdparaDatabase = SWGDPARADatabase.getInstance();

        for (String mdbTableName : mdbTables) {
            THREAD_POOL_EXECUTOR.execute(() -> {
                try {
                    // 表是否还在使用
                    if (ParamDbTableMappingConfig.tableIsNotUse(mdbTableName)) {
                        result.addMessage(mdbTableName + "已弃用");
                        return;
                    }

                    // 表映射配置
                    ParamDbTableMapping paramDbTableMapping = ParamDbTableMappingConfig.getByResourceTableName(mdbTableName);
                    // 数据库表名
                    String dbTableName = paramDbTableMapping.getDbTableName();
                    // mdb表所有字段
                    List<String> mdbTableFields = accessDatabase.getFields(mdbTableName);

                    // 验证字段
                    fieldCompare(mdbTableFields, paramDbTableMapping.getResourceFields());
                    fieldCompare(swgdparaDatabase.getFields(dbTableName), paramDbTableMapping.getDbFields());

                    // 版本
                    String version = swgdparaDatabase.versionAddOne(dbTableName);
                    // mdb表数据
                    List<Map<String, Object>> mdbTableData = accessDatabase.getTableData(mdbTableName);
                    // 多线程遍历单条数据控制
                    CountDownLatch countDownLatch1 = new CountDownLatch(mdbTableData.size());
                    // 字段类型
                    Map<String, String> dbFieldTypeMapping = paramDbTableMapping.getDbFieldTypeMapping();
                    // 是否是主键
                    Map<String, Boolean> dbFieldIsPrimaryKeyMapping = paramDbTableMapping.getDbFieldIsPrimaryKeyMapping();
                    // 要插入的数据
                    List<Map<String, Object>> insertDataList = new Vector<>();

                    for (Map<String, Object> data : mdbTableData) {
                        THREAD_POOL_EXECUTOR.execute(() -> {
                            try {
                                // 单条要插入的数据
                                Map<String, Object> insertData = new HashMap<>();

                                // 版本
                                insertData.put("PARAMS_VERSION", version);

                                for (String mdbFieldName : mdbTableFields) {
                                    // 数据库字段名
                                    String dbFieldName = paramDbTableMapping.getDbFieldByResource(mdbFieldName);
                                    // 数据库字段值
                                    Object dbFieldData = data.get(mdbFieldName);
                                    // 数据库字段类型
                                    String dbFieldType = dbFieldTypeMapping.get(dbFieldName);
                                    // 数据库字段是否是主键
                                    boolean dbFieldIsPrimaryKey = dbFieldIsPrimaryKeyMapping.get(dbFieldName);

                                    // 如果是主键，并且数据为空，则补__00
                                    if (dbFieldIsPrimaryKey && StringUtil.isEmpty(dbFieldData)) {
                                        dbFieldData = "__00";
                                    }

                                    if (!StringUtil.isEmpty(dbFieldData)) {
                                        // 兼容日期字段
                                        if ("TIMESTAMP".equals(dbFieldType)) {
                                            dbFieldData = parseDateToDate(dbFieldData);
                                        }
                                    } else {
                                        dbFieldData = null;
                                    }

                                    synchronized (this) {
                                        insertData.put(dbFieldName, dbFieldData);
                                    }
                                }

                                // 比对数据
                                synchronized (this) {
                                    if (!dataIsExist(insertDataList, insertData)) {
                                        insertDataList.add(insertData);
                                    }
                                }
                            } catch (Throwable e) {
                                log.info(e.getMessage(), e);
                                result.addMessage(e.getMessage());
                            } finally {
                                countDownLatch1.countDown();
                            }
                        });
                    }

                    // 等待
                    ThreadUtil.await(countDownLatch1);

                    // 插入数据
                    swgdparaDatabase.insert(dbTableName, insertDataList);

                    // 比对数据数量
                    if (insertDataList.size() != mdbTableData.size()) {
                        result.addMessage(dbTableName + "导入完成，但数量不一致；已导入：" + insertDataList.size() + "，总共：" + (mdbTableData.size()));
                    }
                } catch (Throwable e) {
                    log.info(e.getMessage(), e);
                    result.addMessage(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        // 等待
        ThreadUtil.await(countDownLatch);

        if (result.message.size() == 0) {
            result.flag = true;
            result.addMessage("导入完成");
        }

        log.info(result.toString());
        return result;
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
     * 兼容日期
     *
     * @param date date
     *
     * @return date
     * */
    private static Date parseDateToDate(Object date) {
        if (date == null) {
            return null;
        }
        String newDate = parseDate(date.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(newDate);
        } catch (java.text.ParseException e) {
            throw new ErrorException(e.getMessage() + "：" + newDate);
        }
    }

    /**
     * 字段比较
     *
     * @param target 原字段
     * @param config config配置中的字段
     * */
    private static void fieldCompare(List<String> target, List<String> config) {
        List<String> error = new ArrayList<>();
        for (String t : target) {
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
            throw new InfoException("配置缺少字段映射：" + error);
        }
    }

    /**
     * 校验数据是否存在
     *
     * @param D 所有数据
     * @param CD 要校验的数据
     *
     * @return 如果根据校验数据遍历字段在所有数据中存在，返回true
     * */
    private static boolean dataIsExist(List<Map<String, Object>> D, Map<String, Object> CD) {
        if (D.size() == 0) {
            return false;
        }
        Set<String> keySet = CD.keySet();
        for (Map<String, Object> map : D) {
            boolean result = true;
            for (String key : keySet) {
                Object o1 = CD.get(key);
                Object o2 = map.get(key);
                String o1S = o1 == null ? "" : o1.toString();
                String o2S = o2 == null ? "" : o2.toString();
                if (o1 instanceof Date) {
                    o1S = DateUtil.format((Date) o1, "yyyy-MM-dd HH:mm:ss");
                }
                if (o2 instanceof Date) {
                    o2S = DateUtil.format((Date) o2, "yyyy-MM-dd HH:mm:ss");
                }
                if (!o1S.equals(o2S)) {
                    result = false;
                }
            }
            if (result) {
                return true;
            }
        }
        return false;
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
        public final List<String> message = new ArrayList<>();

        /**
         * 添加信息
         *
         * @param message 信息
         * */
        private void addMessage(String message) {
            this.message.add(message);
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

    }

}