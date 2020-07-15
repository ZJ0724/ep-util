package com.easipass.epUtil.core.cusResult.formCusResult;

import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.core.cusResult.FormCusResult;
import com.easipass.epUtil.core.dto.CusResultDTO;
import com.easipass.epUtil.entity.resources.cusResult.formCusResult.YeWuFormCusResultResource;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;

/**
 * 报关单业务回执
 *
 * @author ZJ
 * */
public final class YeWuFormCusResult extends FormCusResult {

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param ediNo ediNo
     */
    public YeWuFormCusResult(CusResultDTO cusResultDTO, String ediNo) {
        super(cusResultDTO, ediNo);
    }

    @Override
    protected String getData() {
        //获取回执原document
        YeWuFormCusResultResource yeWuFormCusResultResource = YeWuFormCusResultResource.getInstance();
        Document document = XmlUtil.getDocument(yeWuFormCusResultResource.getInputStream());
        yeWuFormCusResultResource.closeInputStream();

        //document根节点
        Element documentRootElement = document.getRootElement();

        //data节点数据
        String data = documentRootElement.element("Data").getText();

        //解码
        data = Base64Util.decode(data);

        //获取回执解码后的document
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes()));

        //dataDocument根节点
        Element dataDocumentRootElement = dataDocument.getRootElement();

        //替换数据
        dataDocumentRootElement.element("CUS_CIQ_NO").setText(this.getSeqNo());
        dataDocumentRootElement.element("ENTRY_ID").setText(this.getPreEntryId());
        dataDocumentRootElement.element("NOTICE_DATE").setText(DateUtil.getDate());
        dataDocumentRootElement.element("CHANNEL").setText(this.getChannel());
        dataDocumentRootElement.element("NOTE").setText(this.getNote());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);

        // 设置FileName
        String fileName = new SWGDOracle().queryFormHeadFileName(this.getEdiNo());
        if (fileName == null) {
            fileName = this.getName();
        }
        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        // 设置创建时间
        documentRootElement.element("TransInfo").element("CreatTime").setText(DateUtil.getDate());

        return document.asXML();
    }

    @Override
    protected String getName() {
        return "yeWuFormCusResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

}