package com.easipass.EpUtilServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SWGDConfig {

    @Value("${SWGD.url}")
    private String url;

    @Value("${SWGD.port}")
    private int port;

    @Value("${SWGD.sid}")
    private String sid;

    @Value("${SWGD.username}")
    private String username;

    @Value("${SWGD.password}")
    private String password;

    public String getUrl() {
        return url;
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

}
