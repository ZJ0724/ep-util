package com.easipass.epUtil.entity.config;

public class Swgd extends AbstractConfig {

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
     * 单例
     */
    private final static Swgd SWGD = new Swgd();

    /**
     * 构造函数
     * */
    private Swgd() {}

    /**
     * get
     * */
    public String getUrl() {
        return url;
    }

    public Integer getPort() {
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

    /**
     * 获取单例
     * */
    public static Swgd getSWGD() {
        return SWGD;
    }

    @Override
    public String toString() {
        return "Swgd{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", sid='" + sid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}