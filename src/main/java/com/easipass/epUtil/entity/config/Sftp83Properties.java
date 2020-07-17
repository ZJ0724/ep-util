package com.easipass.epUtil.entity.config;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.resources.config.Sftp83PropertiesResource;

/**
 * sftp83配置
 *
 * @author ZJ
 * */
public final class Sftp83Properties extends Config {

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
     * 上传路径
     * */
    @Key
    private String uploadPath;

    /**
     * 单例
     */
    private static Sftp83Properties SFTP_83;

    /**
     * 构造函数
     * */
    private Sftp83Properties() {
        super(Sftp83PropertiesResource.getInstance());
    }

    /**
     * 获取单例
     *
     * @return 单例
     * */
    public static Sftp83Properties getInstance() {
        if (SFTP_83 == null) {
            SFTP_83 = new Sftp83Properties();
        }

        return SFTP_83;
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

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
        this.save();
    }

    @Override
    public String toString() {
        return "Sftp83Properties{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }

}