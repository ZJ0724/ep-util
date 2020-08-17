package com.easipass.util.core.exception;

import com.easipass.util.core.BaseException;

/**
 * 配置异常
 *
 * @author ZJ
 * */
public final class ConfigException extends BaseException {

    /**
     * 构造函数
     *
     * @param message 错误信息
     * */
    public ConfigException(String message) {
        super(message);
    }

}