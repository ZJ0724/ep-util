package com.easipass.EpUtilServer.service.impl.decModResult;

import com.easipass.EpUtilServer.config.Sftp83Config;
import com.easipass.EpUtilServer.entity.Log;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.service.DecModResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
import com.easipass.EpUtilServer.util.EPMSUtil;
import com.easipass.EpUtilServer.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

@Service("YeWuDecModResultServiceImpl")
public class YeWuDecModResultServiceImpl implements DecModResultService {

    @Resource
    @Qualifier("QPDecModResultServiceImpl")
    private DecModResultService decModResultService;

    @Resource
    private Sftp83Config sftp83Config;

    @Override
    public Response setFileName(String preEntryId) {
        return decModResultService.setFileName(preEntryId);
    }

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        Log.info("<---- 上传修撤单业务回执 ---->");

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

        //获取回执原document
        Document document = XmlUtil.getDocument(QPDecModResultServiceImpl.class.getResourceAsStream("/decModResult/yeWuDecModResult"));

        //document根节点
        Element documentRootElement = document.getRootElement();

        // data节点数据
        String data = documentRootElement.element("Data").getText();

        // 解码
        data = Base64Util.decode(data);

        // 获取回执解码后的document
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes()));

        // dataDocument根节点
        Element dataDocumentRootElement = dataDocument.getRootElement();

        // <HzApprovalResult>节点
        Element HzApprovalResultElement = dataDocumentRootElement.element("SignedData").element("Data").element("HzApprovalResult");

        // 替换数据
        HzApprovalResultElement.element("DestResourceId").setText(ResultDTO.getDecModSeqNo(preEntryId));
        HzApprovalResultElement.element("FeedbackResults").setText(resultDTO.getChannel());
        HzApprovalResultElement.element("ResultNote").setText(resultDTO.getNote());
        data = dataDocument.asXML();
        Log.info(data);

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(ResultDTO.getFileName(preEntryId));

        //上传到sftp
        sftp.uploadFile(
                sftp83Config.getUploadPath(),
                "QPDecModResult-" + ResultDTO.getDecModSeqNo(preEntryId) + "-" + DateUtil.getTime(),
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
