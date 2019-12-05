package com.easipass.EP_Util_Server.util;

import com.easipass.EP_Util_Server.exception.BugException;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import com.jcraft.jsch.*;
import java.io.InputStream;
import java.util.Properties;

public class SftpUtil {

    private String url;
    private int port;
    private String username;
    private String password;
    private ChannelSftp channelSftp;
    private Session session;
    private boolean isConnect=false;

    public SftpUtil(String url, int port, String username, String password) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    private void check(){
        if(!isConnect){
            throw new BugException("未连接");
        }
    }

    /**
     * 连接SFTP
     */
    public void connect() throws UtilTipException{
        try {
            channelSftp=null;
            session=null;
            JSch jSch=new JSch();
            session=jSch.getSession(username,url,port);
            session.setPassword(password);
            Properties properties=new Properties();
            properties.put("StrictHostKeyChecking","no");
            session.setConfig(properties);
            session.connect();
            channelSftp=(ChannelSftp)session.openChannel("sftp");
            channelSftp.connect();
            isConnect=true;
        }catch (JSchException  e){
            throw new UtilTipException("连接失败");
        }
    }

    /**
     * 关闭sftp通道
     */
    public void closeSFTP() {
        if(channelSftp!=null&&channelSftp.isConnected()){
            channelSftp.disconnect();
            channelSftp=null;
        }
        if(session!=null&&session.isConnected()){
            session.disconnect();
            session=null;
        }
    }

    /**
     * 上传文件
     */
    public void uploadFile(String path,InputStream inputStream,String name){
        check();
        try {
            channelSftp.cd(path);
            channelSftp.put(inputStream,name);
        }catch (SftpException e){
            throw new BugException(e.getMessage());
        }
    }

}