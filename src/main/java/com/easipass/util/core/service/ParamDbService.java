package com.easipass.util.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.BaseException;
import com.easipass.util.core.Project;
import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.ExcelUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
     * mdb导入比对
     *
     * @param groupName 组名
     * @param mdbPath mdb文件路径
     * @param isDeleteFile 比对完是否删除文件
     *
     * @return 比对结果
     * */
    public Result mdbImportComparator(String groupName, String mdbPath, boolean isDeleteFile) {
        Result result = new Result();

        try {
            if (StringUtil.isEmpty(groupName)) {
                result.message.add("组名不能为空");
                return result;
            }

            // 验证组名是否存在
            if (!SWGDPARADatabase.groupNameIsExist(groupName)) {
                result.message.add("组名不存在");
                return result;
            }

            // 校验mdb文件是否正确
            try {
                MdbDatabase mdbDatabase = new MdbDatabase(mdbPath);
                mdbDatabase.close();
            } catch (WarningException e) {
                result.message.add(e.getMessage());
                return result;
            }

            // mdb表名集合
            List<String> mdbTables = SWGDPARADatabase.getGroupTables(groupName, "SOURCE_TABLE_NAME");
            // 数据库表名集合
            List<String> dbTables = SWGDPARADatabase.getGroupTables(groupName, "TARGET_TABLE_NAME");
            // countDownLatch
            CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());

            // 遍历mdb表
            for (int i = 0; i < mdbTables.size(); i++) {
                final int finalI = i;

                Project.THREAD_POOL_EXECUTOR.execute(() -> {
                    try {
                        // mdb表名
                        String mdbTableName = mdbTables.get(finalI);
                        // db表名
                        String dbTableName = dbTables.get(finalI);

                        if (StringUtil.isEmpty(mdbTableName)) {
                            result.message.add("存在为空的mdb表名");
                        }

                        else if (StringUtil.isEmpty(dbTableName)) {
                            result.message.add("存在为空的db表名");
                        }

                        else {
                            // 版本
                            String version = SWGDPARADatabase.getTableVersion(dbTableName);

                            if (StringUtil.isEmpty(version)) {
                                result.message.add(dbTableName + ": 无版本号");
                            }

                            else {
                                // mdb表字段
                                List<String> mdbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "SOURCE_NAME");
                                // 数据库表字段
                                List<String> dbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "TARGET_NAME");
                                // mdb表数据
                                List<JSONObject> mdbTableData = MdbDatabase.getTableData(mdbPath, mdbTableName);

                                if (mdbTableData == null) {
                                    result.message.add(mdbTableName + "表未找到");
                                }

                                else {
                                    // 遍历数据多线程控制
                                    CountDownLatch countDownLatch1 = new CountDownLatch(mdbTableData.size());

                                    for (int j = 0; j < mdbTableData.size(); j++) {
                                        int finalJ = j;

                                        Project.THREAD_POOL_EXECUTOR.execute(() -> {
                                            try {
                                                // 单条数据
                                                JSONObject jsonObject = mdbTableData.get(finalJ);
                                                // sql
                                                String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + dbTableName + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                                                for (int n = 0; n < mdbTableFields.size(); n++) {
                                                    // mdb字段
                                                    String mdbTableField = mdbTableFields.get(n);
                                                    // 字段值
                                                    String data = jsonObject.getString(mdbTableField);
                                                    // 数据库字段
                                                    String dbTableField = dbTableFields.get(n);

                                                    // COMPLEX.CODE_S
                                                    if ("COMPLEX".equals(dbTableName)) {
                                                        if ("CODE_S".equals(dbTableField)) {
                                                            if (StringUtil.isEmptyAll(data)) {
                                                                data = "__00";
                                                            }
                                                        }
                                                    }

                                                    // null
                                                    if (StringUtil.isEmptyAll(data)) {
                                                        continue;
                                                    }

                                                    // 单引号处理
                                                    data = data.replaceAll("'", "''");

                                                    // 字段类型
                                                    String fieldType = SWGDPARADatabase.getFieldType(dbTableName, dbTableField);

                                                    // 兼容日期格式
                                                    if ("TIMESTAMP".equals(fieldType)) {
                                                        data = parseDate(data);
                                                        sql = StringUtil.append(sql, " AND ", dbTableField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
                                                        continue;
                                                    }

                                                    sql = StringUtil.append(sql, " AND ", dbTableField, " = '", data, "'");
                                                }

                                                log.info(sql);

                                                // 查找数据是否存在
                                                if (!SWGDPARADatabase.dataIsExist(sql)) {
                                                    result.message.add(sql);
                                                }
                                            } catch (Throwable e) {
                                                result.message.add(e.getMessage());
                                            } finally {
                                                countDownLatch1.countDown();
                                            }
                                        });
                                    }

                                    try {
                                        countDownLatch1.await();
                                    } catch (InterruptedException e) {
                                        throw new ErrorException(e.getMessage());
                                    }
                                }
                            }
                        }
                    } catch (Throwable e) {
                        result.message.add(e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            // 等待执行完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }

            if (result.message.size() == 0) {
                result.flag = true;
                result.message.add("比对完成，无差异");
            }

            log.info(result.toString());

            return result;
        } finally {
            // 判断是否删除文件
            if (isDeleteFile) {
                FileUtil.delete(mdbPath);
            }
        }
    }

    /**
     * mdb导出比对
     *
     * @param groupName 组名
     * @param mdbPath mdb文件路径
     * @param isDeleteFile 比对完是否删除文件
     *
     * @return 比对结果
     * */
    public Result mdbExportComparator(String groupName, String mdbPath, boolean isDeleteFile) {
        Result result = new Result();

        try {
            if (StringUtil.isEmpty(groupName)) {
                result.message.add("组名不能为空");
                return result;
            }

            // 验证组名是否存在
            if (!SWGDPARADatabase.groupNameIsExist(groupName)) {
                result.message.add("组名不存在");
                return result;
            }

            // 校验mdb文件是否正确
            try {
                MdbDatabase mdbDatabase = new MdbDatabase(mdbPath);
                mdbDatabase.close();
            } catch (WarningException e) {
                result.message.add(e.getMessage());
                return result;
            }

            // 数据库表名集合
            List<String> dbTables = SWGDPARADatabase.getGroupTables(groupName, "TARGET_TABLE_NAME");
            // mdb表名集合
            List<String> mdbTables = SWGDPARADatabase.getGroupTables(groupName, "SOURCE_TABLE_NAME");
            // countDownLatch
            CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());

            // 遍历数据库表
            for (int i = 0; i < dbTables.size(); i++) {
                final int finalI = i;

                Project.THREAD_POOL_EXECUTOR.execute(() -> {
                    try {
                        // db表名
                        String dbTableName = dbTables.get(finalI);
                        // mdb表名
                        String mdbTableName = mdbTables.get(finalI);

                        if (StringUtil.isEmpty(dbTableName)) {
                            result.message.add("存在为空的db表名");
                        } else if (StringUtil.isEmpty(mdbTableName)) {
                            result.message.add("存在为空的mdb表名");
                        } else {
                            // 版本
                            String version = SWGDPARADatabase.getTableVersion(dbTableName);

                            if (StringUtil.isEmpty(version)) {
                                result.message.add(dbTableName + ": 无版本号");
                            }
                            else {
                                // 数据库表字段
                                List<String> dbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "TARGET_NAME");
                                // mdb表字段
                                List<String> mdbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "SOURCE_NAME");
                                // 数据库表数据
                                List<JSONObject> dbTableData = SWGDPARADatabase.getTableData(dbTableName, version);

                                if (dbTableData == null) {
                                    result.message.add(dbTableName + "表未找到");
                                }
                                else {
                                    // 遍历数据多线程控制
                                    CountDownLatch countDownLatch1 = new CountDownLatch(dbTableData.size());

                                    for (int j = 0; j < dbTableData.size(); j++) {
                                        int finalJ = j;

                                        Project.THREAD_POOL_EXECUTOR.execute(() -> {
                                            try {
                                                // 单条数据
                                                JSONObject jsonObject = dbTableData.get(finalJ);
                                                // sql
                                                String sql = "SELECT * FROM " + mdbTableName + " WHERE 1 = 1";

                                                for (int n = 0; n < dbTableFields.size(); n++) {
                                                    // 数据库字段
                                                    String dbTableField = dbTableFields.get(n);
                                                    // 字段值
                                                    String data = jsonObject.getString(dbTableField);
                                                    // mdb字段
                                                    String mdbTableField = mdbTableFields.get(n);

                                                    // COMPLEX.CODE_S || CIQ_CODE.HS_CODE || CLASSIFY || LICENSEN.CODE_T.CODE_S
                                                    // 主键处理
                                                    if (
                                                            ("COMPLEX".equals(dbTableName) && "CODE_S".equals(dbTableField)) ||
                                                                    ("CIQ_CODE".equals(dbTableName) && "HS_CODE".equals(dbTableField)) ||
                                                                    ("CLASSIFY".equals(dbTableName)) ||
                                                                    ("LICENSEN".equals(dbTableName) && (("CODE_T".equals(dbTableField)) || ("CODE_S".equals(dbTableField)))) ||
                                                                    ("AREA_PRE".equals(dbTableName) && (("USE_TO".equals(dbTableField)) || ("TRADE_MODE".equals(dbTableField)) || ("GOODS_T2".equals(dbTableField)) || ("GOODS_T1".equals(dbTableField)) || ("DOCU_CODE".equals(dbTableField)) || ("DISTRICT_T".equals(dbTableField)) || ("CODE_FLAG".equals(dbTableField)))) ||
                                                                    ("TRADE_MO".equals(dbTableName) && (("TRADE_MODE".equals(dbTableField)) || ("DISTRICT_T".equals(dbTableField)) || ("IM_CONTROL".equals(dbTableField)))) ||
                                                                    ("CTA_INF_REC".equals(dbTableName)) ||
                                                                    ("QUATA".equals(dbTableName) && (("COUNTRY_CO".equals(dbTableField)) || ("CODE_T".equals(dbTableField)) || ("CODE_S".equals(dbTableField))))
                                                    ) {
                                                        if ("__00".equals(data)) {
                                                            data = "";
                                                        }
                                                    }

                                                    // null
                                                    if (StringUtil.isEmptyAll(data)) {
                                                        continue;
                                                    }

                                                    // 单引号处理
                                                    data = data.replaceAll("'", "''");

                                                    // 字段类型
                                                    String fieldType = SWGDPARADatabase.getFieldType(dbTableName, dbTableField);

                                                    // 兼容日期格式
                                                    if ("TIMESTAMP".equals(fieldType)) {
                                                        data = parseDate(data);

                                                        sql = StringUtil.append(sql, " AND ", mdbTableField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
                                                        continue;
                                                    }

                                                    // 兼容FLOAT
                                                    if ("NUMBER".equals(fieldType)) {
                                                        if (data.startsWith(".")) {
                                                            data = "0" + data;
                                                        }
                                                    }

                                                    sql = StringUtil.append(sql, " AND ", mdbTableField, " = '", data, "'");
                                                }

                                                log.info(sql);

                                                // 查找数据是否存在
                                                if (!MdbDatabase.dataIsExist(mdbPath, sql)) {
                                                    result.message.add(sql);
                                                }
                                            } catch (Throwable e) {
                                                result.message.add(e.getMessage());
                                            } finally {
                                                countDownLatch1.countDown();
                                            }
                                        });
                                    }

                                    try {
                                        countDownLatch1.await();
                                    } catch (InterruptedException e) {
                                        throw new ErrorException(e.getMessage());
                                    }
                                }
                            }
                        }
                    } catch (Throwable e) {
                        result.message.add(e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            // 等待执行完成
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }

            if (result.message.size() == 0) {
                result.flag = true;
                result.message.add("比对完成，无差异");
            }

            log.info(result.toString());

            return result;
        } finally {
            // 判断是否删除文件
            if (isDeleteFile) {
                FileUtil.delete(mdbPath);
            }
        }
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
        Result result = new Result();

        try {
            if (StringUtil.isEmpty(tableName)) {
                result.message.add("表名不能为空");
                return result;
            }

            // 表名是否存在
            if (!SWGDPARADatabase.tableIsExist(tableName)) {
                result.message.add(tableName + "不存在");
                return result;
            }

            // 版本
            String version = SWGDPARADatabase.getTableVersion(tableName);

            if (StringUtil.isEmpty(version)) {
                result.message.add(tableName + ": 无版本号");
                return result;
            }

            // excel
            ExcelUtil excelUtil;

            // 加载excel
            try {
                excelUtil = new ExcelUtil(excelPath, 0);
            } catch (BaseException e) {
                result.message.add(e.getMessage());
                return result;
            }

            // excel所有数据
            List<List<String>> excelAllData = excelUtil.getAllData();

            if (excelAllData.size() == 0) {
                result.message.add("excel数据为空");
                return result;
            }

            // excel第一行标题
            List<String> title = excelAllData.get(0);
            // excel字段映射
            List<String> excelFields = SWGDPARADatabase.getTableFields(tableName, "SOURCE_NAME");
            // db字段映射
            List<String> dbFields = SWGDPARADatabase.getTableFields(tableName, "TARGET_NAME");
            // 多线程控制
            CountDownLatch countDownLatch = new CountDownLatch(excelAllData.size() - 1);

            // 让标题和excel字段映射完成一次匹配
            for (String excelField : excelFields) {
                boolean ok = false;

                for (String t : title) {
                    if (StringUtil.isEmpty(t)) {
                        result.message.add("excel存在为空的标题");
                        return result;
                    }

                    if (t.equals(excelField)) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    result.message.add("excel缺少字段：" + excelField);
                    return result;
                }
            }

            // 遍历数据
            for (int i = 1; i < excelAllData.size(); i++) {
                int finalI = i;

                Project.THREAD_POOL_EXECUTOR.execute(() -> {
                    try {
                        // sql
                        String sql = "SELECT * FROM " + SWGDPARADatabase.SCHEMA + "." + tableName + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";
                        // 单行数据
                        List<String> rowData = excelAllData.get(finalI);

                        for (int j = 0 ; j < excelFields.size(); j++) {
                            // excel字段
                            String excelField = excelFields.get(j);
                            // db字段
                            String dbField = dbFields.get(j);
                            // excel单格数据
                            String data = null;

                            // 获取单格数据
                            for (int n = 0; n < title.size(); n++) {
                                if (title.get(n).equals(excelField)) {
                                    data = rowData.get(n);
                                }
                            }

                            // null
                            if (StringUtil.isEmptyAll(data)) {
                                continue;
                            }

                            data = data.replaceAll("'", "''");

                            // \N
                            if ("\\N".equals(data)) {
                                continue;
                            }

                            // 字段类型
                            String fieldType = SWGDPARADatabase.getFieldType(tableName, dbField);

                            // 兼容日期格式
                            if ("TIMESTAMP".equals(fieldType)) {
                                data = parseDate(data);

                                sql = StringUtil.append(sql, " AND ", dbField, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
                                continue;
                            }

                            sql = StringUtil.append(sql, " AND ", dbField, " = '", data, "'");
                        }

                        log.info(sql);

                        // 查找数据是否存在
                        if (!SWGDPARADatabase.dataIsExist(sql)) {
                            result.message.add(sql);
                        }
                    } catch (Throwable e) {
                        result.message.add(e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }

            if (result.message.size() == 0) {
                result.flag = true;
                result.message.add("比对完成，无差异");
            }

            log.info(result.toString());

            return result;
        } finally {
            // 判断是否删除文件
            if (isDeleteFile) {
                FileUtil.delete(excelPath);
            }
        }
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
        Result result = new Result();

        try {
            if (StringUtil.isEmpty(tableName)) {
                result.message.add("表名不能为空");
                return result;
            }

            // 表名是否存在
            if (!SWGDPARADatabase.tableIsExist(tableName)) {
                result.message.add(tableName + "不存在");
                return result;
            }

            // 获取新版本
            String version;

            try {
                version = SWGDPARADatabase.versionAddOne(tableName);
            } catch (ErrorException e) {
                result.message.add(tableName + ": " + e.getMessage());
                return result;
            }

            // excel
            ExcelUtil excelUtil;

            // 加载excel
            try {
                excelUtil = new ExcelUtil(excelPath, 0);
            } catch (BaseException e) {
                result.message.add(e.getMessage());
                return result;
            }

            // excel所有数据
            List<List<String>> excelAllData = excelUtil.getAllData();

            if (excelAllData.size() == 0) {
                result.message.add("excel数据为空");
                return result;
            }

            // excel第一行标题
            List<String> title = excelAllData.get(0);
            // excel字段映射
            List<String> excelFields = SWGDPARADatabase.getTableFields(tableName, "SOURCE_NAME");
            // db字段映射
            List<String> dbFields = SWGDPARADatabase.getTableFields(tableName, "TARGET_NAME");
            // 多线程控制
            CountDownLatch countDownLatch = new CountDownLatch(excelAllData.size() - 1);

            // 让标题和excel字段映射完成一次匹配
            for (String excelField : excelFields) {
                boolean ok = false;

                for (String t : title) {
                    if (StringUtil.isEmpty(t)) {
                        result.message.add("excel存在为空的标题");
                        return result;
                    }

                    if (t.equals(excelField)) {
                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    result.message.add("excel缺少字段：" + excelField);
                    return result;
                }
            }

            // 遍历数据
            for (int i = 1; i < excelAllData.size(); i++) {
                int finalI = i;

                Project.THREAD_POOL_EXECUTOR.execute(() -> {
                    try {
                        // sql
                        String sql = "INSERT INTO " + SWGDPARADatabase.SCHEMA + "." + tableName;
                        // sql字段
                        String sqlField = "(PARAMS_VERSION, ";
                        // sql值
                        String sqlData = "(" + version + ", ";
                        // 单行数据
                        List<String> rowData = excelAllData.get(finalI);

                        for (int j = 0 ; j < excelFields.size(); j++) {
                            // excel字段
                            String excelField = excelFields.get(j);
                            // db字段
                            String dbField = dbFields.get(j);
                            // excel单格数据
                            String data = null;

                            // 获取单格数据
                            for (int n = 0; n < title.size(); n++) {
                                if (title.get(n).equals(excelField)) {
                                    data = rowData.get(n);
                                }
                            }

                            sqlField = StringUtil.append(sqlField, dbField, ", ");

                            if (StringUtil.isEmpty(data)) {
                                sqlData = StringUtil.append(sqlData, "NULL", ", ");
                                continue;
                            }

                            // 字段类型
                            String fieldType = SWGDPARADatabase.getFieldType(tableName, dbField);

                            // 兼容日期格式
                            if ("TIMESTAMP".equals(fieldType)) {
                                data = parseDate(data);
                                sqlData = StringUtil.append(sqlData, "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')", ", ");
                                continue;
                            }

                            data = data.replaceAll("'", "''");

                            sqlData = StringUtil.append(sqlData, "'", data, "', ");
                        }

                        sqlField = sqlField.substring(0, sqlField.length() - 2) + ")";
                        sqlData = sqlData.substring(0, sqlData.length() - 2) + ")";
                        sql = StringUtil.append(sql, sqlField, " VALUES", sqlData);

                        log.info(sql);

                        // 插入数据
                        if (!SWGDPARADatabase.insert(sql)) {
                            result.message.add(sql);
                        }
                    } catch (Throwable e) {
                        result.message.add(e.getMessage());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }

            if (result.message.size() == 0) {
                result.flag = true;
                result.message.add("完成");
            }

            log.info(result.toString());

            return result;
        } finally {
            // 判断是否删除文件
            if (isDeleteFile) {
                FileUtil.delete(excelPath);
            }
        }
    }

    /**
     * 后台进行mdb导入比对
     *
     * @param groupName 组名
     * @param mdbPath mdb文件路径
     * */
    public void mdbImportComparatorOfTask(final String groupName, final String mdbPath) {
        final ParamDbService paramDbService = this;

        new TaskRunService("导入比对：" + groupName) {
            @Override
            public String run() {
                return paramDbService.mdbImportComparator(groupName, mdbPath, true).toString();
            }
        }.start();
    }

    /**
     * 后台进行mdb导出比对
     *
     * @param groupName 组名
     * @param mdbPath mdb文件路径
     * */
    public void mdbExportComparatorOfTask(String groupName, String mdbPath) {
        final ParamDbService paramDbService = this;

        new TaskRunService("导出比对：" + groupName) {
            @Override
            public String run() {
                return paramDbService.mdbExportComparator(groupName, mdbPath, true).toString();
            }
        }.start();
    }

    /**
     * 后台进行excel导入比对
     *
     * @param tableName 表名
     * @param excelPath excel路径
     * */
    public void excelImportComparatorOfTask(String tableName, String excelPath) {
        final ParamDbService paramDbService = this;

        new TaskRunService("excel导入比对：" + tableName) {
            @Override
            public String run() {
                return paramDbService.excelImportComparator(tableName, excelPath, true).toString();
            }
        }.start();
    }

    /**
     * 后台进行excel导入
     *
     * @param tableName 表名
     * @param excelPath excel路径
     * */
    public void excelImportOfTask(String tableName, String excelPath) {
        final ParamDbService paramDbService = this;

        new TaskRunService("excel导入：" + tableName) {
            @Override
            public String run() {
                return paramDbService.excelImport(tableName, excelPath, true).toString();
            }
        }.start();
    }

    /**
     * 兼容日期
     *
     * @param date date
     *
     * @return date
     * */
    private static String parseDate(String date) {
        String[] dates = new String[]{"yyyy-MM-dd HH:mm:ss.000000", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
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