package com.easipass.epUtil.exception;

/**
 * 错误异常
 *
 * @author ZJ
 * */
public final class ErrorException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public ErrorException(String message) {
        super(message);
    }

}