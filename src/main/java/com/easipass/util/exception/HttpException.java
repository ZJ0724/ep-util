package com.easipass.util.exception;

import com.easipass.util.core.BaseException;

/**
 * http请求异常
 *
 * @author ZJ
 * */
public class HttpException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public HttpException(String message) {
        super(message);
    }

}