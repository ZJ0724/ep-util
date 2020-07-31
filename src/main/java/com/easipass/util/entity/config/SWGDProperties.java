package com.easipass.util.entity.config;

import com.easipass.util.entity.Config;
import com.easipass.util.entity.resources.config.SWGDPropertiesResource;

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
    private static SWGDProperties SWGD;

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
        if (SWGD == null) {
            SWGD = new SWGDProperties();
        }

        return SWGD;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.commit();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
        this.commit();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
        this.commit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.commit();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.commit();
    }

    @Override
    public String toString() {
        return "SWGDProperties{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}