package com.easipass.epUtil.enumeration;

public enum SystemOSEnum {

    windows("Windows"),
    linux("Linux");

    private String name;

    private SystemOSEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
