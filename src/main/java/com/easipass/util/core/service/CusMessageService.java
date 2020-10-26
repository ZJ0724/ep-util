package com.easipass.util.core.service;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.ComparisonException;
import com.easipass.util.core.exception.CusMessageException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.core.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 报文服务
 *
 * @author ZJ
 * */
@Service
public final class CusMessageService {

    /**
     * 比对报关单报文
     *
     * @param inputStream inputStream
     *
     * @return ComparisonMessage
     * */
    public ComparisonMessage formComparison(InputStream inputStream) {
        // 比对信息
        ComparisonMessage result = new ComparisonMessage();

        // 报文document
        Document document;
        try {
            document = XmlUtil.getDocument_v2(inputStream);
        } catch (DocumentException e) {
            throw new CusMessageException("报文格式错误: " + e.getMessage());
        }

        // 根节点
        Element rootElement = document.getRootElement();

        // ediNo
        String ediNo = rootElement.element("DecSign").element("ClientSeqNo").getText();

        if (StringUtil.isEmpty(ediNo)) {
            throw new CusMessageException("报文缺少EdiNo");
        }

        // 数据库表头数据
        JSONObject databaseFormHead;

        List<JSONObject> databaseFormHeadList = SWGDDatabase.queryFormHeadToJson(ediNo);
        if (databaseFormHeadList.size() != 1) {
            throw new ComparisonException("数据库表头未找到数据(ediNo: " + ediNo + ")");
        }
        databaseFormHead = databaseFormHeadList.get(0);

        // 比对<DecSign>
        Element DecSign = rootElement.element("DecSign");
        if (DecSign == null) {
            result.addMessage("缺少<DecSign>节点");
        } else {
            for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecSignNodeMapping) {
                result.comparison(getNodeValue(DecSign, nodeMapping.node), getDbValue(databaseFormHead, nodeMapping.dbField), nodeMapping, "DecSign");
            }
        }

