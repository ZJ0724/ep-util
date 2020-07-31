package com.easipass.util.exception;

/**
 * 基础异常类
 *
 * @author ZJ
 * */
public class BaseException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    public BaseException(String message) {
        super(message);
    }

}