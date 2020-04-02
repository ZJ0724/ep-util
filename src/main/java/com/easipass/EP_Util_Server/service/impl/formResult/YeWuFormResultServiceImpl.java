package com.easipass.EP_Util_Server.service.impl.formResult;

import com.easipass.EP_Util_Server.entity.Sftp83;
import com.easipass.EP_Util_Server.entity.formResult.YeWuFormResult;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import com.easipass.EP_Util_Server.service.formResult.YeWuFormResultService;
import com.easipass.EP_Util_Server.util.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.File;

@Service
public class YeWuFormResultServiceImpl implements YeWuFormResultService {

    private final Logger logger=Logger.getLogger(YeWuFormResultServiceImpl.class);

    @Autowired
    Sftp83 sftp83;

    @Override
    public void upload(YeWuFormResult yeWuFormResult) {
        logger.info("上传报关单业务回执...");
        //连接sftp
        SftpUtil sftpUtil=new SftpUtil(sftp83.getUrl(),sftp83.getPort(),sftp83.getUsername(),sftp83.getPassword());
        try {
            sftpUtil.connect();
        }catch (UtilTipException e){
            throw new TipException("SFTP:"+sftp83.getUrl()+"连接失败");
        }

        //获取回执原document
        Document document=XmlUtil.getDocument(TongXunFormResultServiceImpl.class.getResourceAsStream("/formResult/yeWuFormResult"));

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
        dataDocumentRootElement.element("CUS_CIQ_NO").setText(yeWuFormResult.getCus_ciq_no());
        dataDocumentRootElement.element("ENTRY_ID").setText(yeWuFormResult.getEntry_id());
        dataDocumentRootElement.element("NOTICE_DATE").setText(yeWuFormResult.getNotice_date());
        dataDocumentRootElement.element("CHANNEL").setText(yeWuFormResult.getChannel());
        dataDocumentRootElement.element("NOTE").setText(yeWuFormResult.getNote());
        dataDocumentRootElement.element("CUSTOM_MASTER").setText(yeWuFormResult.getCustom_master());
        dataDocumentRootElement.element("I_E_DATE").setText(yeWuFormResult.getI_e_date());
        dataDocumentRootElement.element("D_DATE").setText(yeWuFormResult.getD_date());
        data=dataDocument.asXML();
        logger.info(data);

        //加密
        data=Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        //上传到sftp
        sftpUtil.uploadFile(sftp83.getUploadPath(),
                new ByteArrayInputStream(document.asXML().getBytes()),
                "yeWuFormResult-"+yeWuFormResult.getCus_ciq_no()+"-"+ DateUtil.getTime());

        //run
        EPMSUtil.run(ChromeDriverUtil.getChromeDriverFile());
    }

}