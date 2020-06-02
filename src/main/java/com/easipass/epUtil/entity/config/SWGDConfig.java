package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.annotation.Value;
import com.easipass.epUtil.entity.Config;

@com.easipass.epUtil.annotation.Config("SWGD")
public class SWGDConfig extends Config {

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

    private final static SWGDConfig swgdConfig = new SWGDConfig();

    private SWGDConfig() {}

    public static SWGDConfig getSWGDConfig() {
        return SWGDConfig.swgdConfig;
    }

    public String getUrl() {
        return this.url;
    }

    public int getPort() {
        return port;
    }

    public String getSid() {
        return sid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "SWGDConfig{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}