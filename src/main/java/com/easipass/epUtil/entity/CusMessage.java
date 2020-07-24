package com.easipass.epUtil.entity;

import com.easipass.epUtil.api.websocket.BaseWebsocketApi;
import com.easipass.epUtil.entity.VO.CusMessageComparisonVO;
import com.easipass.epUtil.exception.ErrorException;
import org.dom4j.Element;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 抽象报文
 *
 * @author ZJ
 * */
public abstract class CusMessage {

    /**
     * 报文id
     * */
    private final String id;

    /**
     * 报文集合
     * */
    private static final List<CusMessage> CUS_MESSAGES_LIST = new ArrayList<>();

    /**
     * 日志
     * */
    private static final Log LOG = Log.getLog();

    {
        this.id = new Date().getTime() + "";
    }

    /**
     * 比对
     *
     * @param baseWebsocketApi websocket服务
     * */
    public abstract void comparison(BaseWebsocketApi baseWebsocketApi);

    /**
     * 获取id
     * */
    public final String getId() {
        return this.id;
    }

    /**
     * 添加报文
     * */
    public void push() {
        CUS_MESSAGES_LIST.add(this);
        LOG.info("添加报文: " + this.getId());
    }

    /**
     * 删除报文
     * */
    public void delete() {
        for (CusMessage cusMessage : CUS_MESSAGES_LIST) {
            if (cusMessage.getId().equals(this.getId())) {
                CUS_MESSAGES_LIST.remove(cusMessage);
                LOG.info("删除报文: " + this.getId());
                return;
            }
        }
    }

    /**
     * 获取节点内容
     *
     * @param element 父节点
     * @param elementName 子节点名
     *
     * @return 节点值
     * */
    protected static String getNodeValue(Element element, String elementName) {
        Element cElement = element.element(elementName);

        if (cElement == null) {
            return "";
        }

        return cElement.getText();
    }

    /**
     * 获取dbValue
     *
     * @param resultSet 数据库数据
     * @param value 字段名
     *
     * @return dbValue
     * */
    protected static String getDbValue(ResultSet resultSet, String value) {
        if (value == null) {
            return "";
        }

        String result;

        try {
            result = resultSet.getString(value);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage() + ": " + value);
        }

        return result;
    }

    /**
     * 获取key1
     *
     * @param key key
     *
     * @return key1
     * */
    protected static String getKey1(String key) {
        // [索引
        int index = key.indexOf("[");
        // 返回的key
        String result = key;

        if (index != -1) {
            result = result.substring(0, index);
        }

        return result;
    }

    /**
     * 获取keyValue
     *
     * @param element 元素
     * @param map 映射
     * @param key 映射key
     * @param resultSet 数据库数据
     * @param message 信息
     * @param baseWebsocketApi websocket服务
     *
     * @return keyValue
     * */
    protected static MapKeyValue getKeyValue(Element element, Map<String, String> map, String key, ResultSet resultSet, String message, BaseWebsocketApi baseWebsocketApi) {
        if (resultSet == null) {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getErrorType("数据库" + message + "不存在"));
            return null;
        }

        String key1 = getKey1(key);
        String value = map.get(key);
        String nodeValue = getNodeValue(element, key1);
        String dbValue = getDbValue(resultSet, value);

        return new MapKeyValue(key, key1, value, nodeValue, dbValue);
    }

    /**
     * 比对
     *
     * @param mapKeyValue mapKeyValue
     * @param baseWebsocketApi websocket服务
     * */
    protected static void comparison(MapKeyValue mapKeyValue, BaseWebsocketApi baseWebsocketApi) {
        if (mapKeyValue == null) {
            return;
        }

        String key = mapKeyValue.getKey();
        String key1 = mapKeyValue.getKey1();
        String value = mapKeyValue.getValue();
        String nodeValue = mapKeyValue.getNodeValue();
        String dbValue = mapKeyValue.getDbValue();

        // 如果value是null的话，不进行比对
        if (value == null) {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonNullType(key1));
            return;
        }

        if (nodeValue == null) {
            nodeValue = "";
        }
        if (dbValue == null) {
            dbValue = "";
        }

        if (nodeValue.equals(dbValue)) {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonTrueType(key));
        } else {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonFalseType(key));
        }
    }

    /**
     * 获取报文
     *
     * @param id id
     *
     * @return 报文
     * */
    public static CusMessage get(String id) {
        for (CusMessage cusMessage : CUS_MESSAGES_LIST) {
            if (cusMessage.getId().equals(id)) {
                return cusMessage;
            }
        }

        return null;
    }

    /**
     * MapKeyValue
     *
     * @author ZJ
     * */
    protected static final class MapKeyValue {

        /**
         * key
         * */
        private String key;

        /**
         * key1
         * */
        private String key1;

        /**
         * value
         * */
        private String value;

        /**
         * 节点值
         * */
        private String nodeValue;

        /**
         * 数据库值
         * */
        private String dbValue;

        /**
         * 构造函数
         *
         * @param key key
         * @param key1 key1
         * @param value value
         * @param nodeValue 节点值
         * @param dbValue 数据库值
         * */
        public MapKeyValue(String key, String key1, String value, String nodeValue, String dbValue) {
            this.key = key;
            this.key1 = key1;
            this.value = value;
            this.nodeValue = nodeValue;
            this.dbValue = dbValue;
        }

        /**
         * get, set
         * */
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getNodeValue() {
            return nodeValue;
        }

        public void setNodeValue(String nodeValue) {
            this.nodeValue = nodeValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public void setDbValue(String dbValue) {
            this.dbValue = dbValue;
        }

    }

}