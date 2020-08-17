package com.easipass.util.core.exception;

import com.easipass.util.core.BaseException;

/**
 * 回执异常
 *
 * @author ZJ
 * */
public final class CusResultException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public CusResultException(String message) {
        super(message);
    }

}