package com.easipass.util.core.VO;

import com.easipass.util.core.Config.SWGDDatabaseProperties;
import com.easipass.util.core.util.ClassUtil;

/**
 * SWGDConfigVO
 *
 * @author ZJ
 * */
public final class SWGDConfigVO {

    /**
     * 地址
     * */
    private String url;

    /**
     * 端口
     * */
    private Integer port;

    /**
     * sid
     * */
    private String sid;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * 构造函数
     * */
    public SWGDConfigVO() {
        ClassUtil.assemblyData(SWGDDatabaseProperties.getInstance(), this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
        return "SWGDConfigVO{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}