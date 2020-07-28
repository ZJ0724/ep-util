package com.easipass.epUtil.entity.cusMessage;

import com.easipass.epUtil.api.websocket.BaseWebsocketApi;
import com.easipass.epUtil.entity.CusMessage;
import com.easipass.epUtil.entity.VO.CusMessageComparisonVO;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.exception.CusMessageException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.StringUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.*;

/**
 * 报关单报文
 *
 * @author ZJ
 * */
public final class FormCusMessage extends CusMessage {

    /**
     * 表头
     * */
    private final Element decHead;

    /**
     * 表体
     * */
    private final List<Element> DecList = new ArrayList<>();

    /**
     * ediNo
     * */
    private final String ediNo;

    /**
     * 集装箱
     * */
    private final List<Element> DecContainers = new ArrayList<>();

    /**
     * 随附单证
     * */
    private final List<Element> LicenseDocu = new ArrayList<>();

    /**
     * 申请单证
     * */
    private final List<Element> DecRequestCert = new ArrayList<>();

    /**
     * 企业资质
     * */
    private final List<Element> DecCopLimit = new ArrayList<>();

    /**
     * 企业承诺
     * */
    private final List<Element> DecCopPromise = new ArrayList<>();

    /**
     * 其他包装
     * */
    private final List<Element> DecOtherPack = new ArrayList<>();

    /**
     * 特许权使用费
     * */
    private final Element DecRoyaltyFee;

    /**
     * 构造函数
     *
     * @param multipartFile 前端传过来的文件
     * */
    public FormCusMessage(MultipartFile multipartFile) {
        Element rootElement;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            rootElement = XmlUtil.getDocument_v2(inputStream).getRootElement();
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw new CusMessageException("不是正确的报关单报文");
        }

        this.decHead = rootElement.element("DecHead");
        if (this.decHead == null) {
            throw new CusMessageException("不是正确的报关单报文");
        }

        Element DecSign = rootElement.element("DecSign");
        if (DecSign == null) {
            throw new CusMessageException("不是正确的报关单报文");
        }
        Element ClientSeqNo = DecSign.element("ClientSeqNo");
        if (ClientSeqNo == null) {
            throw new CusMessageException("不是正确的报关单报文");
        }
        String ediNo = ClientSeqNo.getText();
        if ("".equals(ediNo)) {
            throw new CusMessageException("不是正确的报关单报文");
        }
        this.ediNo = ediNo;

        Element DecLists = rootElement.element("DecLists");
        if (DecLists == null) {
            throw new CusMessageException("不是正确的报关单报文");
        }
        List<?> DecListList = DecLists.elements("DecList");
        for (Object element : DecListList) {
            this.DecList.add((Element) element);
        }

        // 集装箱
        Element DecContainers = rootElement.element("DecContainers");

        if (DecContainers != null) {
            List<?> ContainerList = DecContainers.elements("Container");

            for (Object element : ContainerList) {
                this.DecContainers.add((Element) element);
            }
        }

        // 随附单证
        Element DecLicenseDocus = rootElement.element("DecLicenseDocus");

        if (DecLicenseDocus != null) {
            List<?> LicenseDocu = DecLicenseDocus.elements("LicenseDocu");

            for (Object element : LicenseDocu) {
                this.LicenseDocu.add((Element) element);
            }
        }

        // 申请单证
        Element DecRequestCerts = rootElement.element("DecRequestCerts");

        if (DecRequestCerts != null) {
            List<?> DecRequestCert = DecRequestCerts.elements("DecRequestCert");

            for (Object element : DecRequestCert) {
                this.DecRequestCert.add((Element) element);
            }
        }

        // 企业资质
        Element DecCopLimits = rootElement.element("DecCopLimits");

        if (DecCopLimits != null) {
            List<?> DecCopLimit = DecCopLimits.elements("DecCopLimit");

            for (Object element : DecCopLimit) {
                this.DecCopLimit.add((Element) element);
            }
        }

        // 企业承诺
        Element DecCopPromises = rootElement.element("DecCopPromises");

        if (DecCopPromises != null) {
            List<?> DecCopPromise = DecCopPromises.elements("DecCopPromise");

            for (Object element : DecCopPromise) {
                this.DecCopPromise.add((Element) element);
            }
        }

        // 其他包装
        Element DecOtherPacks = rootElement.element("DecOtherPacks");

        if (DecOtherPacks != null) {
            List<?> DecOtherPack = DecOtherPacks.elements("DecOtherPack");

            for (Object element : DecOtherPack) {
                this.DecOtherPack.add((Element) element);
            }
        }

