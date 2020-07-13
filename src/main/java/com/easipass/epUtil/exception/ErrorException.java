package com.easipass.epUtil.exception;

public class ErrorException extends RuntimeException {

    public ErrorException(String message) {
        super(message);
    }

    /**
     * 获取错误异常
     * */
    public static ErrorException getErrorException() {
        return new ErrorException("error");
    }

    /**
     * 获取错误异常
     * */
    public static ErrorException getErrorException(String errorMsg) {
        return new ErrorException("bug:" + errorMsg);
    }

}