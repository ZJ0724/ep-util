package com.easipass.util.entity;

import org.springframework.stereotype.Component;

/**
 * 响应
 *
 * @author ZJ
 * */
@Component
public class Response implements com.zj0724.springbootUtil.Response<Response> {

    /**
     * 类型
     * */
    private Object flag;

    /**
     * 错误代码
     * */
    private Object errorCode;

    /**
     * 错误信息
     * */
    private Object errorMessage;

    /**
     * 数据
     * */
    private Object data;

    /**
     * T
     * */
    private static final String FLAG_T = "T";

    /**
     * F
     * */
    private static final String FLAG_F = "F";

    /**
     * 构造函数
     *
     * @param flag 类型
     * @param errorCode 错误代码
     * @param errorMsg 错误信息
     * @param data 数据
     * */
    private Response(Object flag, Object errorCode, Object errorMsg, Object data) {
        this.flag = flag;
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
        this.data = data;
    }

    /**
     * 构造函数
     * */
    public Response() {}

    /**
     * 返回正确
     *
     * @param data 数据
     *
     * @return 响应
     * */
    public static Response returnTrue(Object data) {
        return new Response(FLAG_T, null, null, data);
    }

    /**
     * 返回正确
     *
     * @return 响应
     * */
    public static Response returnTrue() {
        return new Response(FLAG_T, null, null, null);
    }

    /**
     * 返回错误
     *
     * @param errorMsg 错误信息
     *
     * @return 响应
     * */
    public static Response returnFalse(Object errorMsg) {
        return new Response(FLAG_F, null, errorMsg, null);
    }

    /**
     * 后台错误
     *
     * @return 响应
     * */
    public static Response error(String errorMessage) {
        return new Response(FLAG_F, 500, errorMessage, null);
    }

    /**
     * 请求参数有误或缺失
     *
     * @param errorMessage 错误信息
     *
     * @return 响应
     * */
    public static Response paramMissing(String errorMessage) {
        return new Response(FLAG_F, 400, errorMessage, null);
    }

    /**
     * 配置文件错误
     *
     * @return 响应
     * */
    public static Response configError() {
        return new Response(FLAG_F, 501, "配置文件错误，请不要手动修改配置文件", null);
    }

    @Override
    public Response paramError(String s) {
        return paramMissing(s);
    }

    /**
     * get, set
     * */
    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static String getFlagT() {
        return FLAG_T;
    }

    public static String getFlagF() {
        return FLAG_F;
    }

    @Override
    public String toString() {
        return "Response{" +
                "flag=" + flag +
                ", errorCode=" + errorCode +
                ", errorMessage=" + errorMessage +
                ", data=" + data +
                '}';
    }

}