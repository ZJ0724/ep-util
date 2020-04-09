package com.easipass.EpUtilServer.entity;

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
        return new Response("T", "", "", data);
    }

    /**
     * 返回错误
     * */
    public static Response returnFalse(Object errorCode, Object errorMsg) {
        return new Response("F", errorCode, errorMsg, null);
    }

}
