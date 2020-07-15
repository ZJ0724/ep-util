package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.resources.config.SWGDPropertiesResource;

/**
 * SWGD配置
 *
 * @author ZJ
 * */
public final class SWGDProperties extends Config {

    /**
     * 地址
     * */
    @Key
    private String url;

    /**
     * 端口
     * */
    @Key
    private Integer port;

    /**
     * sid
     * */
    @Key
    private String sid;

    /**
     * 用户名
     * */
    @Key
    private String username;

    /**
     * 密码
     * */
    @Key
    private String password;

    /**
     * 单例
     */
    private final static SWGDProperties SWGD = new SWGDProperties();

    /**
     * 构造函数
     * */
    private SWGDProperties() {
        super(SWGDPropertiesResource.getInstance());
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static SWGDProperties getInstance() {
        return SWGD;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.save();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
        this.save();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
        this.save();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.save();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.save();
    }

}