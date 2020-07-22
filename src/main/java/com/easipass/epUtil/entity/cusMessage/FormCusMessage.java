package com.easipass.epUtil.entity.cusMessage;

import com.easipass.epUtil.api.websocket.BaseWebsocketApi;
import com.easipass.epUtil.entity.AbstractCusMessage;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.entity.VO.CusMessageComparisonVO;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.exception.CusFileException;
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
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 报关单报文
 *
 * @author ZJ
 * */
public final class FormCusMessage extends AbstractCusMessage {

    /**
     * 报文集合
     * */
    private static final Map<String, FormCusMessage> FORM_CUS_MESSAGE_MAP = new LinkedHashMap<>();

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
     * 创建时间
     * */
    private final Date createTime = new Date();

    static {
        // 当报文创建时间超过1天，则删除
        new Thread(() -> {
            Log log = Log.getLog();

            log.info("开启报文删除服务");
            while (true) {
                Set<String> keys = FORM_CUS_MESSAGE_MAP.keySet();
                long nowTime = new Date().getTime();
                long outTime = 24 * 60 * 60 * 1000;
//                long outTime = 10 * 1000;
                List<String> newKeys;
                try {
                    newKeys = new CopyOnWriteArrayList<>(keys);
                } catch (java.util.ConcurrentModificationException e) {
                    log.error("报文集合被修改，重新遍历");
                    continue;
                }

                for (String key : newKeys) {
                    FormCusMessage cusMessage = FORM_CUS_MESSAGE_MAP.get(key);

                    if (nowTime > cusMessage.createTime.getTime() + outTime) {
                        FORM_CUS_MESSAGE_MAP.remove(key);
                        log.info("删除报关单报文: " + key + "; size: " + FORM_CUS_MESSAGE_MAP.size());
                    }
                }
            }
        }).start();
    }

    /**
     * 构造函数
     *
     * @param multipartFile 前端传过来的文件
     * */
    private FormCusMessage(MultipartFile multipartFile) {
        Element rootElement;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            rootElement = XmlUtil.getDocument_v2(inputStream).getRootElement();
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw new CusFileException("不是正确的报关单报文");
        }

        this.decHead = rootElement.element("DecHead");
        if (this.decHead == null) {
            throw new CusFileException("不是正确的报关单报文");
        }

        Element DecSign = rootElement.element("DecSign");
        if (DecSign == null) {
            throw new CusFileException("不是正确的报关单报文");
        }
        Element ClientSeqNo = DecSign.element("ClientSeqNo");
        if (ClientSeqNo == null) {
            throw new CusFileException("不是正确的报关单报文");
        }
        String ediNo = ClientSeqNo.getText();
        if ("".equals(ediNo)) {
            throw new CusFileException("不是正确的报关单报文");
        }
        this.ediNo = ediNo;

        Element DecLists = rootElement.element("DecLists");
        if (DecLists == null) {
            throw new CusFileException("不是正确的报关单报文");
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
    }

    @Override
    public void comparison(BaseWebsocketApi baseWebsocketApi) {
        // 数据库
        SWGDOracle SWGDOracle = new SWGDOracle();

        try {
            // 检查数据库连接
            SWGDOracle.connect();

            // 比对表头
            ResultSet dbFormHead = SWGDOracle.queryFormHead(this.ediNo);
            Set<String> formHeadKeys = FormCusMessageNodeMapping.FORM_HEAD_MAPPING.keySet();

            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[表头]"));
            for (String key : formHeadKeys) {
                MapKeyValue mapKeyValue = getKeyValue(this.decHead, FormCusMessageNodeMapping.FORM_HEAD_MAPPING, key, dbFormHead, "表头", baseWebsocketApi);

                if (mapKeyValue != null) {
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

                        for (String value : values) {
                            String s = getDbValue(dbFormHead, value);
                            newDbValue = StringUtil.append(newDbValue, s);
                        }
                        mapKeyValue.setDbValue(newDbValue);
                    }
                } else {
                    break;
                }

                comparison(mapKeyValue, baseWebsocketApi);
            }

            // 比对表体数据
            int size = this.DecList.size();
            Set<String> ListKeys = FormCusMessageNodeMapping.FORM_LIST_MAPPING.keySet();

            for (int i = 0; i < size; i++) {
                Element element = this.DecList.get(i);
                ResultSet resultSet = SWGDOracle.queryFormList(ediNo, i + "");
                // codeTs
                String codeTs = "";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[表体 - " + (i + 1) + "]"));
                for (String key : ListKeys) {
                    MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.FORM_LIST_MAPPING, key, resultSet, "表体", baseWebsocketApi);

                    if (mapKeyValue != null) {
                        String key1 = mapKeyValue.getKey1();
                        String dbValue = mapKeyValue.getDbValue();
                        String value = mapKeyValue.getValue();

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
                            codeTs = StringUtil.append(codeTs, dbValue);
                            mapKeyValue.setDbValue(codeTs);
                        }
                    } else {
                        break;
                    }

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }

            // 比对集装箱信息
            int containerSize = this.DecContainers.size();

