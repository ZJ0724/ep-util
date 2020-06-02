package com.easipass.epUtil.exception;

public class ConfigException extends BaseException {

    private ConfigException(String message) {
        super(message);
    }

    /**
     * 配置文件异常
     * */
    public static ConfigException configFileException() {
        throw new ConfigException("配置文件错误！");
    }

}