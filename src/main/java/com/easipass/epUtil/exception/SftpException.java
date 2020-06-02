package com.easipass.epUtil.exception;

import com.easipass.epUtil.entity.config.Sftp83Config;

public class SftpException extends BaseException {

    private SftpException(String message) {
        super(message);
    }

    /**
     * sftp83连接失败
     * */
    public static SftpException sftp83ConnectFail() {
        return new SftpException("sftp:" + Sftp83Config.getSftp83Config().getUrl() + "连接失败");
    }

}