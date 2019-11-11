package com.easipass.EP_Util_Server.service.impl;

import com.easipass.EP_Util_Server.cache.Application_properties;
import com.easipass.EP_Util_Server.entity.decModResult.YeWuDecModResultBean;
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

public class YeWuDecModResultServiceImpl implements DecModResultService {

    private String fileName;
    private YeWuDecModResultBean yeWuDecModResultBean;

    public YeWuDecModResultServiceImpl(){}

    public YeWuDecModResultServiceImpl(String fileName,YeWuDecModResultBean yeWuDecModResultBean){

        this.fileName=fileName;
        this.yeWuDecModResultBean=yeWuDecModResultBean;

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
            document= XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/decModResult/yeWuDecModResult"));
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

        //HzApprovalResult节点
        Element HzApprovalResultElement=dataDocumentRootElement.element("SignedData").element("Data").element("HzApprovalResult");

        //替换数据
        HzApprovalResultElement.element("DestResourceId").setText(this.yeWuDecModResultBean.getDestResourceId());
        HzApprovalResultElement.element("FeedbackResults").setText(this.yeWuDecModResultBean.getFeedbackResults());
        HzApprovalResultElement.element("ResultNote").setText(this.yeWuDecModResultBean.getResultNote());
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
            SftpUtil.uploadFile(Application_properties.uploadPath,new ByteArrayInputStream(document.asXML().getBytes()),"yeWuDecModResult-"+this.yeWuDecModResultBean.getDestResourceId()+"-"+ DateUtil.getTime());
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

        new QPDecModResultServiceImpl().setFileName(preEntryId,fileName);

    }

}