package com.easipass.util.core;

import com.easipass.util.api.websocket.BaseWebsocketApi;
import com.easipass.util.core.VO.CusMessageComparisonVO;
import com.easipass.util.core.exception.ErrorException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CusMessage.class);

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
     * 删除报文
     * */
    public void delete() {
        for (CusMessage cusMessage : CUS_MESSAGES_LIST) {
            if (cusMessage.getId().equals(this.getId())) {
                CUS_MESSAGES_LIST.remove(cusMessage);
                log.info("删除报文: {}", this.getId());
                return;
            }
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
     * 添加报文
     *
     * @param cusMessage 报文
     * */
    public static void push(CusMessage cusMessage) {
        CUS_MESSAGES_LIST.add(cusMessage);
        log.info("添加报文: {}", cusMessage.getId());
    }

    /**
     * 节点映射
     *
     * @author ZJ
     * */
    protected static final class NodeMapping {

        /**
         * 节点
         * */
        private final String node;

        /**
         * 节点名
         * */
        private final String nodeName;

        /**
         * 节点值
         * */
        private String nodeValue;

        /**
         * 数据库对应字段
         * */
        private final String dbField;

        /**
         * 数据库值
         * */
        private String dbValue;

        /**
         * 是否不能为空
         * */
        private final boolean notNull;

        /**
         * 构造函数
         *
         * @param node 节点
         * @param nodeName 节点名
         * @param dbField 数据库对应字段
         * @param notNull 是否不能为空
         * */
        public NodeMapping(String node, String nodeName, String dbField, boolean notNull) {
            this.node = node;
            this.nodeName = node + "[" + nodeName + "]";
            this.dbField = dbField;
            this.notNull = notNull;
        }

        /**
         * 构造函数
         *
         * @param node 节点
         * @param nodeName 节点名
         * @param dbField 数据库对应字段
         * */
        public NodeMapping(String node, String nodeName, String dbField) {
            this(node, nodeName, dbField, false);
        }

        /**
         * get, set
         * */
        public String getNode() {
            return node;
        }

        public String getNodeName() {
            return nodeName;
        }

        public String getNodeValue() {
            return nodeValue;
        }

        public String getDbField() {
            return dbField;
        }

        public String getDbValue() {
            return dbValue;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNodeValue(String nodeValue) {
            this.nodeValue = nodeValue;
        }

        public void setDbValue(String dbValue) {
            this.dbValue = dbValue;
        }

        /**
         * 加载数据
         * @param element 元素
         * @param resultSet 数据库数据
         * */
        public void loadData(Element element, ResultSet resultSet) {
            this.nodeValue = getNodeValue(element, this.node);
            this.dbValue = getDbValue(resultSet, this.dbField);
        }

        /**
         * 比对
         *
         * @param baseWebsocketApi websocket服务
         * */
        public void comparison(BaseWebsocketApi baseWebsocketApi) {
            // 如果dbField是null的话，不进行比对
            if (this.dbField == null) {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonNullType(this.nodeName));
                return;
            }

            if (this.nodeValue == null) {
                this.nodeValue = "";
            }
            if (this.dbValue == null) {
                this.dbValue = "";
            }

            // 校验必填
            if (this.notNull && "".equals(this.nodeValue)) {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonFalseType(this.nodeName));
                return;
            }

            if (nodeValue.equals(dbValue)) {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonTrueType(this.nodeName));
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonFalseType(this.nodeName));
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
        public static String getNodeValue(Element element, String elementName) {
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
        public static String getDbValue(ResultSet resultSet, String value) {
            if (value == null || "?".equals(value)) {
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
         * 检查数据库数据
         *
         * @param resultSet resultSet
         * @param message message
         * @param baseWebsocketApi baseWebsocketApi
         *
         * @return 数据不为NULl这返回true
         * */
        public static boolean checkResultSet(ResultSet resultSet, String message, BaseWebsocketApi baseWebsocketApi) {
            try {
                if (resultSet == null || !resultSet.next()) {
                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getErrorType("数据库" + message + "数据不存在"));
                    return false;
                }
            } catch (SQLException e) {
                throw new ErrorException(e.getMessage());
            }

            return true;
        }

        /**
         * 获取ieFlag
         *
         * @param ieFlag ieFlag
         *
         * @return ieFlag
         * */
        public static String getIeFlag(String ieFlag) {
            switch (ieFlag) {
                case "0" : case "2" : case "4" : case "6" : case "8" : case "A" :
                    return "E";

                case "1" : case "3" : case "5" : case "7" : case "9" : case "B" :
                case "D" :  case "F" :
                    return "I";
            }

            return null;
        }

    }

}