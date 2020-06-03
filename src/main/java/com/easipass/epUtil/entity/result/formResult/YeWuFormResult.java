package com.easipass.epUtil.entity.result.formResult;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.result.FormResult;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;

public class YeWuFormResult extends FormResult {

    /**
     * 构造函数
     * */
    public YeWuFormResult(String ediNo, ResultDTO resultDTO) {
        super(ediNo, resultDTO);
    }

    @Override
    public String makeData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(YeWuFormResult.class.getResourceAsStream(ResourcePathConfig.YE_WU_FORM_RESULT_PATH));

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

        return document.asXML();
    }

    @Override
    public String makeFileName() {
        return "yeWuFormResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

}