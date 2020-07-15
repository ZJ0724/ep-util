package com.easipass.epUtil.core.cusResult.formCusResult;

import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.core.cusResult.FormCusResult;
import com.easipass.epUtil.core.dto.CusResultDTO;
import com.easipass.epUtil.entity.resources.cusResult.formCusResult.TongXunFormCusResultResource;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;

/**
 * 报关单通讯回执
 *
 * @author ZJ
 * */
public final class TongXunFormCusResult extends FormCusResult {

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     * @param ediNo ediNo
     */
    public TongXunFormCusResult(CusResultDTO cusResultDTO, String ediNo) {
        super(cusResultDTO, ediNo);
    }

    @Override
    protected String getData() {
        //获取回执原document
        TongXunFormCusResultResource tongXunFormCusResultResource = TongXunFormCusResultResource.getInstance();
        Document document = XmlUtil.getDocument(tongXunFormCusResultResource.getInputStream());
        tongXunFormCusResultResource.closeInputStream();

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
        dataDocumentRootElement.element("ResponseCode").setText(this.getChannel());
        dataDocumentRootElement.element("ErrorMessage").setText(this.getNote());
        dataDocumentRootElement.element("ClientSeqNo").setText(this.getEdiNo());
        dataDocumentRootElement.element("SeqNo").setText(this.getSeqNo());
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
        return "tongXunFormCusResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

}