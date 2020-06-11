package com.easipass.epUtil.config;

public enum DaKaSignConfig {

    /**
     * 开始打卡
     * */
    START("1"),

    /**
     * 停止打卡
     * */
    STOP("0");

    /**
     * 标记数据
     */
    private final String data;

    /**
     * 构造函数
     * */
    DaKaSignConfig(String data) {
        this.data = data;
    }

    /**
     * get
     * */
    public String getData() {
        return data;
    }

}