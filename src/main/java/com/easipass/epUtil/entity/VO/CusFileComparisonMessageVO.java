package com.easipass.epUtil.entity.VO;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 报文比对消息VO
 *
 * @author ZJ
 * */
public final class CusFileComparisonMessageVO {

    /**
     * 类型
     *
     * 0: 标题信息
     * 1: 比对信息
     * 2: 错误信息
     * */
    private final String type;

    /**
     * 节点名
     * */
    private final String node;


    /**
     * 信息内容
     *
     * 如果是type为1, true就是比对通过，false就是比对失败，null就是不进行比对
     * */
    private final String message;

    /**
     * 构造函数
     *
     * @param type 类型
     * @param node 节点名
     * @param message 信息内容
     * */
    public CusFileComparisonMessageVO(String type, String node, String message) {
        this.type = type;
        this.message = message;
        this.node = node;
    }

    /**
     * 获取标题类型
     *
     * @param message 标题信息
     * */
    public static CusFileComparisonMessageVO getTitleType(String message) {
        return new CusFileComparisonMessageVO("0", null, message);
    }

    /**
     * 获取比对通过类型
     *
     * @param node 节点
     * */
    public static CusFileComparisonMessageVO getComparisonTrueType(String node) {
        return new CusFileComparisonMessageVO("1", node, "true");
    }

    /**
     * 获取比对失败类型
     *
     * @param node 节点
     * */
    public static CusFileComparisonMessageVO getComparisonFalseType(String node) {
        return new CusFileComparisonMessageVO("1", node, "false");
    }

    /**
     * 获取不进行比对类型
     *
     * @param node 节点
     * */
    public static CusFileComparisonMessageVO getComparisonNullType(String node) {
        return new CusFileComparisonMessageVO("1", node, "null");
    }

    /**
     * 获取错误信息类型
     *
     * @param message 错误信息
     * */
    public static CusFileComparisonMessageVO getErrorType(String message) {
        return new CusFileComparisonMessageVO("2", null, message);
    }

    @Override
    public String toString() {
        Map<String, String> result = new LinkedHashMap<>();

        result.put("type", this.type);
        result.put("node", this.node);
        result.put("message", this.message);

        return JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

}