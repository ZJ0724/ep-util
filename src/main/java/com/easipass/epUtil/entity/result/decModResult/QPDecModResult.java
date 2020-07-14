package com.easipass.epUtil.entity.result.decModResult;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.component.oracle.SWGDOracle;
import com.easipass.epUtil.entity.result.DecModResult;
import com.easipass.epUtil.util.Base64Util;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;

public class QPDecModResult extends DecModResult {

    /**
     * 构造函数
     * */
    public QPDecModResult(String preEntryId, ResultDTO resultDTO) {
        super(preEntryId, resultDTO);
    }

    @Override
    public String makeData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(QPDecModResult.class.getResourceAsStream(ResourcePathConfig.QP_DEC_MOD_RESULT_PATH));

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
        dataDocumentRootElement.element("DecModSeqNo").setText(this.getDecModSeqNo());
        dataDocumentRootElement.element("ResultMessage").setText(this.getNote());
        dataDocumentRootElement.element("ResultCode").setText(this.getChannel());
        data = dataDocument.asXML();

        //加密
        data = Base64Util.encode(data);

        //替换原document的data节点
        documentRootElement.element("Data").setText(data);
        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(this.getFileName());

        // 设置数据库fileName字段
        SWGDOracle swgdOracle = new SWGDOracle();
        swgdOracle.updateDecModFileName(this.getPreEntryId(), this.getFileName());

        return document.asXML();
    }

    @Override
    public String makeFileName() {
        return "QPDecModCusResult-" + this.getDecModSeqNo() + "-" + DateUtil.getTime();
    }

}