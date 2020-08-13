package com.easipass.util.exception;

import com.easipass.util.core.BaseException;

/**
 * 报文异常
 *
 * @author ZJ
 * */
public final class CusMessageException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 信息
     * */
    public CusMessageException(String message) {
        super(message);
    }

}