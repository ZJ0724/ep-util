package com.easipass.EpUtilServer.service.impl.decModResult;

import com.easipass.EpUtilServer.config.ResourcePathConfig;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.enumeration.ResponseFlagEnum;
import com.easipass.EpUtilServer.service.BaseService;
import com.easipass.EpUtilServer.service.DecModResultService;
import com.easipass.EpUtilServer.util.Base64Util;
import com.easipass.EpUtilServer.util.DateUtil;
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
    @Qualifier("BaseDecModResultServiceImpl")
    private DecModResultService baseDecModResultService;

    @Resource
    private BaseService baseService;

    @Override
    public Response setFileName(String preEntryId) {
        return baseDecModResultService.setFileName(preEntryId);
    }

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        // 前置操作
        Response response = baseService.before(false, null);
        if (response.getFlag().equals(ResponseFlagEnum.FALSE.getFlag())) {
            return response;
        }
        Sftp sftp = (Sftp) response.getData();

        //获取回执原document
        Document document = XmlUtil.getDocument(QPDecModResultServiceImpl.class.getResourceAsStream(ResourcePathConfig.YE_WU_DEC_MOD_RESULT_PATH));

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

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(ResultDTO.getFileName(preEntryId));

        // 后置操作
        return baseService.after(document, "yeWuDecModResult-" + ResultDTO.getDecModSeqNo(preEntryId) + "-" + DateUtil.getTime(), false, sftp, null);
    }

}
