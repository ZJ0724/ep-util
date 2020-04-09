package com.easipass.EpUtilServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sftp83Config {

    @Value("${sftp83.url}")
    private String url;

    @Value("${sftp83.port}")
    private int port;

    @Value("${sftp83.username}")
    private String username;

    @Value("${sftp83.password}")
    private String password;

    @Value("${sftp83.uploadPath}")
    private String uploadPath;

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

}
