package com.easipass.EP_Util_Server.service.impl;

import com.easipass.EP_Util_Server.cache.Application_properties;
import com.easipass.EP_Util_Server.entity.formResult.TongXunFormResultBean;
import com.easipass.EP_Util_Server.exception.BugException;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.FormResultService;
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

public class TongXunFormResultServiceImpl implements FormResultService {

    private TongXunFormResultBean tongXunFormResultBean;

    public TongXunFormResultServiceImpl(TongXunFormResultBean tongXunFormResultBean){

        this.tongXunFormResultBean=tongXunFormResultBean;

    }

    @Override
    public void upload() {

        //获取回执原document
        Document document=null;
        try {
            document=XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/formResult/tongXunFormResult"));
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
        dataDocumentRootElement.element("ResponseCode").setText(this.tongXunFormResultBean.getResponseCode());
        dataDocumentRootElement.element("ErrorMessage").setText(this.tongXunFormResultBean.getErrorMessage());
        dataDocumentRootElement.element("ClientSeqNo").setText(this.tongXunFormResultBean.getClientSeqNo());
        dataDocumentRootElement.element("SeqNo").setText(this.tongXunFormResultBean.getSeqNo());
        dataDocumentRootElement.element("TrnPreId").setText(this.tongXunFormResultBean.getTrnPreId());
        data=dataDocument.asXML();

        //加密
        try {
            data=Base64Util.encode(data);
        }catch (IOException e){
            throw new BugException("base64加密错误");
        }

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        //上传到sftp
        try {
            SftpUtil.uploadFile(
                    Application_properties.sftp_83_url,
                    Application_properties.sftp_83_port,
                    Application_properties.sftp_83_username,
                    Application_properties.sftp_83_password,
                    Application_properties.uploadPath,
                    new ByteArrayInputStream(document.asXML().getBytes()),
                    "tongXunFormResult-"+this.tongXunFormResultBean.getSeqNo()+"-"+ DateUtil.getTime());
        }catch (SftpException e){
            throw new TipException("sftp:"+Application_properties.sftp_83_url+"连接失败");
        }

        //run
        try {
            EPMSUtil.run(new File(System.getProperty("user.dir"),"chromedriver/"+Application_properties.chromeDriverVersion+"/chromedriver.exe"));
        }catch (ExecuteException e){
            throw new BugException(e.getMessage());
        }

    }

}