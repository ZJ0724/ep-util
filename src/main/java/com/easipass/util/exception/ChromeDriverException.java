package com.easipass.util.exception;

import com.easipass.util.core.BaseException;

/**
 * 谷歌驱动异常
 *
 * @author ZJ
 * */
public final class ChromeDriverException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public ChromeDriverException(String message) {
        super(message);
    }

}