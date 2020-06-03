package com.easipass.epUtil.entity.result.decModResult;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.result.DecModResult;
import com.easipass.epUtil.service.impl.decModResult.QPDecModResultServiceImpl;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;

public class YeWuDecModResult extends DecModResult {

    /**
     * 构造函数
     */
    public YeWuDecModResult(String preEntryId, ResultDTO resultDTO) {
        super(preEntryId, resultDTO);
    }

    @Override
    public String makeData() {
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
        HzApprovalResultElement.element("DestResourceId").setText(this.getDecModSeqNo());
        HzApprovalResultElement.element("FeedbackResults").setText(this.getChannel());
        HzApprovalResultElement.element("ResultNote").setText(this.getNote());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(this.getFileName());

        return document.asXML();
    }

    @Override
    public String makeFileName() {
        return "yeWuDecModResult-" + this.getDecModSeqNo() + "-" + DateUtil.getTime();
    }

}