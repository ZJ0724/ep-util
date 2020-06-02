package com.easipass.epUtil.exception;

public class OracleException extends BaseException {

    private OracleException(String message) {
        super(message);
    }

    /**
     * 数据库异常
     * */
    public static OracleException getOracleException(String message) {
        return new OracleException(message);
    }

}