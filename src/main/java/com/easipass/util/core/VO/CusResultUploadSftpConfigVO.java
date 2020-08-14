package com.easipass.util.core.VO;

/**
 * sftp83ConfigVO
 *
 * @author ZJ
 * */
public final class CusResultUploadSftpConfigVO extends AbstractVO {

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
        return "CusResultUploadSftpConfigVO{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                '}';
    }

}