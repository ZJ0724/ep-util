package com.easipass.util.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 错误异常
 *
 * @author ZJ
 * */
public final class ErrorException extends RuntimeException {

    /**
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(ErrorException.class);

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public ErrorException(String message) {
        super(message);
        log.error(message, this);
    }

}