        // DecRoyaltyFee 特许权使用费
        this.DecRoyaltyFee = rootElement.element("DecRoyaltyFee");
    }

    @Override
    public void comparison(BaseWebsocketApi baseWebsocketApi) {
        // 数据库
        SWGDOracle SWGDOracle = new SWGDOracle();

        try {
            // 检查数据库连接
            SWGDOracle.connect();

            // ediNo
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[ediNo: " + this.ediNo + "]"));

            // 比对表头
            String formHeadMessage = "[表头]";

            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(formHeadMessage));

            ResultSet dbFormHead = SWGDOracle.queryFormHead(this.ediNo);

            if (!checkResultSet(dbFormHead, formHeadMessage, baseWebsocketApi)) {
                return;
            }

            Set<String> formHeadKeys = FormCusMessageNodeMapping.FORM_HEAD_MAPPING.keySet();

            for (String key : formHeadKeys) {
                MapKeyValue mapKeyValue = getKeyValue(this.decHead, FormCusMessageNodeMapping.FORM_HEAD_MAPPING, key, dbFormHead);
                String key1 = mapKeyValue.getKey1();
                String nodeValue = mapKeyValue.getNodeValue();
                String dbValue = mapKeyValue.getDbValue();

                // IEDate、DespDate、CmplDschrgDt特殊处理
                if (key1.equals("IEDate") || key1.equals("DespDate") || key1.equals("CmplDschrgDt")) {
                    mapKeyValue.setDbValue(DateUtil.formatDateYYYYMMdd(dbValue));
                }

                // DeclareName特殊处理
                if ("DeclareName".equals(key1)) {
                    // IE_TYPE
                    String ieType = getDbValue(dbFormHead, "IE_TYPE");

                    // 如果IE_TYPE为0，则DeclareName可以为null
                    if ("0".equals(ieType)) {
                        if ("".equals(nodeValue)) {
                            mapKeyValue.setDbValue("");
                        }
                    }
                }

                // 特殊处理PackNo
                if ("PackNo".equals(key1)) {
                    // 如果nodeValue为0，并且dbValue为null，则将dbValue设置成0
                    if ("0".equals(nodeValue) && dbValue == null) {
                        mapKeyValue.setDbValue("0");
                    }
                }

                // 特殊处理Type
                if ("Type".equals(key1)) {
                    String[] values = new String[]{"TYPE_2", "TYPE_3", "TYPE_4", "TYPE_5"};
                    String newDbValue = "  ";

                    if ("".equals(nodeValue)) {
                        newDbValue = "";
                    }

                    for (String value : values) {
                        String s = getDbValue(dbFormHead, value);
                        newDbValue = StringUtil.append(newDbValue, s);
                    }
                    mapKeyValue.setDbValue(newDbValue);
                }

                comparison(mapKeyValue, baseWebsocketApi);
            }

            // 比对表体数据
            int size = this.DecList.size();
            Set<String> ListKeys = FormCusMessageNodeMapping.FORM_LIST_MAPPING.keySet();

            for (int i = 0; i < size; i++) {
                String formListMessage = "[表体 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(formListMessage));

                ResultSet resultSet = SWGDOracle.queryFormList(ediNo, i + "");

                if (!checkResultSet(resultSet, formListMessage, baseWebsocketApi)) {
                    continue;
                }

                // codeTs
                String codeTs = "";
                Element element = this.DecList.get(i);

                for (String key : ListKeys) {
                    MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.FORM_LIST_MAPPING, key, resultSet);
                    String key1 = mapKeyValue.getKey1();
                    String dbValue = mapKeyValue.getDbValue();
                    String value = mapKeyValue.getValue();
                    String nodeValue = mapKeyValue.getNodeValue();

                    // ProdValidDt特殊处理
                    if (key1.equals("ProdValidDt")) {
                        mapKeyValue.setDbValue(DateUtil.formatDateYYYYMMdd(dbValue));
                    }

                    // 特殊处理GNo
                    if (key1.equals("GNo")) {
                        mapKeyValue.setDbValue((Integer.parseInt(dbValue) + 1) + "");
                    }

                    // 特殊处理CodeTS
                    if ("CODE_T".equals(value)) {
                        codeTs = StringUtil.append(codeTs, dbValue);
                        continue;
                    }
                    if ("CODE_S".equals(value)) {
                        if (dbValue == null) {
                            dbValue = nodeValue.substring(nodeValue.length() - 2);
                        }
                        codeTs = StringUtil.append(codeTs, dbValue);
                        mapKeyValue.setDbValue(codeTs);
                    }

                    comparison(mapKeyValue, baseWebsocketApi);
                }

                // 比对产品资质 DecGoodsLimits
                Element DecGoodsLimits = element.element("DecGoodsLimits");

                if (DecGoodsLimits == null) {
                    continue;
                }

                List<?> DecGoodsLimit_list = DecGoodsLimits.elements("DecGoodsLimit");
                int DecGoodsLimit_listSize = DecGoodsLimit_list.size();
                Set<String> keys = FormCusMessageNodeMapping.DEC_GOODS_LIMIT_MAPPING.keySet();

                for (int j = 0; j < DecGoodsLimit_listSize; j++) {
                    String decGoodsLimitMessage = formListMessage + " -> [产品资质 - " + (j + 1) + "]";

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decGoodsLimitMessage));

                    ResultSet DecGoodsLimitResultSet = SWGDOracle.queryDecGoodsLimit(this.ediNo, i + "", (j + 1) + "");

                    if (!checkResultSet(DecGoodsLimitResultSet,decGoodsLimitMessage , baseWebsocketApi)) {
                        continue;
                    }

                    Element DecGoodsLimit = (Element) DecGoodsLimit_list.get(j);

                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(
                                DecGoodsLimit,
                                FormCusMessageNodeMapping.DEC_GOODS_LIMIT_MAPPING,
                                key,
                                DecGoodsLimitResultSet
                        );

                        comparison(mapKeyValue, baseWebsocketApi);
                    }

                    // 比对产品资质VIN DecGoodsLimitVin
                    List<?> DecGoodsLimitVin_list = DecGoodsLimit.elements("DecGoodsLimitVin");
                    int DecGoodsLimitVin_listSize = DecGoodsLimitVin_list.size();

                    if (DecGoodsLimitVin_listSize == 0) {
                        continue;
                    }

                    for (int k = 0; k < DecGoodsLimitVin_listSize; k++) {
                        String DecGoodsLimitVinMessage = decGoodsLimitMessage + " -> [产品资质VIN - " + (k + 1) + "]";

                        baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(DecGoodsLimitVinMessage));

                        ResultSet decGoodsLimitVinResultSet = SWGDOracle.queryDecGoodsLimitVin(getDbValue(DecGoodsLimitResultSet, "GUID"), (k + 1) + "");

                        if (!checkResultSet(decGoodsLimitVinResultSet, DecGoodsLimitVinMessage, baseWebsocketApi)) {
                            continue;
                        }

                        Set<String> decGoodsLimitVinKeys = FormCusMessageNodeMapping.DEC_GOODS_LIMIT_VIN_MAPPING.keySet();

                        for (String key : decGoodsLimitVinKeys) {
                            MapKeyValue mapKeyValue = getKeyValue(
                                    (Element) DecGoodsLimitVin_list.get(k),
                                    FormCusMessageNodeMapping.DEC_GOODS_LIMIT_VIN_MAPPING,
                                    key,
                                    decGoodsLimitVinResultSet
                            );

                            // 特殊处理BillLadDate
                            if ("BillLadDate".equals(mapKeyValue.getKey1())) {
                                mapKeyValue.setDbValue(DateUtil.formatDate(mapKeyValue.getDbValue(), "yyyy-MM-dd 00:00:00", "yyyyMMdd"));
                            }

                            comparison(mapKeyValue, baseWebsocketApi);
                        }
                    }
                }
            }

            // 比对集装箱信息
            int containerSize = this.DecContainers.size();

            for (int i = 0; i < containerSize; i ++) {
                String containerMessage = "[集装箱 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(containerMessage));

                ResultSet resultSet = SWGDOracle.queryFormContainer(ediNo, i + "");

                if (!checkResultSet(resultSet, containerMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> containersKeys = FormCusMessageNodeMapping.FORM_CONTAINER_MAPPING.keySet();

                for (String key : containersKeys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.DecContainers.get(i),
                            FormCusMessageNodeMapping.FORM_CONTAINER_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对随附单证
            int licenseDocuSize = this.LicenseDocu.size();

            for (int i = 0; i < licenseDocuSize; i++) {
                String licenseDocuMessage = "[随附单证 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(licenseDocuMessage));

                ResultSet resultSet = SWGDOracle.queryFormCertificate(ediNo, i + "");

                if (!checkResultSet(resultSet, licenseDocuMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> certificateKeys = FormCusMessageNodeMapping.FORM_CERTIFICATE_MAPPING.keySet();

                for (String key : certificateKeys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.LicenseDocu.get(i),
                            FormCusMessageNodeMapping.FORM_CERTIFICATE_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对申请单证
            int DecRequestCertSize = this.DecRequestCert.size();

            for (int i = 0; i < DecRequestCertSize; i++) {
                String decRequestCerMessage = "[申请单证 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decRequestCerMessage));

                ResultSet resultSet = SWGDOracle.queryDecRequestCert(this.ediNo, i + "");

                if (!checkResultSet(resultSet, decRequestCerMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> keys = FormCusMessageNodeMapping.DEC_REQUEST_CERT_MAPPING.keySet();

                for (String key : keys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.DecRequestCert.get(i),
                            FormCusMessageNodeMapping.DEC_REQUEST_CERT_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对企业资质
            int decCopLimitSize = this.DecCopLimit.size();

            for (int i = 0; i < decCopLimitSize; i++) {
                String decCopLimitMessage = "[企业资质 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decCopLimitMessage));

                ResultSet resultSet = SWGDOracle.queryDecCopLimit(this.ediNo, (i + 1) + "");

                if (!checkResultSet(resultSet, decCopLimitMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> keys = FormCusMessageNodeMapping.DEC_COP_LIMIT_MAPPING.keySet();

                for (String key : keys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.DecCopLimit.get(i),
                            FormCusMessageNodeMapping.DEC_COP_LIMIT_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对企业承诺
            int decCopPromisesSize = this.DecCopPromise.size();

            for (int i = 0; i < decCopPromisesSize; i++) {
                String decCopPromiseMessage = "[企业承诺 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decCopPromiseMessage));

                ResultSet resultSet = SWGDOracle.queryDecCopPromise(ediNo, (i + 1) + "");

                if (!checkResultSet(resultSet, decCopPromiseMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> keys = FormCusMessageNodeMapping.DEC_COP_PROMISE_MAPPING.keySet();

                for (String key : keys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.DecCopPromise.get(i),
                            FormCusMessageNodeMapping.DEC_COP_PROMISE_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 使用人信息暂时不进行比对
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[使用人信息] 暂时不进行比对"));

            // 标记号码附件暂时不进行比对
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[标记号码附件] 暂时不进行比对"));

            // 比对其他包装
            int decOtherPackSize = this.DecOtherPack.size();

            for (int i = 0; i < decOtherPackSize; i++) {
                String decOtherPackMessage = "[其他包装 - " + (i + 1) + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decOtherPackMessage));

                ResultSet resultSet = SWGDOracle.queryDecOtherPack(ediNo, (i + 1) + "");

                if (!checkResultSet(resultSet, decOtherPackMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> keys = FormCusMessageNodeMapping.DEC_OTHER_PACK_MAPPING.keySet();

                for (String key : keys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            this.DecOtherPack.get(i),
                            FormCusMessageNodeMapping.DEC_OTHER_PACK_MAPPING,
                            key,
                            resultSet
                    );

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对特许权使用费
            if (this.DecRoyaltyFee != null && this.DecRoyaltyFee.elements().size() != 0) {
                String decRoyaltyFeeMessage = "[特许权使用费]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decRoyaltyFeeMessage));

                ResultSet decRoyaltyFeeResultSet = SWGDOracle.queryDecRoyaltyFee(this.ediNo);

                if (checkResultSet(decRoyaltyFeeResultSet, decRoyaltyFeeMessage, baseWebsocketApi)) {
                    Set<String> decRoyaltyFeeKeys = FormCusMessageNodeMapping.DEC_ROYALTY_FEE_MAPPING.keySet();

                    for (String decRoyaltyFeeKey : decRoyaltyFeeKeys) {
                        MapKeyValue mapKeyValue = getKeyValue(
                                this.DecRoyaltyFee,
                                FormCusMessageNodeMapping.DEC_ROYALTY_FEE_MAPPING,
                                decRoyaltyFeeKey,
                                decRoyaltyFeeResultSet
                        );

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            }

            // 比对完成
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[NONE]"));
        } catch (OracleException e) {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getErrorType(e.getMessage()));
        } finally {
            // 关闭数据库
            SWGDOracle.close();
            // 关闭websocket连接
            baseWebsocketApi.close();
        }
    }

    /**
     * 报关单报文节点映射
     *
     * @author ZJ
     * */
    private static final class FormCusMessageNodeMapping {

        /**
         * 表头节点映射
         * */
        protected static final Map<String, String> FORM_HEAD_MAPPING = new LinkedHashMap<>();

        static {
            FORM_HEAD_MAPPING.put("SeqNo[数据中心统一编号]", null);
            FORM_HEAD_MAPPING.put("Type[type]", "TYPE_2");
            FORM_HEAD_MAPPING.put("AgentCode[申报单位代码]", "AGENT_CODE");
            FORM_HEAD_MAPPING.put("AgentName[申报单位名称]", "AGENT_NAME");
            FORM_HEAD_MAPPING.put("ApprNo[批准文号]", "APPR_NO");
            FORM_HEAD_MAPPING.put("BillNo[提运单号]", "BILL_NO");
            FORM_HEAD_MAPPING.put("ContrNo[合同号]", "CONTR_NO");
            FORM_HEAD_MAPPING.put("CustomMaster[申报地海关]", "DECL_PORT");
            FORM_HEAD_MAPPING.put("CutMode[征免性质]", "CUT_MODE");
            FORM_HEAD_MAPPING.put("DistinatePort[经停港/指运港]", "DISTINATE_PORT_STD");
            FORM_HEAD_MAPPING.put("FeeCurr[运费/币制]", "FEE_CURR_STD");
            FORM_HEAD_MAPPING.put("FeeMark[运费/标记]", "FEE_MARK");
            FORM_HEAD_MAPPING.put("FeeRate[运费/率]", "FEE_RATE");
            FORM_HEAD_MAPPING.put("GrossWet[毛重]", "GROSS_WT");
            FORM_HEAD_MAPPING.put("IEDate[进出口日期]", "I_E_DATE");
            FORM_HEAD_MAPPING.put("IEPort[进出口岸]", "I_E_PORT");
            FORM_HEAD_MAPPING.put("InsurCurr[保险费/币制]", "INSUR_CURR_STD");
            FORM_HEAD_MAPPING.put("InsurMark[保险费/标记]", "INSUR_MARK");
            FORM_HEAD_MAPPING.put("InsurRate[保险费/率]", "INSUR_RATE");
            FORM_HEAD_MAPPING.put("LicenseNo[许可证编号]", "LICENSE_NO");
            FORM_HEAD_MAPPING.put("ManualNo[备案号]", "MANUAL_NO");
            FORM_HEAD_MAPPING.put("NetWt[净重]", "NET_WT");
            FORM_HEAD_MAPPING.put("NoteS[备注]", null);
            FORM_HEAD_MAPPING.put("OtherCurr[杂费/币制]", "OTHER_CURR_STD");
            FORM_HEAD_MAPPING.put("OtherMark[杂费/标记]", "OTHER_MARK");
            FORM_HEAD_MAPPING.put("OtherRate[杂费/率]", "OTHER_RATE");
            FORM_HEAD_MAPPING.put("OwnerCode[消费使用单位代码]", "OWNER_CODE");
            FORM_HEAD_MAPPING.put("OwnerName[消费使用单位名称]", "OWNER_NAME");
            FORM_HEAD_MAPPING.put("PackNo[件数]", "PACK_NO");
            FORM_HEAD_MAPPING.put("TradeCode[经营单位统一编码]", "TRADE_CO");
            FORM_HEAD_MAPPING.put("TradeCountry[起运国/运抵国]", "TRADE_COUNTRY_STD");
            FORM_HEAD_MAPPING.put("TradeMode[贸易方式]", "TRADE_MODE_STD");
            FORM_HEAD_MAPPING.put("TradeName[收发货人名称]", "TRADE_NAME");
            FORM_HEAD_MAPPING.put("TrafMode[运输方式]", "TRAF_MODE_STD");
            FORM_HEAD_MAPPING.put("TrafName[运输工具代码及名称]", "TRAF_NAME");
            FORM_HEAD_MAPPING.put("TransMode[成交方式]", "TRANS_MODE");
            FORM_HEAD_MAPPING.put("WrapType[包装种类]", "WRAP_TYPE_STD");
            FORM_HEAD_MAPPING.put("TypistNo[IC卡号]", "I_C_CODE");
            FORM_HEAD_MAPPING.put("BillType[备案清单类型]", "BILL_TYPE");
            FORM_HEAD_MAPPING.put("PromiseItmes[承诺事项]", "PROMISE_ITMES");
            FORM_HEAD_MAPPING.put("TradeAreaCode[贸易国别]", "TRADE_AREA_CODE_STD");
            FORM_HEAD_MAPPING.put("CheckFlow[查验分流]", "CHECK_FLOW");
            FORM_HEAD_MAPPING.put("TaxAaminMark[税收征管标记]", "TAX_AAMIN_MARK");
            FORM_HEAD_MAPPING.put("MarkNo[标记唛码]", "MARK_NO");
            FORM_HEAD_MAPPING.put("DespPortCode[启运口岸代码]", "DESP_PORT_CODE");
            FORM_HEAD_MAPPING.put("EntyPortCode[入境口岸代码]", "ENTY_PORT_CODE");
            FORM_HEAD_MAPPING.put("GoodsPlace[存放地点]", "GOODS_PLACE");
            FORM_HEAD_MAPPING.put("BLNo[B/L号]", "B_L_NO");
            FORM_HEAD_MAPPING.put("InspOrgCode[口岸检验检疫机关]", "INSP_ORG_CODE");
            FORM_HEAD_MAPPING.put("SpecDeclFlag[特种业务标识]", "SPEC_DECL_FLAG");
            FORM_HEAD_MAPPING.put("PurpOrgCode[目的地检验检疫机关]", "PURP_ORG_CODE");
            FORM_HEAD_MAPPING.put("DespDate[启运日期]", "DESP_DATE");
            FORM_HEAD_MAPPING.put("CmplDschrgDt[卸毕日期]", "CMPL_DSCHRG_DT");
            FORM_HEAD_MAPPING.put("CorrelationReasonFlag[关联理由]", "CORRELATION_REASON_FLAG");
            FORM_HEAD_MAPPING.put("VsaOrgCode[领证机关]", "VSA_ORG_CODE");
            FORM_HEAD_MAPPING.put("OrigBoxFlag[原集装箱标识]", "ORIG_BOX_FLAG");
            FORM_HEAD_MAPPING.put("DeclareName[报关员姓名]", "DECLARE_NAME");
            FORM_HEAD_MAPPING.put("NoOtherPack[无其他包装]", "NO_OTHER_PACK");
            FORM_HEAD_MAPPING.put("OrgCode[检验检疫受理机关]", "ORG_CODE");
            FORM_HEAD_MAPPING.put("OverseasConsignorCode[境外发货人代码]", "OVERSEAS_CONSIGNOR_CODE");
            FORM_HEAD_MAPPING.put("OverseasConsignorCname[境外发货人名称]", "OVERSEAS_CONSIGNOR_CNAME");
            FORM_HEAD_MAPPING.put("OverseasConsignorEname[境外发货人名称（外文）]", "OVERSEAS_CONSIGNOR_ENAME");
            FORM_HEAD_MAPPING.put("OverseasConsignorAddr[境外发货人地址]", "OVERSEAS_CONSIGNOR_ADDR");
            FORM_HEAD_MAPPING.put("OverseasConsigneeCode[境外收货人编码]", "OVERSEAS_CONSIGNEE_CODE");
            FORM_HEAD_MAPPING.put("OverseasConsigneeEname[境外收货人名称(外文)]", "OVERSEAS_CONSIGNEE_ENAME");
            FORM_HEAD_MAPPING.put("DomesticConsigneeEname[境内收货人名称（外文）]", "DOMESTIC_CONSIGNEE_ENAME");
            FORM_HEAD_MAPPING.put("CorrelationNo[关联号码]", "CORRELATION_NO");
            FORM_HEAD_MAPPING.put("EdiRemark2[EDI申报备注2]", "EDI_REMARK_2");
            FORM_HEAD_MAPPING.put("TradeCoScc[经营单位统一编码]", "TRADE_CO_SCC");
            FORM_HEAD_MAPPING.put("AgentCodeScc[申报代码统一编码]", "AGENT_CODE_SCC");
            FORM_HEAD_MAPPING.put("OwnerCodeScc[货主单位统一编码]", "OWNER_CODE_SCC");
            FORM_HEAD_MAPPING.put("TradeCiqCode[境内收发货人检验检疫编码]", "TRADE_CIQ_CODE");
            FORM_HEAD_MAPPING.put("OwnerCiqCode[消费使用/生产销售单位检验检疫编码]", "OWNER_CIQ_CODE");
            FORM_HEAD_MAPPING.put("DeclCiqCode[申报单位检验检疫编码]", "DECL_CIQ_CODE");
        }

        /**
         * 表体节点映射
         * */
        protected static final Map<String, String> FORM_LIST_MAPPING = new LinkedHashMap<>();

        static {
            FORM_LIST_MAPPING.put("ClassMark[?]", null);
            FORM_LIST_MAPPING.put("CodeTS", "CODE_T");
            FORM_LIST_MAPPING.put("CodeTS[HS编码]", "CODE_S");
            FORM_LIST_MAPPING.put("ContrItem[新贸序号]", "CONTR_ITEM");
            FORM_LIST_MAPPING.put("DeclPrice[申报单价]", "DECL_PRICE");
            FORM_LIST_MAPPING.put("DutyMode[征减免税方式]", "DUTY_MODE");
            FORM_LIST_MAPPING.put("Factor[?]", null);
            FORM_LIST_MAPPING.put("GModel[规格型号]", "G_MODEL");
            FORM_LIST_MAPPING.put("GName[商品名称]", "G_NAME");
            FORM_LIST_MAPPING.put("GNo[商品序号]", "G_NO");
            FORM_LIST_MAPPING.put("OriginCountry[原产/目的国]", "ORIGIN_COUNTRY_STD");
            FORM_LIST_MAPPING.put("TradeCurr[成交币制]", "TRADE_CURR_STD");
            FORM_LIST_MAPPING.put("DeclTotal[申报总价]", "DECL_TOTAL");
            FORM_LIST_MAPPING.put("GQty[申报数量]", "G_QTY");
            FORM_LIST_MAPPING.put("FirstQty[法定第一数量]", "QTY_CONV");
            FORM_LIST_MAPPING.put("SecondQty[法定第二数量]", "QTY_2");
            FORM_LIST_MAPPING.put("GUnit[申报计量单位]", "G_UNIT_STD");
            FORM_LIST_MAPPING.put("FirstUnit[法定第一计量单位]", "FIRST_UNIT_STD");
            FORM_LIST_MAPPING.put("SecondUnit[法定第二计量单位]", "SECOND_UNIT_STD");
            FORM_LIST_MAPPING.put("UseTo[用途]", "USE_TO");
            FORM_LIST_MAPPING.put("WorkUsd[工缴费]", "WORK_USD");
            FORM_LIST_MAPPING.put("ExgNo[?]", null);
            FORM_LIST_MAPPING.put("ExgVersion[?]", null);
            FORM_LIST_MAPPING.put("DestinationCountry[目的国]", "DESTINATION_COUNTRY_STD");
            FORM_LIST_MAPPING.put("CiqCode[检验检疫编码]", "CIQ_CODE");
            FORM_LIST_MAPPING.put("DeclGoodsEname[商品英文名称]", "DECL_GOODS_ENAME");
            FORM_LIST_MAPPING.put("OrigPlaceCode[原产地区代码]", "ORIG_PLACE_CODE");
            FORM_LIST_MAPPING.put("Purpose[用途代码]", "PURPOSE");
            FORM_LIST_MAPPING.put("ProdValidDt[产品有效期]", "PROD_VALID_DT");
            FORM_LIST_MAPPING.put("ProdQgp[产品保质期]", "PROD_QGP");
            FORM_LIST_MAPPING.put("GoodsAttr[货物属性代码]", "GOODS_ATTR");
            FORM_LIST_MAPPING.put("Stuff[成份/原料/组份]", "STUFF");
            FORM_LIST_MAPPING.put("Uncode[UN编码]", "UN_CODE");
            FORM_LIST_MAPPING.put("DangName[危险货物名称]", "DANG_NAME");
            FORM_LIST_MAPPING.put("DangPackType[危包类别]", "DANG_PACK_TYPE");
            FORM_LIST_MAPPING.put("DangPackSpec[危包规格]", "DANG_PACK_SPEC");
            FORM_LIST_MAPPING.put("EngManEntCnm[境外生产企业名称]", "ENG_MAN_ENT_CNM");
            FORM_LIST_MAPPING.put("NoDangFlag[?]", "NO_DANG_FLAG");
            FORM_LIST_MAPPING.put("DestCode[目的地代码]", "DEST_CODE");
            FORM_LIST_MAPPING.put("GoodsSpec[检验检疫货物规格]", "GOODS_SPEC");
            FORM_LIST_MAPPING.put("GoodsModel[货物型号]", "GOODS_MODEL");
            FORM_LIST_MAPPING.put("GoodsBrand[货物品牌]", "GOODS_BRAND");
            FORM_LIST_MAPPING.put("ProduceDate[生产日期]", "PRODUCE_DATE");
            FORM_LIST_MAPPING.put("ProdBatchNo[生产批号]", "PROD_BATCH_NO");
            FORM_LIST_MAPPING.put("DistrictCode[境内目的地/境内货源地]", "DISTRICT_CODE");
            FORM_LIST_MAPPING.put("CiqName[检验检疫名称]", "CIQ_NAME");
            FORM_LIST_MAPPING.put("MnufctrRegno[生产单位注册号]", "MNUFCTR_REGNO");
            FORM_LIST_MAPPING.put("MnufctrRegName[生产单位名称]", "MNUFCTR_REG_NAME");
        }

        /**
         * 集装箱映射
         * */
        protected static final Map<String, String> FORM_CONTAINER_MAPPING = new LinkedHashMap<>();

        static {
            FORM_CONTAINER_MAPPING.put("ContainerId[集装箱号]", "CONTAINER_ID");
            FORM_CONTAINER_MAPPING.put("ContainerMd[标准码]", "CONTAINER_MD_STD");
            FORM_CONTAINER_MAPPING.put("GoodsNo[商品项号]", "GOODS_NO");
            FORM_CONTAINER_MAPPING.put("LclFlag[拼箱标识]", "LCL_FLAG");
            FORM_CONTAINER_MAPPING.put("GoodsContaWt[?]", null);
            FORM_CONTAINER_MAPPING.put("ContainerWt[集装箱自重]", "CONTAINER_WT");
        }

        /**
         * 随附单证映射
         * */
        protected static final Map<String, String> FORM_CERTIFICATE_MAPPING = new LinkedHashMap<>();

        static {
            FORM_CERTIFICATE_MAPPING.put("DocuCode[单证代码]", "DOCU_CODE");
            FORM_CERTIFICATE_MAPPING.put("CertCode[单证编号]", "CERT_CODE");
        }

        /**
         * 所需单证映射
         * */
        protected static final Map<String, String> DEC_REQUEST_CERT_MAPPING = new LinkedHashMap<>();

        static {
            DEC_REQUEST_CERT_MAPPING.put("AppCertCode[申请单证代码]", "APP_CERT_CODE");
            DEC_REQUEST_CERT_MAPPING.put("ApplOri[申请单证正本数]", "APPL_ORI");
            DEC_REQUEST_CERT_MAPPING.put("ApplCopyQuan[申请单证副本数]", "APPL_COPY_QUAN");
        }

        /**
         * 企业资质
         * */
        protected static final Map<String, String> DEC_COP_LIMIT_MAPPING = new LinkedHashMap<>();

        static {
            DEC_COP_LIMIT_MAPPING.put("EntQualifNo[企业资质编号]", "ENT_QUALIF_NO");
            DEC_COP_LIMIT_MAPPING.put("EntQualifTypeCode[企业资质类别代码]", "ENT_QUALIF_TYPE_CODE");
        }

        /**
         * 企业承诺
         * */
        protected static final Map<String, String> DEC_COP_PROMISE_MAPPING = new LinkedHashMap<>();

        static {
            DEC_COP_PROMISE_MAPPING.put("DeclaratioMaterialCode[证明/声明材料代码]", "DECLARATION_MATERIAL_CODE");
        }

        /**
         * 其他包装
         * */
        protected static final Map<String, String> DEC_OTHER_PACK_MAPPING = new LinkedHashMap<>();

        static {
            DEC_OTHER_PACK_MAPPING.put("PackQty[包装件数]", "PACK_QTY");
            DEC_OTHER_PACK_MAPPING.put("PackType[包装材料种类]", "PACK_TYPE");
        }

        /**
         * 产品资质
         * */
        private static final Map<String, String> DEC_GOODS_LIMIT_MAPPING = new LinkedHashMap<>();

        static {
            DEC_GOODS_LIMIT_MAPPING.put("GoodsNo[商品序号]", "GOODS_NO");
            DEC_GOODS_LIMIT_MAPPING.put("LicTypeCode[许可证类别代码]", "LIC_TYPE_CODE");
            DEC_GOODS_LIMIT_MAPPING.put("LicenceNo[许可证编号]", "LICENCE_NO");
            DEC_GOODS_LIMIT_MAPPING.put("LicWrtofDetailNo[许可证核销明细序号]", "LIC_WRTOF_DETAIL_NO");
            DEC_GOODS_LIMIT_MAPPING.put("LicWrtofQty[许可证核销数量]", "LIC_WRTOF_QTY");
            DEC_GOODS_LIMIT_MAPPING.put("LicWrtofQtyUnit[许可证核销数量单位]", "LIC_WRTOF_QTY_UNIT");
        }

        /**
         * 产品资质VIN
         * */
        private static final Map<String, String> DEC_GOODS_LIMIT_VIN_MAPPING = new LinkedHashMap<>();

        static {
            DEC_GOODS_LIMIT_VIN_MAPPING.put("LicenceNo[许可证编号]", "LICENCE_NO");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("LicTypeCode[许可证类别代码]", "LICTYPE_CODE");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("VinNo[VIN序号]", "VIN_NO");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("BillLadDate[提/运单日期]", "BILL_LAD_DATE");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("QualityQgp[质量保质期]", "QUALITY_QGP");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("MotorNo[发动机号或电机号]", "MOTOR_NO");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("VinCode[车辆识别代码（VIN）]", "VIN_CODE");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("ChassisNo[底盘(车架)号]", "CHASSIS_NO");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("InvoiceNum[发票所列数量]", "INVOICE_NUM");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("ProdCnnm[品名（中文名称）]", "PROD_CNNM");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("ProdEnnm[品名（英文名称）]", "PROD_ENNM");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("ModelEn[型号（英文）]", "MODEL_EN");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("PricePerUnit[单价]", "PRICE_PER_UNIT");
            DEC_GOODS_LIMIT_VIN_MAPPING.put("InvoiceNo[发票号]", "INVOICE_NO");
        }

        /**
         * 特许权使用费
         * */
        private static final Map<String, String> DEC_ROYALTY_FEE_MAPPING = new LinkedHashMap<>();

        static {
            DEC_ROYALTY_FEE_MAPPING.put("PricePreDeterminNo[价格预裁定编号]", "PRICE_PRE_DETERMIN_NO");
            DEC_ROYALTY_FEE_MAPPING.put("TaxRoyaltyDeclType[应税特许权使用费申报情形]", "TAX_ROYALTY_DECL_TYPE");
            DEC_ROYALTY_FEE_MAPPING.put("ContractNo[合同/协议号]", "CONTRACT_NO");
            DEC_ROYALTY_FEE_MAPPING.put("Authorizer[授权方]", "AUTHORIZER");
            DEC_ROYALTY_FEE_MAPPING.put("AuthorizedPerson[被授权方]", "AUTHORIZED_PERSON");
            DEC_ROYALTY_FEE_MAPPING.put("PayType[支付方式]", "PAY_TYPE");
            DEC_ROYALTY_FEE_MAPPING.put("PayTime[支付时间]", "PAY_TIME");
            DEC_ROYALTY_FEE_MAPPING.put("PayPeriod[支付计提周期]", "PAY_PERIOD");
            DEC_ROYALTY_FEE_MAPPING.put("EffectiveDateTime[合同/协议起始执行时间]", "EFFECTIVE_DATE_TIME");
            DEC_ROYALTY_FEE_MAPPING.put("ExpirationDateTime[合同协议终止时间]", "EXPIRATION_DATE_TIME");
            DEC_ROYALTY_FEE_MAPPING.put("RoyaltyAmount[特许权使用费金额]", "ROYALTY_AMOUNT");
            DEC_ROYALTY_FEE_MAPPING.put("Curr[币制]", "CURR");
            DEC_ROYALTY_FEE_MAPPING.put("RoyaltyFeeType[特许权使用费类型]", "ROYALTY_FEE_TYPE");
            DEC_ROYALTY_FEE_MAPPING.put("EdocType[随附材料清单类型]", "EDOC_TYPE");
            DEC_ROYALTY_FEE_MAPPING.put("Statment[说明]", "STATMENT");
            DEC_ROYALTY_FEE_MAPPING.put("IsSecret[是否保密]", "IS_SECRET");
            DEC_ROYALTY_FEE_MAPPING.put("IsCusAudit[是否经过海关审核认定]", "IS_CUS_AUDIT");
            DEC_ROYALTY_FEE_MAPPING.put("IsCusPricePreDetermin[是否经过海关价格预裁定]", "IS_CUS_PRICE_PRE_DETERMIN");
            DEC_ROYALTY_FEE_MAPPING.put("IssueDateTime[合同/协议签约时间]", "ISSUE_DATE_TIME");
            DEC_ROYALTY_FEE_MAPPING.put("PeriodStartDate[本次支付对应的计提周期起始时间]", "PERIOD_START_DATE");
            DEC_ROYALTY_FEE_MAPPING.put("PeriodEndDate[本次支付对应的计提周期终止时间]", "PERIOD_END_DATE");
        }

    }

}