package com.easipass.util.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.ConsoleUtil;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数库比对器
 *
 * @author ZJ
 * */
public final class ParamDbComparator {

    /**
     * 单例
     * */
    private static final ParamDbComparator PARAM_DB_COMPARATOR = new ParamDbComparator();

    /**
     * 构造函数
     * */
    private ParamDbComparator() {}

    /**
     * 日志
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamDbComparator.class);

    /**
     * 导入数据比对
     *
     * @param groupName 组名
     * @param path mdb文件路径
     *
     * @return 比对信息
     * */
    public List<ComparisonMessage> importComparison(String groupName, String path) {
        // mdb表名集合
        List<String> mdbTables = SWGDDatabase.getParamDbTables(groupName, "SOURCE_TABLE_NAME");
        // 数据库表名集合
        List<String> dbTables = SWGDDatabase.getParamDbTables(groupName, "TARGET_TABLE_NAME");
        List<ComparisonMessage> result = new ArrayList<>();

        // 遍历mdb
        for (int i = 0 ;i < mdbTables.size(); i++) {
            ComparisonMessage comparisonMessage = new ComparisonMessage();
            String mdbTableName = mdbTables.get(i);
            String dbTable = dbTables.get(i);

            comparisonMessage.mdbName = mdbTableName;
            comparisonMessage.dbName = dbTable;

            if (StringUtil.isEmpty(mdbTableName)) {
                comparisonMessage.flag = false;
                comparisonMessage.message = "存在mdb表名为空";
                result.add(comparisonMessage);
                continue;
            }

            // 版本
            String version = SWGDDatabase.getParamDbTableVersion(dbTable);

            if (StringUtil.isEmpty(version)) {
                comparisonMessage.flag = false;
                comparisonMessage.message = "参数库无版本";
                result.add(comparisonMessage);
                continue;
            }

            Integer c = SWGDDatabase.getParamDbDataCount(dbTable);
            if (c != null) {
                comparisonMessage.dbCount = c;
            }

            // mdb字段
            List<String> mdbFields = SWGDDatabase.getParamDbFields(dbTable, "SOURCE_NAME");
            // 数据库字段
            List<String> dbFields = SWGDDatabase.getParamDbFields(dbTable, "TARGET_NAME");

            // mdb表数据
            MdbDatabase mdbDatabase = new MdbDatabase(path);
            ResultSet mdbResultSet = mdbDatabase.getTableData(mdbTableName);

            Integer mdbCount = mdbDatabase.getTableCount(mdbTableName);
            if (mdbCount != null) {
                comparisonMessage.mdbCount = mdbCount;
            }

            try {
                while (mdbResultSet.next()) {
                    // mdb单条数据
                    List<String> dataList = new ArrayList<>();

                    for (String field : mdbFields) {
                        String fieldValue = MdbDatabase.getFiledData(mdbResultSet, field);

                        if (fieldValue == null) {
                            fieldValue = "";
                        }

                        dataList.add(fieldValue.replaceAll("'", "''"));
                    }

                    SWGDDatabase swgdDatabase = new SWGDDatabase();
                    String sql = "SELECT * FROM SWGDPARA." + dbTable + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                    for (int j = 0; j < dataList.size(); j ++) {
                        String data = dataList.get(j);

                        if (!StringUtil.isEmptyAll(data)) {
                            String type = Database.getColumnTypeName(swgdDatabase.query("SELECT * FROM SWGDPARA." + dbTable + " WHERE ROWNUM <= 1"), dbFields.get(j));

                            if (StringUtil.isEmpty(type)) {
                                throw new ErrorException("表：" + dbTable + " - " + dbFields.get(j) + "未找到");
                            }

                            if (type.equals("TIMESTAMP")) {
                                sql = StringUtil.append(sql, " AND ", dbFields.get(j), " = ", "TO_DATE('" + data.substring(0, data.length() - 7) + "','yyyy-mm-dd hh24:mi:ss')");
                                continue;
                            }

                            System.out.println(mdbFields.get(j) + " " + type);

                            sql = StringUtil.append(sql, " AND ", dbFields.get(j), " = '", data, "'");
                        }
                    }

                    System.out.println(sql);

                    if (!swgdDatabase.query(sql).next()) {
                        comparisonMessage.flag = false;
                        comparisonMessage.message = StringUtil.append(comparisonMessage.message,"，" , sql);
                    }

                    swgdDatabase.close();
                }
            } catch (SQLException e) {
                throw new ErrorException(e.getMessage());
            } finally {
                mdbDatabase.close();
            }

            result.add(comparisonMessage);
        }

        return result;
    }

    /**
     * 导入数据比对
     *
     * @param multipartFile multipartFile
     *
     * @return List<ComparisonMessage>
     * */
    public List<ComparisonMessage> importComparison(String groupName, MultipartFile multipartFile) {
        File file = new File(Project.CACHE_PATH, DateUtil.getTime() + ".mdb");
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        FileUtil.createFile(file, inputStream);

        try {
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        // 设置权限
        ConsoleUtil.setChmod777(file.getAbsolutePath());

        try {
            return this.importComparison(groupName, file.getAbsolutePath());
        } finally {
            if (file.delete()) {
                LOGGER.info("已删除文件：" + file.getAbsolutePath());
            }
        }
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static ParamDbComparator getInstance() {
        return PARAM_DB_COMPARATOR;
    }

    /**
     * 比对信息
     *
     * @author ZJ
     * */
    private static final class ComparisonMessage {

        /**
         * 是否比对通过
         * */
        public boolean flag = true;

        /**
         * 数据库表名
         * */
        public String dbName = "";

        /**
         * 数据库数量
         * */
        public int dbCount = 0;

        /**
         * mdb表名
         * */
        public String mdbName = "";

        /**
         * mdb数量
         * */
        public int mdbCount = 0;

        /**
         * 信息
         * */
        public String message = "";

        @Override
        public String toString() {
            return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
        }

    }

}