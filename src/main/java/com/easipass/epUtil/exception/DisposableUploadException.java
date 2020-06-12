package com.easipass.epUtil.exception;

public class DisposableUploadException extends BaseException {

    protected DisposableUploadException(String message) {
        super(message);
    }

    /**
     * 一次上传异常
     *
     * @return 一次上传异常
     * */
    public static DisposableUploadException disposableUploadException(String message) {
        return new DisposableUploadException(message);
    }

}