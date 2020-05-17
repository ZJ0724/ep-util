package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.annotation.UploadResultAnnotation;
import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.service.BaseService;
import com.easipass.epUtil.service.DecModResultService;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service("YeWuDecModResultServiceImpl")
public class YeWuDecModResultServiceImpl implements DecModResultService {

    @Override
    @UploadResultAnnotation
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        // 设置文件名
        Response response = BaseDecModResultService.setFileName(preEntryId);
        if (!response.getFlag().equals("T")) {
            return response;
        }

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

        BaseService.uploadResult(document, "yeWuDecModResult-" + ResultDTO.getDecModSeqNo(preEntryId) + "-" + DateUtil.getTime());

        return Response.returnTrue(null);
    }

}
