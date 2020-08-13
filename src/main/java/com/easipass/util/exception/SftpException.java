package com.easipass.util.exception;

import com.easipass.util.core.BaseException;

/**
 * sftp异常
 *
 * @author ZJ
 * */
public final class SftpException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     * */
    public SftpException(String message) {
        super(message);
    }

}