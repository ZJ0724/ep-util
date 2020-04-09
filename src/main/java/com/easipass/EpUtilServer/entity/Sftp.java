package com.easipass.EpUtilServer.entity;

import com.easipass.EpUtilServer.exception.ErrorException;
import com.jcraft.jsch.*;
import java.io.InputStream;
import java.util.Properties;

public class Sftp {

    /**
     * 地址
     * */
    private String url = "";

    /**
     * 端口
     * */
    private int port = 0;

    /**
     * 用户名
     * */
    private String username = "";

    /**
     * 密码
     * */
    private String password = "";

    /**
     * sftp连接
     * */
    private ChannelSftp channelSftp = null;

    /**
     * session通道
     * */
    private Session session = null;

    /**
     * 是否已连接
     * */
    private boolean isConnect = false;

    public Sftp(String url, int port, String username, String password) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
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

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public void setChannelSftp(ChannelSftp channelSftp) {
        this.channelSftp = channelSftp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * 连接
     * */
    public boolean connect() {
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
            return false;
        }

        this.isConnect = true;
        return true;
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
    }

    /**
     * 上传文件
     *
     * @param path 上传路径
     * @param name 上传文件名
     * @param inputStream 字节流
     */
    public void uploadFile(String path, String name, InputStream inputStream) {
        if (!this.isConnect) {
            throw new ErrorException("sftp未连接");
        }

        try {
            channelSftp.cd(path);
            channelSftp.put(inputStream, name);
        } catch (SftpException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}
