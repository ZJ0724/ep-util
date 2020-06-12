package com.easipass.epUtil.component;

import com.easipass.epUtil.exception.ErrorException;
import com.jcraft.jsch.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Sftp {

    /**
     * 地址
     * */
    private String url;

    /**
     * 端口
     * */
    private int port;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * sftp连接
     * */
    private ChannelSftp channelSftp;

    /**
     * session通道
     * */
    private Session session;

    /**
     * 是否已连接
     * */
    private boolean isConnect = false;

    /**
     * 构造函数
     * */
    protected Sftp() {}

    /**
     * get,set
     * */
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

    /**
     * 连接
     * */
    public void connect() {
        try {
            JSch jSch = new JSch();
            this.session = jSch.getSession(username, url, port);
            this.session.setPassword(password);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            this.session.setConfig(properties);
            this.session.connect();
            this.channelSftp = (ChannelSftp)session.openChannel("sftp");
            this.channelSftp.connect();
        }catch (JSchException e) {
            throw com.easipass.epUtil.exception.SftpException.connectFail(this.url);
        }

        Log.getLog().info("sftp: " + this.getUrl() + "已连接!");
        this.isConnect = true;
    }

    /**
     * 关闭
     * */
    public void close() {
        if (this.session != null) {
            this.session.disconnect();
            this.session = null;
        }

        if (this.channelSftp != null) {
            this.channelSftp.disconnect();
            this.channelSftp = null;
        }

        Log.getLog().info("sftp: " + this.getUrl() + "已关闭!");
        this.isConnect = false;
    }

    /**
     * 上传内容
     *
     * @param path 上传路径
     * @param name 上传文件名
     * @param data 内容
     */
    public void upload(String path, String name, String data) {
        this.checkConnect();

        try {
            channelSftp.cd(path);
            InputStream inputStream = new ByteArrayInputStream(data.getBytes());
            channelSftp.put(inputStream, name);
        } catch (SftpException e) {
            throw ErrorException.getErrorException(e.getMessage());
        }
    }

    /**
     * 检查连接
     * */
    private void checkConnect() {
        if (!this.isConnect) {
            throw com.easipass.epUtil.exception.SftpException.connectFail(this.url);
        }
    }

}