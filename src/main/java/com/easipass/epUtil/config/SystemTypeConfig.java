package com.easipass.epUtil.config;

public enum SystemTypeConfig {

    windows("Windows"),

    linux("Linux");

    private String name;

    SystemTypeConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}