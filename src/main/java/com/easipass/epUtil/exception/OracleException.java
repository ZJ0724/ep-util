package com.easipass.epUtil.exception;

public class OracleException extends BaseException {

    private OracleException(String message) {
        super(message);
    }

    /**
     * 连接失败
     * */
    public static OracleException connectFail(String url) {
        return new OracleException("oracle: " + url + "连接失败!");
    }

    /**
     * 查询错误
     * */
    public static OracleException queryError(String message) {
        return new OracleException(message);
    }

}