            if (containerSize != 0) {
                Set<String> containersKeys = FormCusMessageNodeMapping.FORM_CONTAINER_MAPPING.keySet();

                for (int i = 0; i < containerSize; i ++) {
                    Element element = this.DecContainers.get(i);
                    ResultSet resultSet = SWGDOracle.queryFormContainer(ediNo, i + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[集装箱 - " + (i + 1) + "]"));
                    for (String key : containersKeys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.FORM_CONTAINER_MAPPING, key, resultSet, "集装箱", baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无集装箱]"));
            }

            // 比对随附单证
            int licenseDocuSize = this.LicenseDocu.size();

            if (licenseDocuSize != 0) {
                Set<String> certificateKeys = FormCusMessageNodeMapping.FORM_CERTIFICATE_MAPPING.keySet();

                for (int i = 0; i < licenseDocuSize; i++) {
                    Element element = this.LicenseDocu.get(i);
                    ResultSet resultSet = SWGDOracle.queryFormCertificate(ediNo, i + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[随附单证 - " + (i + 1) + "]"));
                    for (String key : certificateKeys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.FORM_CERTIFICATE_MAPPING, key, resultSet, "随附单证", baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无随附单证]"));
            }

            // 比对申请单证
            String decRequestCertMessage = "申请单证";
            int DecRequestCertSize = this.DecRequestCert.size();

            if (DecRequestCertSize != 0) {
                Set<String> keys = FormCusMessageNodeMapping.DEC_REQUEST_CERT_MAPPING.keySet();

                for (int i = 0; i < DecRequestCertSize; i++) {
                    Element element = this.DecRequestCert.get(i);
                    ResultSet resultSet = SWGDOracle.queryDecRequestCert(ediNo, (i + 1) + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[" + decRequestCertMessage + " - " + (i + 1) + "]"));
                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.DEC_REQUEST_CERT_MAPPING, key, resultSet, decRequestCertMessage, baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无" + decRequestCertMessage + "]"));
            }

            // 比对企业资质
            String decCopLimitMessage = "企业资质";
            int decCopLimitSize = this.DecCopLimit.size();

            if (decCopLimitSize != 0) {
                Set<String> keys = FormCusMessageNodeMapping.DEC_COP_LIMIT_MAPPING.keySet();

                for (int i = 0; i < decCopLimitSize; i++) {
                    Element element = this.DecCopLimit.get(i);
                    ResultSet resultSet = SWGDOracle.queryDecCopLimit(ediNo, (i + 1) + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[" + decCopLimitMessage + " - " + (i + 1) + "]"));
                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.DEC_COP_LIMIT_MAPPING, key, resultSet, decRequestCertMessage, baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无" + decCopLimitMessage + "]"));
            }

            // 比对企业承诺
            String decCopPromisesMessage = "企业承诺";
            int decCopPromisesSize = this.DecCopPromise.size();

            if (decCopPromisesSize != 0) {
                Set<String> keys = FormCusMessageNodeMapping.DEC_COP_PROMISE_MAPPING.keySet();

                for (int i = 0; i < decCopPromisesSize; i++) {
                    Element element = this.DecCopPromise.get(i);
                    ResultSet resultSet = SWGDOracle.queryDecCopPromise(ediNo, (i + 1) + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[" + decCopPromisesMessage + " - " + (i + 1) + "]"));
                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.DEC_COP_PROMISE_MAPPING, key, resultSet, decRequestCertMessage, baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无" + decCopPromisesMessage + "]"));
            }

            // 使用人信息暂时不进行比对
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[使用人信息暂时不进行比对]"));

            // 标记号码附件暂时不进行比对
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[标记号码附件暂时不进行比对]"));

            // 比对其他包装
            String decOtherPackMessage = "其他包装";
            int decOtherPackSize = this.DecOtherPack.size();

            if (decOtherPackSize != 0) {
                Set<String> keys = FormCusMessageNodeMapping.DEC_OTHER_PACK_MAPPING.keySet();

                for (int i = 0; i < decOtherPackSize; i++) {
                    Element element = this.DecOtherPack.get(i);
                    ResultSet resultSet = SWGDOracle.queryDecOtherPack(ediNo, (i + 1) + "");

                    baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[" + decOtherPackMessage + " - " + (i + 1) + "]"));
                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(element, FormCusMessageNodeMapping.DEC_OTHER_PACK_MAPPING, key, resultSet, decOtherPackMessage, baseWebsocketApi);

                        if (mapKeyValue == null) {
                            break;
                        }

                        comparison(mapKeyValue, baseWebsocketApi);
                    }
                }
            } else {
                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[无" + decOtherPackMessage + "]"));
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
     * 添加报文
     *
     * @param multipartFile 前端传过来的文件
     *
     * @return 唯一标识
     * */
    public static String addFormCusMessage(MultipartFile multipartFile) {
        String id = new Date().getTime() + "";

        if (getFormCusMessage(id) != null) {
            id = id + "1";
        }

        FORM_CUS_MESSAGE_MAP.put(id, new FormCusMessage(multipartFile));

        return id;
    }

    /**
     * 获取报文
     *
     * @return 通过id获取报文
     * */
    public static FormCusMessage getFormCusMessage(String id) {
        Set<String> keys = FORM_CUS_MESSAGE_MAP.keySet();

        for (String key : keys) {
            if (key.equals(id)) {
                return FORM_CUS_MESSAGE_MAP.get(id);
            }
        }

        return null;
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

    }

}