package com.easipass.epUtil.entity.config;

public class Sftp83 extends AbstractConfig {

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
     * 单例
     */
    public final static Sftp83 SFTP_83 = new Sftp83();

    /**
     * 构造函数
     * */
    private Sftp83() {}

    /**
     * get,set
     * */
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

    /**
     * 获取单例
     * */
    public static Sftp83 getSftp83() {
        return SFTP_83;
    }

    @Override
    public String toString() {
        return "Sftp83{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }

}