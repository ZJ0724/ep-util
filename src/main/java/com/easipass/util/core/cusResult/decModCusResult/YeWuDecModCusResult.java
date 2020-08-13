package com.easipass.util.core.cusResult.decModCusResult;

import com.easipass.util.core.cusResult.DecModCusResult;
import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.oracle.SWGDOracle;
import com.easipass.util.core.resource.cusResult.decModCusResult.YeWuDecModCusResultResource;
import com.easipass.util.util.Base64Util;
import com.easipass.util.util.DateUtil;
import com.easipass.util.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * 修撤单业务回执
 *
 * @author ZJ
 * */
public final class YeWuDecModCusResult extends DecModCusResult {

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param preEntryId   报关单号
     */
    public YeWuDecModCusResult(CusResultDTO cusResultDTO, String preEntryId) {
        super(cusResultDTO, preEntryId);
    }

    @Override
    public String getData() {
        //获取回执原document
        YeWuDecModCusResultResource yeWuDecModCusResultResource = YeWuDecModCusResultResource.getInstance();
        Document document = XmlUtil.getDocument(yeWuDecModCusResultResource.getInputStream());
        yeWuDecModCusResultResource.closeInputStream();

        //document根节点
        Element documentRootElement = document.getRootElement();

        // data节点数据
        String data = documentRootElement.element("Data").getText();

        // 解码
        data = Base64Util.decode(data);

        // 获取回执解码后的document
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));

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

        // 获取修撤单文件名
        SWGDOracle swgdOracle = new SWGDOracle();
        String fileName = swgdOracle.queryDecModFileName(this.getPreEntryId());

        // 如果修撤单文件名为null，则将文件名字段设置成回执文件名
        if (fileName == null) {
            fileName = this.getName();
            swgdOracle.updateDecModFileName(this.getPreEntryId(), fileName);
        }

        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        return document.asXML();
    }

    @Override
    public String getName() {
        return "yeWuDecModCusResult-" + this.getDecModSeqNo() + "-" + DateUtil.getTime();
    }

}