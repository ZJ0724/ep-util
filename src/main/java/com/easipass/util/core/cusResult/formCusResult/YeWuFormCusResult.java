package com.easipass.util.core.cusResult.formCusResult;

import com.easipass.util.core.DTO.cusResult.YeWuFormCusResultDTO;
import com.easipass.util.core.Database;
import com.easipass.util.core.Resource;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.cusResult.FormCusResult;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.util.Base64Util;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.XmlUtil;
import com.easipass.util.core.exception.ErrorException;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 报关单业务回执
 *
 * @author ZJ
 * */
public final class YeWuFormCusResult extends FormCusResult {

    /**
     * 报关单号
     * */
    private String preEntryId;

    /**
     * 构造函数
     *
     * @param yeWuFormCusResultDTO yeWuFormCusResultDTO
     */
    public YeWuFormCusResult(YeWuFormCusResultDTO yeWuFormCusResultDTO) {
        super(yeWuFormCusResultDTO.getCusResult(), compatibleEdiNo(yeWuFormCusResultDTO.getRelation()));

        // 当通过报关单号选择单子时，禁止修改报关单号
        if (yeWuFormCusResultDTO.getRelation().getEdiNo() == null && yeWuFormCusResultDTO.getRelation().getPreEntryId() != null) {
            if (yeWuFormCusResultDTO.getCusResult().getPreEntryId() != null) {
                throw new CusResultException("使用报关单号选择时，禁止再次修改报关单号");
            }
        }

        this.preEntryId = yeWuFormCusResultDTO.getCusResult().getPreEntryId();
    }

    @Override
    public String getData() {
        //获取回执原document
        Document document = XmlUtil.getDocument(Resource.YE_WU_FORM_CUS_RESULT);

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
        return "yeWuFormCusResult-" + this.getSeqNo() + "-" + DateUtil.getTime();
    }

    /**
     * 获取报关单号
     *
     * @return 报关单号
     * */
    public final String getPreEntryId() {
        if (this.preEntryId != null) {
            return this.preEntryId;
        }

        SWGDDatabase swgdDatabase = new SWGDDatabase();
        ResultSet resultSet = swgdDatabase.queryFormHead(this.getEdiNo());
        String declPort;
        String preEntryId;
        String ieFlag;

        try {
            if (!resultSet.next()) {
                throw new CusResultException("数据库不存在ediNo: " + this.getEdiNo() + "数据");
            }

            declPort = Database.getFiledData(resultSet, "DECL_PORT");
            preEntryId = Database.getFiledData(resultSet, "PRE_ENTRY_ID");
            ieFlag = Database.getFiledData(resultSet, "IE_FLAG");
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            swgdDatabase.close();
        }

        if (declPort == null) {
            throw new CusResultException("ediNo: " + this.getEdiNo() + "申报关区为空");
        }

        if (ieFlag == null) {
            throw new CusResultException("ediNo: " + this.getEdiNo() + "IE_FLAG为空");
        } else {
            ieFlag = this.getIeFlag(ieFlag);
        }

        if (ieFlag == null) {
            throw new ErrorException("ediNo: " + this.getEdiNo() + "未识别报关单进出口类型");
        }

        if (preEntryId.startsWith("EDI")) {
            preEntryId = declPort + "0000" + ieFlag + "0000" + this.getEdiNo().substring(this.getEdiNo().length() - 5);
        }

        this.preEntryId = preEntryId;

        return preEntryId;
    }

    /**
     * 兼容ediNo，默认使用ediNo
     *
     * @param relation relation
     *
     * @return ediNo
     * */
    private static String compatibleEdiNo(YeWuFormCusResultDTO.Relation relation) {
        if (relation.getEdiNo() != null) {
            return relation.getEdiNo();
        }

        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String ediNo = null;

        try {
            String seqNo = relation.getSeqNo();
            String preEntryId = relation.getPreEntryId();

            if (seqNo != null) {
                ediNo = swgdDatabase.queryEdiNoBySeqNo(seqNo);
            }

            if (preEntryId != null) {
                ediNo = swgdDatabase.queryEdiNoByPreEntryId(preEntryId);
            }
        } finally {
            swgdDatabase.close();
        }

        if (ediNo == null) {
            throw new CusResultException("报关单数据不存在");
        }

        return ediNo;
    }

}