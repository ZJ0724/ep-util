package com.easipass.EpUtilServer.enumeration;

public enum ResponseEnum {

    error(500, "后台错误");

    private Object errorCode;

    private Object errorMsg;

    ResponseEnum(Object errorCode, Object errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
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

}
