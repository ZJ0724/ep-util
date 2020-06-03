package com.easipass.epUtil.exception;

public class SftpException extends BaseException {

    private SftpException(String message) {
        super(message);
    }

    /**
     * 连接失败
     * */
    public static SftpException connectFail(String url) {
        return new SftpException("sftp:" + url + "连接失败");
    }

}