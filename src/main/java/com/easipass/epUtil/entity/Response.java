package com.easipass.epUtil.entity;

import com.easipass.epUtil.config.ErrorCodeConfig;
import com.easipass.epUtil.config.ResponseFlagConfig;

public class Response implements com.zj0724.springbootUtil.Response<Response> {

    private Object flag;
    private Object errorCode;
    private Object errorMessage;
    private Object data;

    public Response() {}

    public Response(Object flag, Object errorCode, Object errorMsg, Object data) {
        this.flag = flag;
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
        this.data = data;
    }

    /**
     * get,set
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

    /**
     * 返回正确
     * */
    public static Response returnTrue(Object data) {
        return new Response(ResponseFlagConfig.TRUE.getFlag(), "", "", data);
    }

    /**
     * 返回正确
     * */
    public static Response returnTrue() {
        return new Response(ResponseFlagConfig.TRUE.getFlag(), "", "", null);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(Object errorMsg) {
        return new Response(ResponseFlagConfig.FALSE.getFlag(), "", errorMsg, null);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(ErrorCodeConfig errorCodeConfig) {
        return new Response(ResponseFlagConfig.FALSE.getFlag(), errorCodeConfig.getErrorCode(), errorCodeConfig.getErrorMsg(), null);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(ErrorCodeConfig errorCodeConfig, String errorMsg) {
        return new Response(ResponseFlagConfig.FALSE.getFlag(), errorCodeConfig.getErrorCode(), errorMsg, null);
    }

    @Override
    public Response paramError(String s) {
        return new Response(ResponseFlagConfig.FALSE.getFlag(), ErrorCodeConfig.PARAM_ERROR.getErrorCode(), s, null);
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