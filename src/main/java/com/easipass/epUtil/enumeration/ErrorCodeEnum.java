package com.easipass.epUtil.enumeration;

public enum ErrorCodeEnum {

    ERROR(500, "后台错误"),
    PARAM_ERROR(400, "请求参数有误或缺失");

    private Object errorCode;

    private Object errorMsg;

    ErrorCodeEnum(Object errorCode, Object errorMsg) {
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
