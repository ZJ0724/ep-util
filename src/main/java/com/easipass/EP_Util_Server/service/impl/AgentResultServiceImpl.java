package com.easipass.EP_Util_Server.service.impl;

import com.easipass.EP_Util_Server.entity.AgentResult;
import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.entity.Sftp83;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import com.easipass.EP_Util_Server.service.AgentResultService;
import com.easipass.EP_Util_Server.util.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    private Logger logger = Logger.getLogger(AgentResultServiceImpl.class);

    @Resource
    private Sftp83 sftp83;

    @Override
    public Response upload(AgentResult agentResult) {
        System.out.println(agentResult);
        logger.info("上传代理委托回执...");

        //连接sftp
        String sftp83Url = sftp83.getUrl();
        SftpUtil sftpUtil = new SftpUtil(sftp83Url,sftp83.getPort(),sftp83.getUsername(),sftp83.getPassword());
        try {
            sftpUtil.connect();
        }catch (UtilTipException e) {
            return Response.returnFalse("", "SFTP:"+sftp83Url+"连接失败");
        }

        // 获取回执原节点
        Document document = XmlUtil.getDocument(AgentResultServiceImpl.class.getResourceAsStream("/agentResult.wts"));

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(agentResult.getResponseCode());
        ResponseInfo.element("ResponseNotes").setText(agentResult.getResponseNotes());
        ResponseInfo.element("CopCusCode").setText(agentResult.getCopCusCode());
        rootElement.element("ConsignNo").setText(agentResult.getConsignNo());
        rootElement.element("DecEntryID").setText(agentResult.getDecEntryID());

        // 上传
        sftpUtil.uploadFile(sftp83.getUploadPath(), new ByteArrayInputStream(document.asXML().getBytes()), "agentResult-" + agentResult.getDecEntryID() + "-" + DateUtil.getTime());

        //关闭sftp
        sftpUtil.closeSFTP();

        //run
        EPMSUtil.run(ChromeDriverUtil.getChromeDriverFile());

        logger.info("上传成功");

        return Response.returnTrue(null);
    }

}
