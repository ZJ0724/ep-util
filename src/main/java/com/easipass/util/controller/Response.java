package com.easipass.util.controller;

public final class Response {

    private boolean flag;

    private Object errorCode;

    private Object errorMessage;

    private Object data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
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

    public static Response returnTrue(Object data) {
        Response response = new Response();
        response.flag = true;
        response.errorCode = null;
        response.errorMessage = null;
        response.data = data;
        return response;
    }

    public static Response returnTrue() {
        return returnTrue(null);
    }

    public static Response returnFalse(Object errorCode, Object errorMessage) {
        Response response = new Response();
        response.flag = false;
        response.errorCode = errorCode;
        response.errorMessage = errorMessage;
        response.data = null;
        return response;
    }

}