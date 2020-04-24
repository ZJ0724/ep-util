package com.easipass.EpUtilServer.entity;

import com.easipass.EpUtilServer.enumeration.ResponseEnum;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;

public class Response {

    private Object flag;
    private Object errorCode;
    private Object errorMsg;
    private Object data;

    public Response(Object flag, Object errorCode, Object errorMsg, Object data) {
        this.flag = flag;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

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

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
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
        return new Response(ResponseFlagEnum.TRUE.getFlag(), "", "", data);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(Object errorMsg) {
        return new Response(ResponseFlagEnum.FALSE.getFlag(), "", errorMsg, null);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(ResponseEnum responseEnum) {
        return new Response(ResponseFlagEnum.FALSE.getFlag(), responseEnum.getErrorCode(), responseEnum.getErrorMsg(), null);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(ResponseEnum responseEnum, String errorMsg) {
        return new Response(ResponseFlagEnum.FALSE.getFlag(), responseEnum.getErrorCode(), errorMsg, null);
    }

}
