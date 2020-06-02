package com.easipass.epUtil.config;

public enum ResponseFlagConfig {

    // 响应正确
    TRUE("T"),
    // 响应错误
    FALSE("F");

    private String flag;

    ResponseFlagConfig(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}