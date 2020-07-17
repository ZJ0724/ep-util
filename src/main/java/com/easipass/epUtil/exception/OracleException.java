package com.easipass.epUtil.exception;

/**
 * 数据库异常
 *
 * @author ZJ
 * */
public final class OracleException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    public OracleException(String message) {
        super(message);
    }

}