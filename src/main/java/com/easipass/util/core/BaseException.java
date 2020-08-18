package com.easipass.util.core;

/**
 * 基础异常类
 *
 * @author ZJ
 * */
public abstract class BaseException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    protected BaseException(String message) {
        super(message);
    }

}