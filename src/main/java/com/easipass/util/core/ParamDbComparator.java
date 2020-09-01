package com.easipass.util.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easipass.util.api.websocket.BaseWebsocketApi;
import com.easipass.util.core.exception.WarningException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 参数库比对器
 *
 * @author ZJ
 * */
public abstract class ParamDbComparator {

    /**
     * websocket
     * */
    private BaseWebsocketApi baseWebsocketApi;

    /**
     * 比对
     *
     * @param groupName 分组名
     * @param mdbPath mdb文件路径
     *
     * @return 比对信息
     * */
    public abstract ComparisonMessage comparison(String groupName, String mdbPath);

    /**
     * 添加websocket
     *
     * @param baseWebsocketApi baseWebsocketApi
     * */
    public final void addWebsocket(BaseWebsocketApi baseWebsocketApi) {
        this.baseWebsocketApi = baseWebsocketApi;
    }

    /**
     * 发送数据
     *
     * @param data 数据
     * */
    public final void sendData(String data) {
        if (this.baseWebsocketApi != null) {
            baseWebsocketApi.sendMessage(data);
        }
    }

    /**
     * 删除websocket
     * */
    public final void deleteWebsocket() {
        this.baseWebsocketApi = null;
    }

    /**
     * 兼容日期
     *
     * @param date date
     *
     * @return date
     * */
    protected static String parseDate(String date) throws WarningException {
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
            throw new WarningException("日期格式不存在: " + date);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date1);
    }

    /**
     * 比对信息
     *
     * @author ZJ
     * */
    public static final class ComparisonMessage {

        /**
         * 状态
         * */
        public boolean flag = true;

        /**
         * 信息
         * */
        public String message = "";

        /**
         * 当前比对进度
         * */
        public String currentProgress = "0%";

        /**
         * 一共多少个表
         * */
        public int AllTable;

        /**
         * 比对完成信息
         * */
        public final List<TableFinishMessage> finishMessages = new ArrayList<>();

        @Override
        public String toString() {
            return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
        }

        /**
         * 单个表比对完成信息
         *
         * @author ZJ
         * */
        public static final class TableFinishMessage {

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
            public final List<String> messages = new ArrayList<>();

            @Override
            public String toString() {
                return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
            }

        }

    }

}