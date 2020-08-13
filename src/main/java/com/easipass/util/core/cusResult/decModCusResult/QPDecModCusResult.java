package com.easipass.util.core.cusResult.decModCusResult;

import com.easipass.util.core.cusResult.DecModCusResult;
import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.oracle.SWGDOracle;
import com.easipass.util.core.resource.cusResult.decModCusResult.QPDecModCusResultResource;
import com.easipass.util.util.Base64Util;
import com.easipass.util.util.DateUtil;
import com.easipass.util.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * 修撤单QP回执
 *
 * @author ZJ
 * */
public final class QPDecModCusResult extends DecModCusResult {

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param preEntryId   报关单号
     */
    public QPDecModCusResult(CusResultDTO cusResultDTO, String preEntryId) {
        super(cusResultDTO, preEntryId);
    }

    @Override
    public String getData() {
        //获取回执原document
        QPDecModCusResultResource qpDecModCusResultResource = QPDecModCusResultResource.getInstance();
        Document document = XmlUtil.getDocument(qpDecModCusResultResource.getInputStream());
        qpDecModCusResultResource.closeInputStream();

        //document根节点
        Element documentRootElement = document.getRootElement();

        //data节点数据
        String data = documentRootElement.element("Data").getText();

        //解码
        data = Base64Util.decode(data);

        //获取回执解码后的document
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));

        //dataDocument根节点
        Element dataDocumentRootElement = dataDocument.getRootElement();

        //替换数据
        dataDocumentRootElement.element("DecModSeqNo").setText(this.getDecModSeqNo());
        dataDocumentRootElement.element("ResultMessage").setText(this.getNote());
        dataDocumentRootElement.element("ResultCode").setText(this.getChannel());
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
        return "QPDecModCusResult-" + this.getDecModSeqNo() + "-" + DateUtil.getTime();
    }

}