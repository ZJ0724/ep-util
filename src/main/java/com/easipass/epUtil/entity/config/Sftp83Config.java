package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.annotation.Config;
import com.easipass.epUtil.annotation.Value;

@Config("sftp83")
public class Sftp83Config extends com.easipass.epUtil.entity.Config {

    @Value("url")
    private String url;

    @Value("port")
    private int port;

    @Value("username")
    private String username;

    @Value("password")
    private String password;

    @Value("uploadPath")
    private String uploadPath;

    private final static Sftp83Config sftp83Config = new Sftp83Config();

    private Sftp83Config() {}

    public static Sftp83Config getSftp83Config() {
        return Sftp83Config.sftp83Config;
    }

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

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public String toString() {
        return "Sftp83Config{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }

}