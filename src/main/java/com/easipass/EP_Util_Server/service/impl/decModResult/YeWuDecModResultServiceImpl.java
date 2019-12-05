package com.easipass.EP_Util_Server.service.impl.decModResult;

import com.easipass.EP_Util_Server.entity.ChromeDriver;
import com.easipass.EP_Util_Server.entity.Sftp83;
import com.easipass.EP_Util_Server.entity.decModResult.YeWuDecModResultData;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import com.easipass.EP_Util_Server.service.decModResult.YeWuDecModResultService;
import com.easipass.EP_Util_Server.service.impl.formResult.TongXunFormResultServiceImpl;
import com.easipass.EP_Util_Server.util.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.File;

@Service
public class YeWuDecModResultServiceImpl implements YeWuDecModResultService {

    private final Logger logger=Logger.getLogger(YeWuDecModResultServiceImpl.class);

    @Autowired
    Sftp83 sftp83;

    @Autowired
    ChromeDriver chromeDriver;

    @Override
    public void upload(YeWuDecModResultData yeWuDecModResultData, String fileName) {
        logger.info("上传修撤单业务回执...");
        //连接sftp
        String sftp83Url=sftp83.getUrl();
        SftpUtil sftpUtil=new SftpUtil(sftp83Url,sftp83.getPort(),sftp83.getUsername(),sftp83.getPassword());
        try {
            sftpUtil.connect();
        }catch (UtilTipException e){
            throw new TipException("SFTP:"+sftp83Url+"连接失败");
        }

        //获取回执原document
        Document document=XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/decModResult/yeWuDecModResult"));

        //document根节点
        Element documentRootElement=document.getRootElement();

        //data节点数据
        String data=documentRootElement.element("Data").getText();

        //解码
        data= Base64Util.decode(data);

        //获取回执解码后的document
        Document dataDocument=XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes()));

        //dataDocument根节点
        Element dataDocumentRootElement=dataDocument.getRootElement();

        //替换数据
        Element HzApprovalResultElement=dataDocumentRootElement.element("SignedData").element("Data");
        HzApprovalResultElement.element("DestResourceId").setText(yeWuDecModResultData.getDestResourceId());
        HzApprovalResultElement.element("FeedbackResults").setText(yeWuDecModResultData.getFeedbackResults());
        HzApprovalResultElement.element("ResultNote").setText(yeWuDecModResultData.getResultNote());
        data=dataDocument.asXML();
        logger.info(data);

        //加密
        data=Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        //上传到sftp
        sftpUtil.uploadFile(sftp83.getUploadPath(),
                new ByteArrayInputStream(document.asXML().getBytes()),
                "yeWuDecModResult-"+yeWuDecModResultData.getDestResourceId()+"-"+ DateUtil.getTime());

        //关闭sftp
        sftpUtil.closeSFTP();

        //run
        EPMSUtil.run(new File(System.getProperty("user.dir"), ChromeDriver.getChromeDriverPath(chromeDriver.getVersion())));
    }

}