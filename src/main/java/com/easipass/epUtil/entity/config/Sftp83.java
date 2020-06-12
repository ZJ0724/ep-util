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
    private final static Sftp83 SFTP_83 = new Sftp83();

    /**
     * 构造函数
     * */
    private Sftp83() {}

    /**
     * get
     * */
    public String getUrl() {
        return url;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUploadPath() {
        return uploadPath;
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