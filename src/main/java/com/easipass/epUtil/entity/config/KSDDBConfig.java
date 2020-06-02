package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.annotation.Config;
import com.easipass.epUtil.annotation.Value;

@Config("KSDDB")
public class KSDDBConfig extends com.easipass.epUtil.entity.Config {

    @Value("url")
    private String url;

    @Value("port")
    private int port;

    @Value("sid")
    private String sid;

    @Value("username")
    private String username;

    @Value("password")
    private String password;

    private final static KSDDBConfig ksddbConfig = new KSDDBConfig();

    private KSDDBConfig() {}

    public String getUrl() {
        return url;
    }

    public static KSDDBConfig getKSDDBConfig() {
        return KSDDBConfig.ksddbConfig;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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
        return "KSDDBConfig{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}