package com.easipass.util.core.cusResult.formCusResult;

import com.easipass.util.core.Database;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.cusResult.FormCusResult;
import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.resource.cusResult.formCusResult.YeWuFormCusResultResource;
import com.easipass.util.core.cusResult.CusResultException;
import com.easipass.util.core.util.Base64Util;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

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
        super(cusResultDTO, compatiblePreEntryId(ediNo));
    }

    @Override
    public String getData() {
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
        Document dataDocument = XmlUtil.getDocument(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));

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
        String fileName = new SWGDDatabase().queryFormHeadFileName(this.getEdiNo());
        if (fileName == null) {
            fileName = this.getName();
        }
        documentRootElement.element("AddInfo").element("FileName").setText(fileName);

        // 设置创建时间
        documentRootElement.element("TransInfo").element("CreatTime").setText(DateUtil.getDate());

        return document.asXML();
    }

    @Override
    public String getName() {
        return "yeWuFormCusResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

    /**
     * 获取报关单号
     *
     * @return 报关单号
     * */
    protected final String getPreEntryId() {
        SWGDDatabase swgdOracle = new SWGDDatabase();

        ResultSet resultSet = swgdOracle.queryFormHead(this.getEdiNo());

        if (resultSet == null) {
            swgdOracle.close();
            throw new CusResultException("数据库不存在ediNo: " + this.getEdiNo() + "数据");
        }

        String declPort = Database.getFiledData(resultSet, "DECL_PORT");

        if (declPort == null) {
            throw new CusResultException("ediNo: " + this.getEdiNo() + "申报关区为空");
        }

        String preEntryId = Database.getFiledData(resultSet, "PRE_ENTRY_ID");

        swgdOracle.close();

        if (preEntryId.startsWith("EDI")) {
            return new SWGDDatabase().queryDeclPort(this.getEdiNo()) + "000000000" + this.getEdiNo().substring(this.getEdiNo().length() - 5);
        } else {
            return preEntryId;
        }
    }

    /**
     * 兼容报关单号
     *
     * @param ediNo ediNo
     *
     * @return ediNo
     * */
    private static String compatiblePreEntryId(String ediNo) {
        if (ediNo.startsWith("EDI")) {
            return ediNo;
        }

        return new SWGDDatabase().queryEdiNo(ediNo);
    }

}