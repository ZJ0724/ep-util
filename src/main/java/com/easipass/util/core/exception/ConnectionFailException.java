package com.easipass.util.core.exception;

import com.easipass.util.core.BaseException;

/**
 * 连接失败异常
 *
 * @author ZJ
 * */
public final class ConnectionFailException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    public ConnectionFailException(String message) {
        super(message);
    }

}