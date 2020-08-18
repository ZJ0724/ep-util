package com.easipass.util.core.DTO.config;

import com.easipass.util.core.DTO.AbstractDTO;

/**
 * SWGD数据库DTO
 *
 * @author ZJ
 * */
public final class SWGDDatabaseConfigDTO extends AbstractDTO {

    /**
     * 驱动类
     * */
    private String driverClass;

    /**
     * url
     * */
    private String url;

    /**
     * username
     * */
    private String username;

    /**
     * password
     * */
    private String password;


    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SWGDDatabaseConfigDTO{" +
                "driverClass='" + driverClass + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}