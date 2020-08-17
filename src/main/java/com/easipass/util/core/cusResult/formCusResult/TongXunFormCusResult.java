package com.easipass.util.core.cusResult.formCusResult;

import com.easipass.util.core.DTO.cusResult.TongXunFormCusResultDTO;
import com.easipass.util.core.Resource;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.cusResult.FormCusResult;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.util.Base64Util;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * 报关单通讯回执
 *
 * @author ZJ
 * */
public final class TongXunFormCusResult extends FormCusResult {

    /**
     * 构造函数
     *
     * @param tongXunFormCusResultDTO tongXunFormCusResultDTO
     */
    public TongXunFormCusResult(TongXunFormCusResultDTO tongXunFormCusResultDTO) {
        super(tongXunFormCusResultDTO.getCusResult(), tongXunFormCusResultDTO.getRelation().getEdiNo());

        String seqNo = tongXunFormCusResultDTO.getCusResult().getSeqNo();

        this.setSeqNo(seqNo);
    }

    @Override
    public String getData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(Resource.TONG_XUN_FORM_CUS_RESULT);

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
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String fileName = swgdDatabase.queryFormHeadFileName(this.getEdiNo());

        if (fileName == null) {
            fileName = this.getName();
        }

        swgdDatabase.close();

        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        // 设置创建时间
        documentRootElement.element("TransInfo").element("CreatTime").setText(DateUtil.getDate());

        return document.asXML();
    }

    @Override
    public String getName() {
        return "tongXunFormCusResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

}