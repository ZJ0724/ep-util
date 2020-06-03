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

public class TongXunFormResult extends FormResult {

    /**
     * 构造函数
     * */
    public TongXunFormResult(String ediNo, ResultDTO resultDTO) {
        super(ediNo, resultDTO);
    }

    @Override
    public String makeData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(TongXunFormResult.class.getResourceAsStream(ResourcePathConfig.TONG_XUN_FORM_RESULT_PATH));

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

        // 设置创建时间
        documentRootElement.element("TransInfo").element("CreatTime").setText(DateUtil.getDate());

        return document.asXML();
    }

    @Override
    public String makeFileName() {
        return "tongXunFormResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

}