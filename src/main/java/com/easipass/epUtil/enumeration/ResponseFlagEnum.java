package com.easipass.epUtil.enumeration;

public enum ResponseFlagEnum {

    // 响应正确
    TRUE("T"),
    // 响应错误
    FALSE("F");

    private String flag;

    ResponseFlagEnum(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
