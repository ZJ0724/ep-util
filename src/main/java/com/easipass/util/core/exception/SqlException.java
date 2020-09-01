package com.easipass.util.core.exception;

import com.easipass.util.core.BaseException;

/**
 * sql异常
 *
 * @author ZJ
 * */
public final class SqlException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public SqlException(String message) {
        super(message);
    }

}