package com.easipass.EP_Util_Server.util;

import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SftpUtil {

    private static ChannelSftp channelSftp=null;
    private static Session session=null;

    /**
     * 连接SFTP
     */
    public static void connect(String url,int port,String username,String password) throws JSchException {

        JSch jSch=new JSch();
        session=jSch.getSession(username,url,port);
        session.setPassword(password);
        Properties properties=new Properties();
        properties.put("StrictHostKeyChecking","no");
        session.setConfig(properties);
        session.connect();
        channelSftp=(ChannelSftp)session.openChannel("sftp");
        channelSftp.connect();

    }

    /**
     * 关闭sftp通道
     */
    public static void closeSFTP() {

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
    public static void uploadFile(String path, InputStream inputStream,String name) throws IOException, SftpException{

        if(channelSftp==null || session==null){
            throw new SftpException(0,"sftp未连接");
        }
        channelSftp.cd(path);
        channelSftp.put(inputStream,name);

    }

}