package com.easipass.util.core.cusResult.decModCusResult;

import com.easipass.util.core.DTO.cusResult.CusResultDTO;
import com.easipass.util.core.DTO.cusResult.DecModCusResultDTO;
import com.easipass.util.core.Resource;
import com.easipass.util.core.cusResult.DecModCusResult;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.util.Base64Util;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.XmlUtil;
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
     * */
    public QPDecModCusResult(String preEntryId, String channel, String note) {
        super(new CusResultDTO(channel, note), preEntryId);
    }

    /**
     * 构造函数
     *
     * @param decModCusResultDTO qpDecModCusResultDTO
     */
    public QPDecModCusResult(DecModCusResultDTO decModCusResultDTO) {
        this(decModCusResultDTO.getRelation().getPreEntryId(), decModCusResultDTO.getCusResult().getChannel(), decModCusResultDTO.getCusResult().getNote());
    }

    @Override
    public String getData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(Resource.QP_DEC_MOD_CUS_RESULT);

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
        SWGDDatabase swgdDatabaseOracle = new SWGDDatabase();
        String fileName = swgdDatabaseOracle.queryDecModFileName(this.getPreEntryId());

        // 如果修撤单文件名为null，则将文件名字段设置成回执文件名
        if (fileName == null) {
            fileName = this.getName();
            swgdDatabaseOracle.updateDecModFileName(this.getPreEntryId(), fileName);
        }

        swgdDatabaseOracle.close();

        //替换FileName
        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        return document.asXML();
    }

    @Override
    public String getName() {
        return "QPDecModCusResult-" + this.getDecModSeqNo() + "-" + DateUtil.getTime();
    }

}