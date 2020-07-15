package com.easipass.epUtil.exception;

import com.zj0724.uiAuto.exception.BaseException;

/**
 * 上传回执异常
 *
 * @author ZJ
 * */
public final class UploadCusResultException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public UploadCusResultException(String message) {
        super(message);
    }

}