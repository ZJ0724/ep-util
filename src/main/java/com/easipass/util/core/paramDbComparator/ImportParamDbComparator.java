package com.easipass.util.core.paramDbComparator;

import com.easipass.util.core.Database;
import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.Project;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDPARADatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.MathUtil;
import com.easipass.util.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参数库导入比对器
 *
 * @author ZJ
 * */
public final class ImportParamDbComparator extends ParamDbComparator {

    /**
     * 组名
     * */
    private final String groupName;

    /**
     * mdb文件路径
     * */
    private final String mdbPath;

    /**
     * 日志
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportParamDbComparator.class);

    /**
     * 构造函数
     *
     * @param groupName 组名
     * @param mdbPath mdb文件路径
     * */
    public ImportParamDbComparator(String groupName, String mdbPath) {
        this.groupName = groupName;
        this.mdbPath = mdbPath;
    }

    @Override
    public ParamDbComparator.ComparisonMessage comparison() {
        // 比对信息
        ParamDbComparator.ComparisonMessage result = new ComparisonMessage();

        try {
            // 验证组名是否存在
            if (!SWGDPARADatabase.groupNameIsExist(this.groupName)) {
                throw new WarningException("组名: " + this.groupName + "不存在");
            }

            // mdb表名集合
            final List<String> mdbTables = SWGDPARADatabase.getGroupTables(groupName, "SOURCE_TABLE_NAME");
            // 数据库表名集合
            final List<String> dbTables = SWGDPARADatabase.getGroupTables(groupName, "TARGET_TABLE_NAME");

            // 设置比对信息
            result.AllTable = dbTables.size();

            CountDownLatch countDownLatch = new CountDownLatch(mdbTables.size());

            // 当前比对了多少个表
            AtomicInteger count = new AtomicInteger();

            // 发送websocket
            this.sendData(result.toString());

            // 遍历mdb
            for (int i = 0; i < mdbTables.size(); i++) {
                final int finalI = i;

                Project.THREAD_POOL_EXECUTOR.execute(() -> {
                    ComparisonMessage.TableFinishMessage finishMessage = new ComparisonMessage.TableFinishMessage();

                    try {
                        String mdbTableName = mdbTables.get(finalI);
                        String dbTableName = dbTables.get(finalI);

                        if (StringUtil.isEmpty(mdbTableName)) {
                            throw new WarningException("存在为空的mdb表名");
                        }

                        if (StringUtil.isEmpty(dbTableName)) {
                            throw new WarningException("存在为空的数据库表名");
                        }

                        finishMessage.resourceName = mdbTableName;
                        finishMessage.dbName = dbTableName;

                        // mdb表数据数量
                        finishMessage.resourceCount = MdbDatabase.getTableCount(mdbPath, mdbTableName);

                        // 数据库表数据数量
                        finishMessage.dbCount = SWGDPARADatabase.getTableCount(dbTableName);

                        // 版本
                        String version = SWGDPARADatabase.getTableVersion(dbTableName);

                        // mdb表字段
                        List<String> mdbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "SOURCE_NAME");
                        // 数据库表字段
                        List<String> dbTableFields = SWGDPARADatabase.getTableFields(dbTableName, "TARGET_NAME");

                        MdbDatabase mdbDatabase = new MdbDatabase(mdbPath);
                        try {
                            // mdb表数据
                            ResultSet resultSet = mdbDatabase.query("SELECT * FROM " + mdbTableName);

                            while (resultSet.next()) {
                                // mdb单条数据
                                List<String> dataList = new ArrayList<>();

                                for (String field : mdbTableFields) {
                                    String fieldValue = Database.getFiledData(resultSet, field);

                                    if (fieldValue == null) {
                                        fieldValue = "";
                                    }

                                    dataList.add(fieldValue.replaceAll("'", "''"));
                                }

                                // 数据库查询sql
                                String sql = "SELECT * FROM " + SWGDPARADatabase.SWGDPARA + "." + dbTableName + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                                for (int j = 0; j < dataList.size(); j ++) {
                                    String data = dataList.get(j);
                                    // 数据库字段
                                    String dbFieldName = dbTableFields.get(j);

                                    // COMPLEX.CODE_S
                                    if ("COMPLEX".equals(dbTableName)) {
                                        if ("CODE_S".equals(dbFieldName)) {
                                            if (StringUtil.isEmptyAll(data)) {
                                                data = "__00";
                                                sql = StringUtil.append(sql, " AND ", dbFieldName, " = '", data, "'");
                                                continue;
                                            }
                                        }
                                    }

                                    // null
                                    if (StringUtil.isEmptyAll(data)) {
                                        continue;
                                    }

                                    // 字段类型
                                    String fieldType = SWGDPARADatabase.getFieldType(dbTableName, dbFieldName);

                                    // 兼容日期格式
                                    if ("TIMESTAMP".equals(fieldType)) {
                                        data = parseDate(data);

                                        sql = StringUtil.append(sql, " AND ", dbFieldName, " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
                                        continue;
                                    }

                                    sql = StringUtil.append(sql, " AND ", dbFieldName, " = '", data, "'");
                                }

                                LOGGER.info(sql);

                                // 查找数据是否存在
                                if (!SWGDPARADatabase.dataIsExist(sql)) {
                                    finishMessage.messages.add(sql);
                                    finishMessage.flag = false;
                                }
                            }
                        } catch (SQLException e) {
                            throw new WarningException(e.getMessage());
                        } finally {
                            mdbDatabase.close();
                        }
                    } catch (WarningException e) {
                        finishMessage.messages.add(e.getMessage());
                        finishMessage.flag = false;
                    } finally {
                        count.incrementAndGet();

                        if (finishMessage.flag) {
                            finishMessage.messages.add("SUCCESS");
                        }
                        result.finishMessages.add(finishMessage);
                        result.currentProgress = MathUtil.getPercentage(count.doubleValue(), result.AllTable);
                        // 发送websocket
                        this.sendData(result.toString());

                        countDownLatch.countDown();
                    }
                });
            }

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new ErrorException(e.getMessage());
            }
        } catch (WarningException e) {
            result.flag = false;
            result.message = e.getMessage();
        }

        LOGGER.info(result.toString());

        // 发送websocket
        this.sendData(result.toString());

        return result;
    }

}