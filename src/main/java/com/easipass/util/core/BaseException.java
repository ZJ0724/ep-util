package com.easipass.util.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础异常类
 *
 * @author ZJ
 * */
public abstract class BaseException extends RuntimeException {

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(BaseException.class);

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    protected BaseException(String message) {
        super(message);
        log.info(message, this);
    }

}