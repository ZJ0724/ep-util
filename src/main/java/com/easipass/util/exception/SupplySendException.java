package com.easipass.util.exception;

import com.easipass.util.core.BaseException;

/**
 * 第三方发送异常
 *
 * @author ZJ
 * */
public class SupplySendException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public SupplySendException(String message) {
        super(message);
    }

}