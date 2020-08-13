package com.easipass.util.core.VO;

import com.easipass.util.core.config.Sftp83Properties;
import com.easipass.util.util.ClassUtil;

/**
 * sftp83ConfigVO
 *
 * @author ZJ
 * */
public final class Sftp83ConfigVO {

    /**
     * 地址
     * */
    private String url;

    /**
     * 端口
     * */
    private Integer port;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * 上传路径
     * */
    private String uploadPath;

    /**
     * 构造函数
     * */
    public Sftp83ConfigVO() {
        ClassUtil.assemblyData(Sftp83Properties.getInstance(), this);
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
        return "Sftp83ConfigVO{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }

}