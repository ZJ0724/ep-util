package com.easipass.util.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easipass.util.core.config.ParamDbMapping;
import com.easipass.util.core.database.MdbDatabase;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
     * 比对mdb文件
     *
     * @param path mdb文件路径
     *
     * @return 比对信息
     * */
    public List<ComparisonMessage> mdbComparison(String path) {
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        MdbDatabase mdbDatabase = new MdbDatabase(path);
        List<ComparisonMessage> result = new ArrayList<>();

        try {
            // 遍历配置
            List<ParamDbMapping.Row> rows = ParamDbMapping.getInstance().getRows();

            for (ParamDbMapping.Row row : rows) {
                ComparisonMessage comparisonMessage = new ComparisonMessage();
                // 数据库表
                String dbName = row.getDbName();
                Integer dbCount = swgdDatabase.getParamDbDataCount(dbName);
                // mdb表
                String mdbName = row.getMdbName();
                Integer mdbCount = mdbDatabase.getTableCount(mdbName);

                comparisonMessage.dbName = dbName;
                comparisonMessage.mdbName = mdbName;

                if (dbCount == null) {
                    comparisonMessage.message = StringUtil.append(comparisonMessage.message, "数据库" + dbName + "表不存在");
                } else {
                    comparisonMessage.dbCount = dbCount;
                }

                if (mdbCount == null) {
                    String message = "mdb文件" + mdbName + "表不存在";

                    if (!StringUtil.isEmpty(comparisonMessage.message)) {
                        comparisonMessage.message = StringUtil.append(comparisonMessage.message, "，", message);
                    } else {
                        comparisonMessage.message = StringUtil.append(comparisonMessage.message, message);
                    }
                } else {
                    comparisonMessage.mdbCount = mdbCount;
                }

                if (dbCount != null && mdbCount != null) {
                    if (dbCount.equals(mdbCount)) {
                        comparisonMessage.flag = true;
                        comparisonMessage.message = StringUtil.append(comparisonMessage.message, "OK");
                    } else {
                        comparisonMessage.message = StringUtil.append(comparisonMessage.message, "ERROR");
                    }
                }

                result.add(comparisonMessage);
            }
        } finally {
            swgdDatabase.close();
            mdbDatabase.close();
        }

        return result;
    }

    /**
     * 比对mdb文件
     *
     * @param multipartFile multipartFile
     *
     * @return List<ComparisonMessage>
     * */
    public List<ComparisonMessage> mdbComparison(MultipartFile multipartFile) {
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

        List<ComparisonMessage> result = this.mdbComparison(file.getAbsolutePath());

        Project.THREAD_POOL_EXECUTOR.execute(() -> {
            if (!file.delete()) {
                throw new ErrorException("删除mdb文件失败");
            }
        });

        return result;
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
        public boolean flag = false;

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