        // 比对表头
        Element DecHead = rootElement.element("DecHead");
        if (DecHead == null) {
            result.addMessage("缺少<DecHead>节点");
        } else {
            for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecHeadNodeMapping) {
                String nodeValue = getNodeValue(DecHead, nodeMapping.node);
                String dbValue = getDbValue(databaseFormHead, nodeMapping.dbField);

                // 特殊处理IEFlag
                if (nodeMapping.node.equals("IEFlag")) {
                    switch (dbValue) {
                        case "0" : case "2" : case "4" : case "6" : case "8" : case "A" :
                            dbValue = "E";
                            break;
                        case "1" : case "3" : case "5" : case "7" : case "9" : case "B" :
                        case "D" : case "F" :
                            dbValue = "I";
                            break;
                        default :
                            dbValue = null;
                            break;
                    }
                }

                // IEDate、DespDate、CmplDschrgDt特殊处理
                if (nodeMapping.node.equals("IEDate") || nodeMapping.node.equals("DespDate") || nodeMapping.node.equals("CmplDschrgDt")) {
                    dbValue = DateUtil.formatDateYYYYMMdd(dbValue);
                }

                // DeclareName特殊处理
                if ("DeclareName".equals(nodeMapping.node)) {
                    // IE_TYPE
                    String ieType = getDbValue(databaseFormHead, "IE_TYPE");

                    // 如果IE_TYPE为0,2，则DeclareName可以为null
                    if ("2".equals(ieType) || "0".equals(ieType)) {
                        if ("".equals(nodeValue)) {
                            dbValue = "";
                        }
                    }
                }

                // 特殊处理PackNo
                if ("PackNo".equals(nodeMapping.node)) {
                    // 如果nodeValue为0，并且dbValue为null，则将dbValue设置成0
                    if ("0".equals(nodeValue) && dbValue == null) {
                        dbValue = "0";
                    }
                }

                // 特殊处理Type
                if ("Type".equals(nodeMapping.node)) {
                    String type = getDbValue(databaseFormHead, "TYPE");

                    if (type == null || "0".equals(type)) {
                        dbValue = "";
                    } else {
                        String[] values = new String[]{"TYPE_2", "TYPE_3", "TYPE_4", "TYPE_5"};
                        String newDbValue = "  ";

                        for (String value : values) {
                            String s = getDbValue(databaseFormHead, value);
                            newDbValue = StringUtil.append(newDbValue, s);
                        }

                        dbValue = newDbValue;
                    }
                }

                result.comparison(nodeValue, dbValue, nodeMapping, "表头");
            }
        }

        // 比对两段转入节点
        Element DecTpAccessType = rootElement.element("DecTpAccessType");

        for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecTpAccessTypeMapping) {
            result.comparison(getNodeValue(DecTpAccessType, nodeMapping.node), getDbValue(databaseFormHead, nodeMapping.dbField), nodeMapping, "两段转入");
        }

        // 比对表体
        // 数据库表体数据
        List<JSONObject> databaseFormListList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "')");
        // 报文DecLists节点
        Element DecLists = rootElement.element("DecLists");

        if (DecLists == null) {
            result.addMessage("缺少<DecLists>节点");
        }

        // 数据库表体数据必须和子节点数一致
        else if (databaseFormListList.size() != DecLists.elements().size()) {
            result.addMessage(formatMessage("表体", "报文节点数量与数据库数量不一致"));
        }

        else {
            for (int i = 0; i < databaseFormListList.size(); i++) {
                // 数据库单个表体
                JSONObject databaseFormList = null;
                for (JSONObject jsonObject : databaseFormListList) {
                    if ((i + "").equals(getDbValue(jsonObject, "G_NO"))) {
                        databaseFormList = jsonObject;
                        break;
                    }
                }
                if (databaseFormList == null) {
                    result.addMessage(formatMessage("表体 - " + (i + 1), "数据库中不存在"));
                    continue;
                }

                // 报文单个表体
                Element DecList = null;
                for (Element element : DecLists.elements("DecList")) {
                    if (((i + 1) + "").equals(getNodeValue(element, "GNo"))) {
                        DecList = element;
                        break;
                    }
                }
                if (DecList == null) {
                    result.addMessage(formatMessage("表体 - " + (i + 1), "报文中不存在"));
                    continue;
                }

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecListNodeMapping) {
                    String nodeValue = getNodeValue(DecList, nodeMapping.node);
                    String dbValue = getDbValue(databaseFormList, nodeMapping.dbField);

                    // ProdValidDt特殊处理
                    if (nodeMapping.node.equals("ProdValidDt")) {
                        dbValue = DateUtil.formatDateYYYYMMdd(dbValue);
                    }

                    // 特殊处理CodeTS
                    if ("CodeTS".equals(nodeMapping.node)) {
                        String codeT = getDbValue(databaseFormList, "CODE_T");
                        String codeS = getDbValue(databaseFormList, "CODE_S");

                        if (StringUtil.isEmpty(codeS)) {
                            codeS = "00";
                        }

                        dbValue = codeT + codeS;
                    }

                    // 特殊处理ciqCode
                    if ("CiqCode".equals(nodeMapping.node)) {
                        String fileDataSource = getDbValue(databaseFormHead, "FILE_DATASOURCE");

                        // 如果清空了ciqCode，从tmpCiqCode取值
                        if (!StringUtil.isEmpty(fileDataSource)) {
                            if ("2".equals(fileDataSource.substring(1, 2))) {
                                dbValue = getDbValue(databaseFormList, "TMP_CIQ_CODE");
                            }
                        }
                    }

                    result.comparison(nodeValue, dbValue, nodeMapping, "表体 - " + (i + 1));
                }

                // 比对产品资质
                // 数据库产品资质数据
                List<JSONObject> databaseGoodsLimit = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_GOODS_LIMIT WHERE LIST_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') AND G_NO = '" + i + "') ORDER BY ORDER_NO");
                // 报文产品资质节点
                Element DecGoodsLimits = DecList.element("DecGoodsLimits");

                // 如果存在产品资质，则数据库的数量要与报文一致
                if (databaseGoodsLimit.size() > 0) {
                    if (DecGoodsLimits == null) {
                        result.addMessage("缺少<DecGoodsLimits>节点");
                    }

                    else {
                        // 报文产品资质集合
                        List<Element> DecGoodsLimitList = DecGoodsLimits.elements("DecGoodsLimit");

                        if (databaseGoodsLimit.size() != DecGoodsLimitList.size()) {
                            result.addMessage(formatMessage("表体 - " + (i + 1), "产品资质", "报文节点数量与数据库数量不一致"));
                        }

                        else {
                            for (int j = 0; j < databaseGoodsLimit.size(); j++) {
                                // 数据库单个产品资质
                                JSONObject jsonObject = databaseGoodsLimit.get(j);
                                // 报文单个产品资质
                                Element element = DecGoodsLimitList.get(j);

                                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecGoodsLimitNodeMapping) {
                                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "表体 - " + (i + 1), "产品资质 - " + (j + 1));
                                }

                                // 比对产品资质VIN
                                // 数据库产品资质VIN数据
                                List<JSONObject> databaseDecGoodsLimitVin = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_GOODS_LIMIT_VIN WHERE LIMIT_ID = '" + getDbValue(jsonObject, "ID") + "' ORDER BY ORDER_NO");
                                // 报文产品资质VIN数据
                                List<Element> DecGoodsLimitVinList = element.elements("DecGoodsLimitVin");

                                if (databaseDecGoodsLimitVin.size() != 0) {
                                    // 数据库数据与报文中数量一致
                                    if (databaseDecGoodsLimitVin.size() != DecGoodsLimitVinList.size()) {
                                        result.addMessage(formatMessage("表体 - " + (i + 1), "产品资质 - " + (j + 1), "产品资质VIN", "报文节点数量与数据库数量不一致"));
                                    } else {
                                        for (int n = 0; n < databaseDecGoodsLimitVin.size(); n++) {
                                            // 单个数据库产品资质VIN
                                            JSONObject jsonObject1 = databaseDecGoodsLimitVin.get(n);
                                            // 单个报文产品资质VIN
                                            Element element1 = DecGoodsLimitVinList.get(n);

                                            for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecGoodsLimitVinNodeMapping) {
                                                String dbValue = getDbValue(jsonObject1, nodeMapping.dbField);

                                                // 特殊处理BillLadDate
                                                if ("BillLadDate".equals(nodeMapping.node)) {
                                                    dbValue = DateUtil.formatDate(dbValue, "yyyy-MM-dd 00:00:00", "yyyyMMdd");
                                                }

                                                result.comparison(getNodeValue(element1, nodeMapping.node), dbValue, nodeMapping, "表体 - " + (i + 1), "产品资质 - " + (j + 1), "产品资质VIN - " + (n + 1));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 比对集装箱
        // 数据库集装箱信息
        List<JSONObject> databaseContainer = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_CONTAINER WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY CONTAINER_NO");
        // 报文集装箱
        Element DecContainers = rootElement.element("DecContainers");
        List<Element> ContainerList = DecContainers == null ? new ArrayList<>() : DecContainers.elements("Container");

        if (databaseContainer.size() != ContainerList.size()) {
            result.addMessage(formatMessage("集装箱", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseContainer.size(); i++) {
                JSONObject jsonObject = databaseContainer.get(i);
                Element element = ContainerList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.ContainerNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "集装箱 - " + (i + 1));
                }
            }
        }

        // 比对随附单证
        // 数据库随附单证
        List<JSONObject> databaseLicenseDocu = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_CERTIFICATE WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY CERTIFICATE_NO");
        // 报文随附单证
        Element DecLicenseDocus = rootElement.element("DecLicenseDocus");
        List<Element> LicenseDocuList = DecLicenseDocus == null ? new ArrayList<>() : DecLicenseDocus.elements("LicenseDocu");

        if (databaseLicenseDocu.size() != LicenseDocuList.size()) {
            result.addMessage(formatMessage("随附单证", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseLicenseDocu.size(); i++) {
                JSONObject jsonObject = databaseLicenseDocu.get(i);
                Element element = LicenseDocuList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.LicenseDocuNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "随附单证 - " + (i + 1));
                }
            }
        }

        // 比对所需单证
        // 数据库所需单证
        List<JSONObject> databaseDecRequestCert = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_REQUEST_CERT WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY ORDER_NO");
        // 报文所需单证
        Element DecRequestCerts = rootElement.element("DecRequestCerts");
        List<Element> DecRequestCertList = DecRequestCerts == null ? new ArrayList<>() : DecRequestCerts.elements("DecRequestCert");

        if (databaseDecRequestCert.size() != DecRequestCertList.size()) {
            result.addMessage(formatMessage("所需单证", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseDecRequestCert.size(); i++) {
                JSONObject jsonObject = databaseDecRequestCert.get(i);
                Element element = DecRequestCertList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecRequestCertNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "所需单证 - " + (i + 1));
                }
            }
        }

        // 对比企业资质
        // 数据库企业资质
        List<JSONObject> databaseDecCopLimit = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_COP_LIMIT WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY ORDER_NO");
        // 报文企业资质
        Element DecCopLimits = rootElement.element("DecCopLimits");
        List<Element> DecCopLimitList = DecCopLimits == null ? new ArrayList<>() : DecCopLimits.elements("DecCopLimit");

        if (databaseDecCopLimit.size() != DecCopLimitList.size()) {
            result.addMessage(formatMessage("企业资质", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseDecCopLimit.size(); i++) {
                JSONObject jsonObject = databaseDecCopLimit.get(i);
                Element element = DecCopLimitList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecCopLimitNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "企业资质 - " + (i + 1));
                }
            }
        }

        // 对比企业承诺
        // 数据库企业承诺
        List<JSONObject> databaseDecCopPromise = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_COP_PROMISE WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY ORDER_NO");
        // 报文企业资质
        Element DecCopPromises = rootElement.element("DecCopPromises");
        List<Element> DecCopPromiseList = DecCopPromises == null ? new ArrayList<>() : DecCopPromises.elements("DecCopPromise");

        if (databaseDecCopPromise.size() != DecCopPromiseList.size()) {
            result.addMessage(formatMessage("企业承诺", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseDecCopPromise.size(); i++) {
                JSONObject jsonObject = databaseDecCopPromise.get(i);
                Element element = DecCopPromiseList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecCopPromiseNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "企业承诺 - " + (i + 1));
                }
            }
        }

        // 对比其他包装
        // 数据库其他包装
        List<JSONObject> databaseDecOtherPack = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_OTHER_PACK WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') ORDER BY ORDER_NO");
        // 报文其他包装
        Element DecOtherPacks = rootElement.element("DecOtherPacks");
        List<Element> DecOtherPackList = DecOtherPacks == null ? new ArrayList<>() : DecOtherPacks.elements("DecOtherPack");

        if (databaseDecOtherPack.size() != DecOtherPackList.size()) {
            result.addMessage(formatMessage("其他包装", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseDecOtherPack.size(); i++) {
                JSONObject jsonObject = databaseDecOtherPack.get(i);
                Element element = DecOtherPackList.get(i);

                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecOtherPackNodeMapping) {
                    result.comparison(getNodeValue(element, nodeMapping.node), getDbValue(jsonObject, nodeMapping.dbField), nodeMapping, "其他包装 - " + (i + 1));
                }
            }
        }

        // 对比特许权使用费
        // 数据库特许权使用费
        List<JSONObject> databaseDecRoyaltyFeeList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_DEC_ROYALTY_FEE WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "')");
        JSONObject databaseDecRoyaltyFee = databaseDecRoyaltyFeeList.size() != 0 ? databaseDecRoyaltyFeeList.get(0) : null;
        // 报文特许权使用费
        Element DecRoyaltyFee = rootElement.element("DecRoyaltyFee");

        if (databaseDecRoyaltyFee != null) {
            if (DecRoyaltyFee == null) {
                result.addMessage(formatMessage("缺少<DecRoyaltyFee>"));
            } else {
                for (NodeMapping nodeMapping : FormCusMessageNodeMapping.DecRoyaltyFeeNodeMapping) {
                    result.comparison(getNodeValue(DecRoyaltyFee, nodeMapping.node), getDbValue(databaseDecRoyaltyFee, nodeMapping.dbField), nodeMapping, "特许权使用费");
                }
            }
        }

        // 比对原产地证等
        List<JSONObject> databaseEcoList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_ECO WHERE HEAD_ID = '" + databaseFormHead.get("ID") + "'");
        List<Element> EcoRelationList = rootElement.elements("EcoRelation");

        for (JSONObject jsonObject : databaseEcoList) {
            // CERT_TYPE
            String CERT_TYPE = jsonObject.getString("CERT_TYPE");

            if (StringUtil.isEmpty(CERT_TYPE)) {
                result.addMessage(formatMessage("原产地证", "数据库存在CERT_TYPE为空"));
                continue;
            }

            // DEC_G_NO
            String DEC_G_NO = jsonObject.getString("DEC_G_NO");

            if (StringUtil.isEmpty(DEC_G_NO)) {
                result.addMessage(formatMessage("原产地证", "数据库存在DEC_G_NO为空"));
                continue;
            }

            // 找到对应的报文节点
            Element EcoRelation = null;

            for (Element element : EcoRelationList) {
                // 报文中CertType
                String CertType = getNodeValue(element, "CertType");

                if (StringUtil.isEmpty(CertType)) {
                    continue;
                }

                // 报文中DecGNo
                String DecGNo = getNodeValue(element, "DecGNo");

                if (StringUtil.isEmpty(DecGNo)) {
                    continue;
                }

                // 取CertType第一位
                CertType = CertType.substring(0, 1);

                if (CertType.equals(CERT_TYPE) && DecGNo.equals(DEC_G_NO)) {
                    EcoRelation = element;
                    break;
                }
            }

            if (EcoRelation == null) {
                result.addMessage(formatMessage("原产地证", "报文未找到<CERT_TYPE>为" + CERT_TYPE));
                continue;
            }

            // 拿到随附单证Y证，通过<>去截取
            String CERT_CODE_1 = "";
            String CERT_CODE_2 = "";

            List<JSONObject> CERTIFICATE_Y_LIST = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_CERTIFICATE WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "') AND DOCU_CODE = 'Y'");

            if (CERTIFICATE_Y_LIST.size() != 0) {
                JSONObject CERTIFICATE_Y = CERTIFICATE_Y_LIST.get(0);
                String CERT_CODE = getDbValue(CERTIFICATE_Y, "CERT_CODE");

                if (!StringUtil.isEmpty(CERT_CODE)) {
                    int i = CERT_CODE.indexOf(">");

                    if (i != -1) {
                        CERT_CODE_1 = CERT_CODE.substring(1, i);
                        CERT_CODE_2 = CERT_CODE.substring(i + 1);
                    }
                }
            }

            for (NodeMapping nodeMapping : FormCusMessageNodeMapping.EcoRelationNodeMapping) {
                // dbValue
                String dbValue = getDbValue(jsonObject, nodeMapping.dbField);

                // 特殊处理Y证
                if ("CertType".equals(nodeMapping.node) && "Y".equals(CERT_TYPE)) {
                    dbValue = dbValue + CERT_CODE_1;
                }
                if ("EcoCertNo".equals(nodeMapping.node) && "Y".equals(CERT_TYPE)) {
                    dbValue = CERT_CODE_2;
                }

                result.comparison(getNodeValue(EcoRelation, nodeMapping.node), dbValue, nodeMapping, "原产地证");
            }
        }

        if (result.getMessages().size() == 0) {
            result.setFlag(true);
            result.addMessage("比对完成，无差异");
        } else {
            result.setFlag(false);
        }

        result.addMessageInFirst("ediNo: " + ediNo);

        return result;
    }

    /**
     * 比对修撤单报文
     *
     * @param inputStream inputStream
     *
     * @return ComparisonMessage
     * */
    public ComparisonMessage decModComparison(InputStream inputStream) {
        // 比对信息
        ComparisonMessage result = new ComparisonMessage();

        // 报文document
        Document document;
        try {
            document = XmlUtil.getDocument_v2(inputStream);
        } catch (DocumentException e) {
            throw new CusMessageException("报文格式错误: " + e.getMessage());
        }

        // 根节点
        Element rootElement = document.getRootElement();

        // preEntryId
        Element EntryIdElement = rootElement.element("EntryId");
        String preEntryId = EntryIdElement != null ? EntryIdElement.getText() : null;
        if (StringUtil.isEmpty(preEntryId)) {
            throw new CusMessageException("缺少preEntryId");
        }

        // 比对表头

        // 数据库表头信息
        List<JSONObject> databaseDecModHeadList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_DECMOD_HEAD WHERE PRE_ENTRY_ID = '" + preEntryId + "'");
        JSONObject databaseDecModHead;
        if (databaseDecModHeadList.size() != 1) {
            throw new CusMessageException("数据库数据异常(preEntryId: " + preEntryId + ")");
        } else {
            databaseDecModHead = databaseDecModHeadList.get(0);
        }

        // 报关单表头
        List<JSONObject> databaseFormHeadList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE PRE_ENTRY_ID = '" + preEntryId + "'");
        JSONObject databaseFormHead;
        if (databaseFormHeadList.size() != 1) {
            throw new CusMessageException("数据库报关单数据异常(preEntryId: " + preEntryId + ")");
        } else {
            databaseFormHead = databaseFormHeadList.get(0);
        }

        for (NodeMapping nodeMapping : DecModCusMessageNodeMapping.HEAD) {
            String dbValue = getDbValue(databaseDecModHead, nodeMapping.dbField);

            // CustomsCode
            if ("CustomsCode".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "DECL_PORT");
            }

            // TradeCode
            if ("TradeCode".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "TRADE_CO");
            }

            // TradeName
            if ("TradeName".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "TRADE_NAME");
            }

            // AgentName
            if ("AgentName".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "AGENT_NAME");
            }

            // IeFlag
            if ("IeFlag".equals(nodeMapping.node)) {
                switch (getDbValue(databaseFormHead, "IE_FLAG")) {
                    case "0" : case "2" : case "4" : case "6" : case "8" : case "A" :
                        dbValue = "E";
                        break;
                    case "1" : case "3" : case "5" : case "7" : case "9" : case "B" :
                    case "D" : case "F" :
                        dbValue = "I";
                        break;
                    default :
                        dbValue = null;
                        break;
                }
            }

            // IcCode
            if ("IcCode".equals(nodeMapping.node)) {
                dbValue = dbValue + "|" + getDbValue(databaseDecModHead, "CERT_NO");
            }

            // TradeCreditCode
            if ("TradeCreditCode".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "TRADE_CO_SCC");
            }

            // AgentCreditCode
            if ("AgentCreditCode".equals(nodeMapping.node)) {
                dbValue = getDbValue(databaseFormHead, "AGENT_CODE_SCC");
            }

            result.comparison(getNodeValue(rootElement, nodeMapping.node), dbValue, nodeMapping, "表头");
        }

        // 比对表体
        // 数据库表体
        List<JSONObject> databaseDecModListList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_DECMOD_LIST WHERE HEAD_ID = '" + databaseDecModHead.get("ID") + "' ORDER BY ID");
        // 报文表体
        Element Items = rootElement.element("Items");
        List<Element> ItemList = Items == null ? new ArrayList<>() : Items.elements("Item");

        if (databaseDecModListList.size() != ItemList.size()) {
            result.addMessage(formatMessage("表体", "报文节点数量与数据库数量不一致"));
        } else {
            for (int i = 0; i < databaseDecModListList.size(); i++) {
                JSONObject jsonObject = databaseDecModListList.get(i);
                Element element = ItemList.get(i);

                for (NodeMapping nodeMapping : DecModCusMessageNodeMapping.LIST) {
                    String dbValue = getDbValue(jsonObject, nodeMapping.dbField);

                    // No
                    if ("No".equals(nodeMapping.node)) {
                        dbValue = (i + 1) + "";
                    }

                    result.comparison(getNodeValue(element, nodeMapping.node), dbValue, nodeMapping, "表体 - " + (i + 1));
                }
            }
        }

        if (result.getMessages().size() == 0) {
            result.setFlag(true);
            result.addMessage("比对完成，无差异");
        } else {
            result.setFlag(false);
        }

        result.addMessageInFirst("preEntryId: " + preEntryId);

        return result;
    }

    /**
     * 获取报文节点值
     *
     * @param element 节点
     * @param node 子节点名
     *
     * @return 子节点值
     * */
    private static String getNodeValue(Element element, String node) {
        if (element == null) {
            return null;
        }

        Element element1 = element.element(node);
        if (element1 == null) {
            return null;
        }
        return element1.getText();
    }

    /**
     * 获取数据库值
     *
     * @param jsonObject 数据库数据
     * @param dbField 数据库字段
     *
     * @return 字段值
     * */
    private static String getDbValue(JSONObject jsonObject, String dbField) {
        return jsonObject.getString(dbField);
    }

    /**
     * 格式化信息
     *
     * @param messages 信息，最后一位是信息，前面都是节点位置
     *
     * @return 格式化后的信息
     * */
    private static String formatMessage(String... messages) {
        String result = "";

        for (int i = 0; i < messages.length; i++) {
            String message = "[" + messages[i] + "]";
            if  (i != 0 && i != messages.length - 1) {
                message = " - " + message;
            }

            if (i == messages.length - 1) {
                result = result + " : " + messages[i];
                break;
            }

            result = StringUtil.append(result, message);
        }

        return result;
    }

    /**
     * 比对信息
     *
     * @author ZJ
     * */
    public static final class ComparisonMessage {

        /**
         * 状态
         * */
        private boolean flag = false;

        /**
         * 信息
         * */
        private final List<String> messages = new ArrayList<>();

        /**
         * setFlag
         *
         * @param flag flag
         * */
        private void setFlag(boolean flag) {
            this.flag = flag;
        }

        /**
         * addMessage
         *
         * @param message message
         * */
        private void addMessage(String message) {
            this.messages.add(message);
        }

        /**
         * addMessage
         *
         * @param message message
         * */
        private void addMessageInFirst(String message) {
            this.messages.add(0, message);
        }

        /**
         * isFlag
         *
         * @return flag
         * */
        public boolean isFlag() {
            return this.flag;
        }

        /**
         * getMessages
         *
         * @return messages
         * */
        public List<String> getMessages() {
            return this.messages;
        }

        /**
         * 比对
         *
         * @param nodeValue 节点值
         * @param dbValue 数据库值
         * @param nodeMapping nodeMapping
         * @param titles 标题
         * */
        public void comparison(String nodeValue, String dbValue, NodeMapping nodeMapping, String... titles) {
            String[] messages = new String[titles.length + 2];

            System.arraycopy(titles, 0, messages, 0, titles.length);
            messages[messages.length - 2] = nodeMapping.node + "(" + nodeMapping.name + ")";

            if (nodeMapping.notNull) {
                if (nodeValue == null) {
                    messages[messages.length - 1] = "为空";
                    this.addMessage(formatMessage(messages));
                    return;
                }
            }

            if (dbValue == null) {
                dbValue = "";
            }
            if (nodeValue == null) {
                nodeValue = "";
            }

            if (!dbValue.equals(nodeValue)) {
                messages[messages.length - 1] = "数据库值: " + dbValue + "; 节点值: " + nodeValue;
                this.addMessage(formatMessage(messages));
            }
        }

        @Override
        public String toString() {
            JSONObject jsonObject = new JSONObject(true);
            jsonObject.put("flag", this.flag);
            jsonObject.put("messages", this.messages);
            return jsonObject.toJSONString();
        }

    }

    /**
     * 报关单报文节点映射
     *
     * @author ZJ
     * */
    private static final class FormCusMessageNodeMapping {

        /**
         * <DecSign><DecSign/>
         * */
        public static final List<NodeMapping> DecSignNodeMapping = new ArrayList<>();

        static {
            DecSignNodeMapping.add(new NodeMapping("Sign", "SIGNTXT", "加签串"));
        }

        /**
         * 表头
         * */
        public static final List<NodeMapping> DecHeadNodeMapping = new ArrayList<>();

        static {
            DecHeadNodeMapping.add(new NodeMapping("IEFlag", "IE_FLAG", "进出口标识"));
            DecHeadNodeMapping.add(new NodeMapping("Type", "TYPE", "TYPE"));
            DecHeadNodeMapping.add(new NodeMapping("AgentCode", "AGENT_CODE", "申报单位代码"));
            DecHeadNodeMapping.add(new NodeMapping("AgentName", "AGENT_NAME", "申报单位名称"));
            DecHeadNodeMapping.add(new NodeMapping("ApprNo", "APPR_NO", "批准文号"));
            DecHeadNodeMapping.add(new NodeMapping("BillNo", "BILL_NO", "提运单号"));
            DecHeadNodeMapping.add(new NodeMapping("ContrNo", "CONTR_NO", "合同号"));
            DecHeadNodeMapping.add(new NodeMapping("CustomMaster", "DECL_PORT", "申报地海关"));
            DecHeadNodeMapping.add(new NodeMapping("CutMode", "CUT_MODE", "征免性质"));
            DecHeadNodeMapping.add(new NodeMapping("DistinatePort", "DISTINATE_PORT_STD", "经停港/指运港"));
            DecHeadNodeMapping.add(new NodeMapping("FeeCurr", "FEE_CURR_STD", "运费/币制"));
            DecHeadNodeMapping.add(new NodeMapping("FeeMark", "FEE_MARK", "运费/标记"));
            DecHeadNodeMapping.add(new NodeMapping("FeeRate", "FEE_RATE", "运费/率"));
            DecHeadNodeMapping.add(new NodeMapping("GrossWet", "GROSS_WT", "毛重"));
            DecHeadNodeMapping.add(new NodeMapping("IEDate", "I_E_DATE", "进出口日期"));
            DecHeadNodeMapping.add(new NodeMapping("IEPort", "I_E_PORT", "进出口岸"));
            DecHeadNodeMapping.add(new NodeMapping("InsurCurr", "INSUR_CURR_STD", "保险费/币制"));
            DecHeadNodeMapping.add(new NodeMapping("InsurMark", "INSUR_MARK", "保险费/标记"));
            DecHeadNodeMapping.add(new NodeMapping("InsurRate", "INSUR_RATE", "保险费/率"));
            DecHeadNodeMapping.add(new NodeMapping("LicenseNo", "LICENSE_NO", "许可证编号"));
            DecHeadNodeMapping.add(new NodeMapping("ManualNo", "MANUAL_NO", "备案号"));
            DecHeadNodeMapping.add(new NodeMapping("NetWt", "NET_WT", "净重"));
            DecHeadNodeMapping.add(new NodeMapping("NoteS", "NOTE_S", "备注"));
            DecHeadNodeMapping.add(new NodeMapping("OtherCurr", "OTHER_CURR_STD", "杂费/币制"));
            DecHeadNodeMapping.add(new NodeMapping("OtherMark", "OTHER_MARK", "杂费/标记"));
            DecHeadNodeMapping.add(new NodeMapping("OtherRate", "OTHER_RATE", "杂费/率"));
            DecHeadNodeMapping.add(new NodeMapping("OwnerCode", "OWNER_CODE", "消费使用单位代码"));
            DecHeadNodeMapping.add(new NodeMapping("OwnerName", "OWNER_NAME", "消费使用单位名称"));
            DecHeadNodeMapping.add(new NodeMapping("PackNo", "PACK_NO", "件数"));
            DecHeadNodeMapping.add(new NodeMapping("TradeCode", "TRADE_CO", "经营单位统一编码"));
            DecHeadNodeMapping.add(new NodeMapping("TradeCountry", "TRADE_COUNTRY_STD", "起运国/运抵国"));
            DecHeadNodeMapping.add(new NodeMapping("TradeMode", "TRADE_MODE_STD", "贸易方式"));
            DecHeadNodeMapping.add(new NodeMapping("TradeName", "TRADE_NAME", "收发货人名称"));
            DecHeadNodeMapping.add(new NodeMapping("TrafMode", "TRAF_MODE_STD", "运输方式"));
            DecHeadNodeMapping.add(new NodeMapping("TrafName", "TRAF_NAME", "运输工具代码及名称"));
            DecHeadNodeMapping.add(new NodeMapping("TransMode", "TRANS_MODE", "成交方式"));
            DecHeadNodeMapping.add(new NodeMapping("WrapType", "WRAP_TYPE_STD", "包装种类"));
            DecHeadNodeMapping.add(new NodeMapping("TypistNo", "I_C_CODE", "IC卡号"));
            DecHeadNodeMapping.add(new NodeMapping("BillType", "BILL_TYPE", "备案清单类型"));
            DecHeadNodeMapping.add(new NodeMapping("DataSource", "FILE_DATASOURCE", "DataSource"));
            DecHeadNodeMapping.add(new NodeMapping("PromiseItmes", "PROMISE_ITMES", "承诺事项"));
            DecHeadNodeMapping.add(new NodeMapping("TradeAreaCode", "TRADE_AREA_CODE_STD", "贸易国别"));
            DecHeadNodeMapping.add(new NodeMapping("CheckFlow", "CHECK_FLOW", "查验分流"));
            DecHeadNodeMapping.add(new NodeMapping("TaxAaminMark", "TAX_AAMIN_MARK", "税收征管标记"));
            DecHeadNodeMapping.add(new NodeMapping("MarkNo", "MARK_NO", "标记唛码"));
            DecHeadNodeMapping.add(new NodeMapping("DespPortCode", "DESP_PORT_CODE", "启运口岸代码"));
            DecHeadNodeMapping.add(new NodeMapping("EntyPortCode", "ENTY_PORT_CODE", "入境口岸代码"));
            DecHeadNodeMapping.add(new NodeMapping("GoodsPlace", "GOODS_PLACE", "存放地点"));
            DecHeadNodeMapping.add(new NodeMapping("BLNo", "B_L_NO", "B/L号"));
            DecHeadNodeMapping.add(new NodeMapping("InspOrgCode", "INSP_ORG_CODE", "口岸检验检疫机关"));
            DecHeadNodeMapping.add(new NodeMapping("SpecDeclFlag", "SPEC_DECL_FLAG", "特种业务标识"));
            DecHeadNodeMapping.add(new NodeMapping("PurpOrgCode", "PURP_ORG_CODE", "目的地检验检疫机关"));
            DecHeadNodeMapping.add(new NodeMapping("DespDate", "DESP_DATE", "启运日期"));
            DecHeadNodeMapping.add(new NodeMapping("CmplDschrgDt", "CMPL_DSCHRG_DT", "卸毕日期"));
            DecHeadNodeMapping.add(new NodeMapping("CorrelationReasonFlag", "CORRELATION_REASON_FLAG", "关联理由"));
            DecHeadNodeMapping.add(new NodeMapping("VsaOrgCode", "VSA_ORG_CODE", "领证机关"));
            DecHeadNodeMapping.add(new NodeMapping("OrigBoxFlag", "ORIG_BOX_FLAG", "原集装箱标识"));
            DecHeadNodeMapping.add(new NodeMapping("DeclareName", "DECLARE_NAME", "报关员姓名"));
            DecHeadNodeMapping.add(new NodeMapping("NoOtherPack", "NO_OTHER_PACK", "无其他包装"));
            DecHeadNodeMapping.add(new NodeMapping("OrgCode", "ORG_CODE", "检验检疫受理机关"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsignorCode", "OVERSEAS_CONSIGNOR_CODE", "境外发货人代码"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsignorCname", "OVERSEAS_CONSIGNOR_CNAME", "境外发货人名称"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsignorEname", "OVERSEAS_CONSIGNOR_ENAME", "境外发货人名称（外文）"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsignorAddr", "OVERSEAS_CONSIGNOR_ADDR", "境外发货人地址"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsigneeCode", "OVERSEAS_CONSIGNEE_CODE", "境外收货人编码"));
            DecHeadNodeMapping.add(new NodeMapping("OverseasConsigneeEname", "OVERSEAS_CONSIGNEE_ENAME", "境外收货人名称(外文)"));
            DecHeadNodeMapping.add(new NodeMapping("DomesticConsigneeEname", "DOMESTIC_CONSIGNEE_ENAME", "境内收货人名称（外文）"));
            DecHeadNodeMapping.add(new NodeMapping("CorrelationNo", "CORRELATION_NO", "关联号码"));
            DecHeadNodeMapping.add(new NodeMapping("EdiRemark2", "EDI_REMARK_2", "EDI申报备注2"));
            DecHeadNodeMapping.add(new NodeMapping("TradeCoScc", "TRADE_CO_SCC", "经营单位统一编码"));
            DecHeadNodeMapping.add(new NodeMapping("AgentCodeScc", "AGENT_CODE_SCC", "申报代码统一编码"));
            DecHeadNodeMapping.add(new NodeMapping("OwnerCodeScc", "OWNER_CODE_SCC", "货主单位统一编码"));
            DecHeadNodeMapping.add(new NodeMapping("TradeCiqCode", "TRADE_CIQ_CODE", "境内收发货人检验检疫编码"));
            DecHeadNodeMapping.add(new NodeMapping("OwnerCiqCode", "OWNER_CIQ_CODE", "消费使用/生产销售单位检验检疫编码"));
            DecHeadNodeMapping.add(new NodeMapping("DeclCiqCode", "DECL_CIQ_CODE", "申报单位检验检疫编码"));
        }

        /**
         * 两段转入
         * */
        public static final List<NodeMapping> DecTpAccessTypeMapping = new ArrayList<>();

        static {
            DecTpAccessTypeMapping.add(new NodeMapping("TransitionApply", "TRANSITION_APPLY", "转场申请"));
            DecTpAccessTypeMapping.add(new NodeMapping("TransitionSite", "TRANSITION_SITE", "转入场所场地"));
            DecTpAccessTypeMapping.add(new NodeMapping("ConditionalLiftoffApply", "CONDITIONAL_LIFTOFF_APPLY", "附条件提离申请"));
            DecTpAccessTypeMapping.add(new NodeMapping("PortDestMergeCheckApply", "PORT_DEST_MERGE_CHECK_APPLY", "口岸与目的地合并检查申请"));
        }

        /**
         * 表体映射
         * */
        public static final List<NodeMapping> DecListNodeMapping = new ArrayList<>();

        static {
            DecListNodeMapping.add(new NodeMapping("CodeTS", "CODE_T", "HS编码"));
            DecListNodeMapping.add(new NodeMapping("ContrItem", "CONTR_ITEM", "新贸序号"));
            DecListNodeMapping.add(new NodeMapping("DeclPrice", "DECL_PRICE", "申报单价"));
            DecListNodeMapping.add(new NodeMapping("DutyMode", "DUTY_MODE", "征减免税方式"));
            DecListNodeMapping.add(new NodeMapping("GModel", "G_MODEL", "规格型号"));
            DecListNodeMapping.add(new NodeMapping("GName", "G_NAME", "商品名称"));
            DecListNodeMapping.add(new NodeMapping("OriginCountry", "ORIGIN_COUNTRY_STD", "原产/目的国"));
            DecListNodeMapping.add(new NodeMapping("TradeCurr", "TRADE_CURR_STD", "成交币制"));
            DecListNodeMapping.add(new NodeMapping("DeclTotal", "DECL_TOTAL", "申报总价"));
            DecListNodeMapping.add(new NodeMapping("GQty", "G_QTY", "申报数量"));
            DecListNodeMapping.add(new NodeMapping("FirstQty", "QTY_CONV", "法定第一数量"));
            DecListNodeMapping.add(new NodeMapping("SecondQty", "QTY_2", "法定第二数量"));
            DecListNodeMapping.add(new NodeMapping("GUnit", "G_UNIT_STD", "申报计量单位"));
            DecListNodeMapping.add(new NodeMapping("FirstUnit", "FIRST_UNIT_STD", "法定第一计量单位"));
            DecListNodeMapping.add(new NodeMapping("SecondUnit", "SECOND_UNIT_STD", "法定第二计量单位"));
            DecListNodeMapping.add(new NodeMapping("UseTo", "USE_TO", "用途"));
            DecListNodeMapping.add(new NodeMapping("WorkUsd", "WORK_USD", "工缴费"));
            DecListNodeMapping.add(new NodeMapping("DestinationCountry", "DESTINATION_COUNTRY_STD", "目的国"));
            DecListNodeMapping.add(new NodeMapping("CiqCode", "CIQ_CODE", "检验检疫编码"));
            DecListNodeMapping.add(new NodeMapping("DeclGoodsEname", "DECL_GOODS_ENAME", "商品英文名称"));
            DecListNodeMapping.add(new NodeMapping("OrigPlaceCode", "ORIG_PLACE_CODE", "原产地区代码"));
            DecListNodeMapping.add(new NodeMapping("Purpose", "PURPOSE", "用途代码"));
            DecListNodeMapping.add(new NodeMapping("ProdValidDt", "PROD_VALID_DT", "产品有效期"));
            DecListNodeMapping.add(new NodeMapping("ProdQgp", "PROD_QGP", "产品保质期"));
            DecListNodeMapping.add(new NodeMapping("GoodsAttr", "GOODS_ATTR", "货物属性代码"));
            DecListNodeMapping.add(new NodeMapping("Stuff", "STUFF", "成份/原料/组份"));
            DecListNodeMapping.add(new NodeMapping("Uncode", "UN_CODE", "UN编码"));
            DecListNodeMapping.add(new NodeMapping("DangName", "DANG_NAME", "危险货物名称"));
            DecListNodeMapping.add(new NodeMapping("DangPackType", "DANG_PACK_TYPE", "危包类别"));
            DecListNodeMapping.add(new NodeMapping("DangPackSpec", "DANG_PACK_SPEC", "危包规格"));
            DecListNodeMapping.add(new NodeMapping("EngManEntCnm", "ENG_MAN_ENT_CNM", "境外生产企业名称"));
            DecListNodeMapping.add(new NodeMapping("NoDangFlag", "NO_DANG_FLAG", "?"));
            DecListNodeMapping.add(new NodeMapping("DestCode", "DEST_CODE", "目的地代码"));
            DecListNodeMapping.add(new NodeMapping("GoodsSpec", "GOODS_SPEC", "检验检疫货物规格"));
            DecListNodeMapping.add(new NodeMapping("GoodsModel", "GOODS_MODEL", "货物型号"));
            DecListNodeMapping.add(new NodeMapping("GoodsBrand", "GOODS_BRAND", "货物品牌"));
            DecListNodeMapping.add(new NodeMapping("ProduceDate", "PRODUCE_DATE", "生产日期"));
            DecListNodeMapping.add(new NodeMapping("ProdBatchNo", "PROD_BATCH_NO", "生产批号"));
            DecListNodeMapping.add(new NodeMapping("DistrictCode", "DISTRICT_CODE", "境内目的地/境内货源地"));
            DecListNodeMapping.add(new NodeMapping("CiqName", "CIQ_NAME", "检验检疫名称"));
            DecListNodeMapping.add(new NodeMapping("MnufctrRegno", "MNUFCTR_REGNO", "生产单位注册号"));
            DecListNodeMapping.add(new NodeMapping("MnufctrRegName", "MNUFCTR_REG_NAME", "生产单位名称"));
        }

        /**
         * 产品资质
         * */
        public static final List<NodeMapping> DecGoodsLimitNodeMapping = new ArrayList<>();

        static {
            DecGoodsLimitNodeMapping.add(new NodeMapping("GoodsNo", "GOODS_NO", "商品序号"));
            DecGoodsLimitNodeMapping.add(new NodeMapping("LicTypeCode", "LIC_TYPE_CODE", "许可证类别代码"));
            DecGoodsLimitNodeMapping.add(new NodeMapping("LicenceNo", "LICENCE_NO", "许可证编号"));
            DecGoodsLimitNodeMapping.add(new NodeMapping("LicWrtofDetailNo", "LIC_WRTOF_DETAIL_NO", "许可证核销明细序号"));
            DecGoodsLimitNodeMapping.add(new NodeMapping("LicWrtofQty", "LIC_WRTOF_QTY", "许可证核销数量"));
            DecGoodsLimitNodeMapping.add(new NodeMapping("LicWrtofQtyUnit", "LIC_WRTOF_QTY_UNIT", "许可证核销数量单位"));
        }

        /**
         * 产品资质VIN
         * */
        public static final List<NodeMapping> DecGoodsLimitVinNodeMapping = new ArrayList<>();

        static {
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("LicenceNo", "LICENCE_NO", "许可证编号"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("LicTypeCode", "LICTYPE_CODE", "许可证类别代码"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("VinNo", "VIN_NO", "VIN序号"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("BillLadDate", "BILL_LAD_DATE", "提/运单日期"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("QualityQgp", "QUALITY_QGP", "质量保质期"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("MotorNo", "MOTOR_NO", "发动机号或电机号"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("VinCode", "VIN_CODE", "车辆识别代码（VIN）"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("ChassisNo", "CHASSIS_NO", "底盘(车架)号"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("InvoiceNum", "INVOICE_NUM", "发票所列数量"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("ProdCnnm", "PROD_CNNM", "品名（中文名称）"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("ProdEnnm", "PROD_ENNM", "品名（英文名称）"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("QualityQgp", "PROD_ENNM", "品名（英文名称）"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("ModelEn", "MODEL_EN", "型号（英文）"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("PricePerUnit", "PRICE_PER_UNIT", "单价"));
            DecGoodsLimitVinNodeMapping.add(new NodeMapping("InvoiceNo", "INVOICE_NO", "发票号"));
        }

        /**
         * 集装箱
         * */
        public static final List<NodeMapping> ContainerNodeMapping = new ArrayList<>();

        static {
            ContainerNodeMapping.add(new NodeMapping("ContainerId", "CONTAINER_ID", "集装箱号"));
            ContainerNodeMapping.add(new NodeMapping("ContainerMd", "CONTAINER_MD_STD", "标准码"));
            ContainerNodeMapping.add(new NodeMapping("GoodsNo", "GOODS_NO", "商品项号"));
            ContainerNodeMapping.add(new NodeMapping("LclFlag", "LCL_FLAG", "拼箱标识"));
            ContainerNodeMapping.add(new NodeMapping("ContainerWt", "CONTAINER_WT", "集装箱自重"));
        }

        /**
         * 随附单证
         * */
        public static final List<NodeMapping> LicenseDocuNodeMapping = new ArrayList<>();

        static {
            LicenseDocuNodeMapping.add(new NodeMapping("DocuCode", "DOCU_CODE", "单证代码"));
            LicenseDocuNodeMapping.add(new NodeMapping("CertCode", "CERT_CODE", "单证编号"));
        }

        /**
         * 所需单证
         * */
        public static final List<NodeMapping> DecRequestCertNodeMapping = new ArrayList<>();

        static {
            DecRequestCertNodeMapping.add(new NodeMapping("AppCertCode", "APP_CERT_CODE", "申请单证代码"));
            DecRequestCertNodeMapping.add(new NodeMapping("ApplOri", "APPL_ORI", "申请单证正本数"));
            DecRequestCertNodeMapping.add(new NodeMapping("ApplCopyQuan", "APPL_COPY_QUAN", "申请单证副本数"));
        }

        /**
         * 企业资质
         * */
        public static final List<NodeMapping> DecCopLimitNodeMapping = new ArrayList<>();

        static {
            DecCopLimitNodeMapping.add(new NodeMapping("EntQualifNo", "ENT_QUALIF_NO", "企业资质编号"));
            DecCopLimitNodeMapping.add(new NodeMapping("EntQualifTypeCode", "ENT_QUALIF_TYPE_CODE", "企业资质类别代码"));
        }

        /**
         * 企业承诺
         * */
        public static final List<NodeMapping> DecCopPromiseNodeMapping = new ArrayList<>();

        static {
            DecCopPromiseNodeMapping.add(new NodeMapping("DeclaratioMaterialCode", "DECLARATION_MATERIAL_CODE", "证明/声明材料代码"));
        }

        /**
         * 其他包装
         * */
        public static final List<NodeMapping> DecOtherPackNodeMapping = new ArrayList<>();

        static {
            DecOtherPackNodeMapping.add(new NodeMapping("PackQty", "PACK_QTY", "包装件数"));
            DecOtherPackNodeMapping.add(new NodeMapping("PackType", "PACK_TYPE", "包装材料种类"));
        }

        /**
         * 特许权使用费
         * */
        public static final List<NodeMapping> DecRoyaltyFeeNodeMapping = new ArrayList<>();

        static {
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PricePreDeterminNo", "PRICE_PRE_DETERMIN_NO", "价格预裁定编号"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("TaxRoyaltyDeclType", "TAX_ROYALTY_DECL_TYPE", "应税特许权使用费申报情形"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("ContractNo", "CONTRACT_NO", "合同/协议号"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("Authorizer", "AUTHORIZER", "授权方"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("AuthorizedPerson", "AUTHORIZED_PERSON", "被授权方"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PayType", "PAY_TYPE", "支付方式"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PayTime", "PAY_TIME", "支付时间"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PayPeriod", "PAY_PERIOD", "支付计提周期"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("EffectiveDateTime", "EFFECTIVE_DATE_TIME", "合同/协议起始执行时间"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("ExpirationDateTime", "EXPIRATION_DATE_TIME", "合同协议终止时间"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("RoyaltyAmount", "ROYALTY_AMOUNT", "特许权使用费金额"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("Curr", "CURR", "币制"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("RoyaltyFeeType", "ROYALTY_FEE_TYPE", "特许权使用费类型"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("EdocType", "EDOC_TYPE", "随附材料清单类型"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("Statment", "STATMENT", "说明"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("IsSecret", "IS_SECRET", "是否保密"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("IsCusAudit", "IS_CUS_AUDIT", "是否经过海关审核认定"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("IsCusPricePreDetermin", "IS_CUS_PRICE_PRE_DETERMIN", "是否经过海关价格预裁定"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("IssueDateTime", "ISSUE_DATE_TIME", "合同/协议签约时间"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PeriodStartDate", "PERIOD_START_DATE", "本次支付对应的计提周期起始时间"));
            DecRoyaltyFeeNodeMapping.add(new NodeMapping("PeriodEndDate", "PERIOD_END_DATE", "本次支付对应的计提周期终止时间"));
        }

        /**
         * 原产地证等
         * */
        public static final List<NodeMapping> EcoRelationNodeMapping = new ArrayList<>();

        static {
            EcoRelationNodeMapping.add(new NodeMapping("CertType", "CERT_TYPE", "随附单证代码"));
            EcoRelationNodeMapping.add(new NodeMapping("EcoCertNo", "ECO_CERT_NO", "随附单证编号"));
            EcoRelationNodeMapping.add(new NodeMapping("DecGNo", "DEC_G_NO", "表体序号"));
            EcoRelationNodeMapping.add(new NodeMapping("EcoGNo", "ECO_G_NO", "单证项号"));
        }

    }

    /**
     * 修撤单报文节点映射
     *
     * @author ZJ
     * */
    private static final class DecModCusMessageNodeMapping {

        /**
         * 表头
         * */
        public static final List<NodeMapping> HEAD = new ArrayList<>();

        static {
            HEAD.add(new NodeMapping("DecModSeqNo", "DECMODSEQNO", "修撤单统一编号"));
            HEAD.add(new NodeMapping("DecModType", "DECMODTYPE", "修撤单类型"));
            HEAD.add(new NodeMapping("EntryId", "PRE_ENTRY_ID", "报关单号"));
            HEAD.add(new NodeMapping("CustomsCode", "?", "申报地海关"));
            HEAD.add(new NodeMapping("TradeCode", "?", "收发货人代码"));
            HEAD.add(new NodeMapping("TradeName", "?", "收发货人名称"));
            HEAD.add(new NodeMapping("AgentCode", "TRADE_CODE", "企业代码"));
            HEAD.add(new NodeMapping("AgentName", "?", "企业名称"));
            HEAD.add(new NodeMapping("DecModNote", "DECMODNOTE", "修撤单原因"));
            HEAD.add(new NodeMapping("CheckMark", "CHECKMARK", "审查表识"));
            HEAD.add(new NodeMapping("DecSeqNo", "DECSEQNO", "报关单统一编号"));
            HEAD.add(new NodeMapping("Sign", "SIGNTXT", "加签信息"));
            HEAD.add(new NodeMapping("SignTime", "SIGN_TIME", "加签时间"));
            HEAD.add(new NodeMapping("IeFlag", "?", "进出口标识"));
            HEAD.add(new NodeMapping("OperType", "OPERTYPE", "操作类型"));
            HEAD.add(new NodeMapping("IcCode", "IC_CODE", "IC卡号"));
            HEAD.add(new NodeMapping("EntOpName", "ENTOPNAME", "联系人"));
            HEAD.add(new NodeMapping("EntOpTele", "ENTOPTELE", "联系方式"));
            HEAD.add(new NodeMapping("FeedDept", "FEEDDEPT", "岗位"));
            HEAD.add(new NodeMapping("TradeCreditCode", "?", "境内收发货人统一社会信用代码"));
            HEAD.add(new NodeMapping("AgentCreditCode", "?", "申报单位统一社会信用代码"));
        }

        /**
         * 表体
         * */
        public static final List<NodeMapping> LIST = new ArrayList<>();

        static {
            LIST.add(new NodeMapping("No", "?", "序号"));
            LIST.add(new NodeMapping("FieldCode", "QP_FIELDCODE", "字段代码"));
            LIST.add(new NodeMapping("FieldName", "QP_FIELDNAME", "字段名"));
            LIST.add(new NodeMapping("OldValue", "OLDVALUE", "字段原值"));
            LIST.add(new NodeMapping("NewValue", "NEWVALUE", "字段新值"));
            LIST.add(new NodeMapping("OldName", "OLDNAME", "字段原值参数表对应中文名称"));
            LIST.add(new NodeMapping("NewName", "NEWNAME", "字段新值参数表对应中文名称"));
        }

    }

    /**
     * 节点映射
     *
     * @author ZJ
     * */
    private static final class NodeMapping {

        /**
         * 数据库字段
         * */
        public final String dbField;

        /**
         * 报文节点
         * */
        public final String node;

        /**
         * 名称
         * */
        public final String name;

        /**
         * 不能为空
         * */
        public final boolean notNull;

        /**
         * constructor
         *
         * @param dbField dbField
         * @param node node
         * @param name name
         * @param notNull notNull
         * */
        private NodeMapping(String node, String dbField, String name, boolean notNull) {
            this.node = node;
            this.dbField = dbField;
            this.name = name;
            this.notNull = notNull;
        }

        /**
         * constructor
         *
         * @param dbField dbField
         * @param node node
         * @param name name
         * */
        private NodeMapping(String node, String dbField, String name) {
            this(node, dbField, name, false);
        }

    }

}