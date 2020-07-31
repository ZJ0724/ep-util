package com.easipass.util.entity;

import com.easipass.util.exception.ErrorException;
import com.jcraft.jsch.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * ftp
 *
 * @author ZJ
 * */
public class Sftp {

    /**
     * 地址
     * */
    private final String url;

    /**
     * 端口
     * */
    private final int port;

    /**
     * 用户名
     * */
    private final String username;

    /**
     * 密码
     * */
    private final String password;

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
     *
     * @param url 地址
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * */
    public Sftp(String url, int port, String username, String password) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * 连接
     * */
    public void connect() {
        if (this.isConnect) {
            return;
        }

        try {
            JSch jSch = new JSch();
            this.session = jSch.getSession(username, url, port);
            this.session.setPassword(password);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            this.session.setConfig(properties);
            this.session.connect();
            this.channelSftp = (ChannelSftp) session.openChannel("sftp");
            this.channelSftp.connect();
        }catch (JSchException e) {
            throw new com.easipass.util.exception.SftpException("sftp: " + this.url + ", 连接失败");
        }

        Log.getLog().info("sftp: " + this.url + ", 已连接");
        this.isConnect = true;
    }

    /**
     * 关闭
     * */
    public void close() {
        if (!this.isConnect) {
            return;
        }

        if (this.session != null) {
            this.session.disconnect();
            this.session = null;
        }

        if (this.channelSftp != null) {
            this.channelSftp.disconnect();
            this.channelSftp = null;
        }

        Log.getLog().info("sftp: " + this.url + ", 已关闭");
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
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 检查连接
     * */
    private void checkConnect() {
        if (!this.isConnect) {
            throw new com.easipass.util.exception.SftpException("sftp: " + this.url + ", 未连接");
        }
    }

}