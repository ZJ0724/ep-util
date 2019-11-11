package com.easipass.EP_Util_Server.service.impl;

import com.easipass.EP_Util_Server.cache.Application_properties;
import com.easipass.EP_Util_Server.entity.decModResult.QPDecModResultBean;
import com.easipass.EP_Util_Server.exception.BugException;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.DecModResultService;
import com.easipass.EP_Util_Server.util.*;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import pers.ZJ.UiAuto.exception.ExecuteException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class QPDecModResultServiceImpl implements DecModResultService {

    private String fileName;
    private QPDecModResultBean qpDecModResultBean;

    public QPDecModResultServiceImpl(){}

    public QPDecModResultServiceImpl(String fileName,QPDecModResultBean qpDecModResultBean){

        this.fileName=fileName;
        this.qpDecModResultBean=qpDecModResultBean;

    }

    @Override
    public void upload() {

        //连接SFTP83
        try {
            SftpUtil.connect(Application_properties.sftp_83_url,Application_properties.sftp_83_port,Application_properties.sftp_83_username,Application_properties.sftp_83_password);
        }catch (JSchException e){
            throw new TipException("sftp:"+Application_properties.sftp_83_url+"未连接");
        }

        //获取回执原document
        Document document=null;
        try {
            document= XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/decModResult/QPDecModResult"));
        }catch (DocumentException e){
            throw new BugException("回执原文件错误");
        }

        //document根节点
        Element documentRootElement=document.getRootElement();

        //获取data节点数据
        String data=documentRootElement.element("Data").getText();

        //解码
        try {
            data= Base64Util.decode(data);
        }catch (IOException e){
            throw new BugException("base64解码错误");
        }

        //获取回执解码后的document
        Document dataDocument=null;
        try {
            InputStream inputStream=new ByteArrayInputStream(data.getBytes());
            dataDocument=XmlUtil.getDocument(inputStream);
        }catch (DocumentException e){
            throw new TipException("解密Data节点后数据不是xml格式");
        }

        //dataDocument根节点
        Element dataDocumentRootElement=dataDocument.getRootElement();

        //替换数据
        dataDocumentRootElement.element("DecModSeqNo").setText(this.qpDecModResultBean.getDecModSeqNo());
        dataDocumentRootElement.element("ResultMessage").setText(this.qpDecModResultBean.getResultMessage());
        dataDocumentRootElement.element("ResultCode").setText(this.qpDecModResultBean.getResultCode());
        data=dataDocument.asXML();

        //加密
        try {
            data=Base64Util.encode(data);
        }catch (IOException e){
            throw new BugException("base64加密错误");
        }

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        //替换原document的FileName节点
        documentRootElement.element("AddInfo").element("FileName").setText(this.fileName);

        //上传到sftp
        try {
            SftpUtil.uploadFile(Application_properties.uploadPath,new ByteArrayInputStream(document.asXML().getBytes()),"QPDecModResult-"+this.qpDecModResultBean.getDecModSeqNo()+"-"+ DateUtil.getTime());
        }catch (SftpException |IOException e){
            throw new BugException(e.getMessage());
        }

        //关闭sftp
        SftpUtil.closeSFTP();

        //run
        try {
            EPMSUtil.run(new File(System.getProperty("user.dir"),"chromedriver/"+Application_properties.chromeDriverVersion+"/chromedriver.exe"));
        }catch (ExecuteException e){
            throw new BugException(e.getMessage());
        }

    }

    @Override
    public void setFileName(String preEntryId, String fileName) {

        //连接数据库
        try {
            OracleUtil.connect(
                    Application_properties.SWGD_url,
                    Application_properties.SWGD_port,
                    Application_properties.SWGD_sid,
                    Application_properties.SWGD_username,
                    Application_properties.SWGD_password
            );
        }catch (ClassNotFoundException e){
            throw new BugException("未找到类");
        }catch (SQLException e){
            throw new TipException("SWGD数据库连接失败");
        }

        //修改文件名
        try {
            OracleUtil.update("update SWGD.T_SWGD_DECMOD_HEAD set FILE_NAME=? where PRE_ENTRY_ID=?",new Object[]{fileName,preEntryId});
        }catch (SQLException e){
            throw new BugException("sql执行出错");
        }

        //关闭数据库
        try {
            OracleUtil.close();
        }catch (SQLException e){
            throw new BugException("关闭数据库出错");
        }

    }

}