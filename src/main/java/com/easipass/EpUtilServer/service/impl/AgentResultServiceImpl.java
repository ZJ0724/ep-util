package com.easipass.EpUtilServer.service.impl;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.*;
import com.easipass.EpUtilServer.entity.DTO.AgentResultDTO;
import com.easipass.EpUtilServer.service.AgentResultService;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.EPMSUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    @Resource
    private Sftp83Config sftp83Config;

    @Override
    public Response upload(String ediNo, AgentResultDTO agentResult) {
        Log.info("<---- 上传代理委托回执 ---->");

        //连接sftp
        Sftp sftp = new Sftp(
                sftp83Config.getUrl(),
                sftp83Config.getPort(),
                sftp83Config.getUsername(),
                sftp83Config.getPassword()
        );
        if (!sftp.connect()) {
            Log.info("sftp连接失败");
            return Response.returnFalse("", "sftp连接失败");
        }

        // 获取回执原节点
        Document document = XmlUtil.getDocument(AgentResultServiceImpl.class.getResourceAsStream("/agentResult.wts"));

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(agentResult.getChannel());
        ResponseInfo.element("ResponseNotes").setText(agentResult.getNote());
        ResponseInfo.element("CopCusCode").setText(agentResult.getTradeCode());
        rootElement.element("ConsignNo").setText(AgentResultDTO.getAgentSeqNo(ediNo));
        rootElement.element("DecEntryID").setText(ediNo);
        Log.info(document.asXML());

        //上传到sftp
        sftp.uploadFile(
                sftp83Config.getUploadPath(),
                "agentResult-" + ediNo + "-" + DateUtil.getTime(),
                new ByteArrayInputStream(document.asXML().getBytes())
        );

        //关闭sftp
        sftp.close();

        //run
        EPMSUtil.run();

        Log.info("<---- success ---->");
        return Response.returnTrue(null);
    }

}
