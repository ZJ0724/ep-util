package com.easipass.epUtil.exception;

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
    protected BaseException(String message) {
        super(message);
    }

}