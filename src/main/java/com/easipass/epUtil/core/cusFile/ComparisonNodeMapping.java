package com.easipass.epUtil.core.cusFile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 比对节点映射
 * key: 报关单节点[节点中文名]
 * value: 数据库节点映射
 * 如果value为null, 不进行比对
 *
 * @author ZJ
 * */
public class ComparisonNodeMapping {

    /**
     * 表头节点映射
     * */
    public static final Map<String, String> FORM_HEAD_MAPPING = new LinkedHashMap<>();

    static {
        FORM_HEAD_MAPPING.put("SeqNo[数据中心统一编号]", "SEQ_NO");
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
     * 获取key
     *
     * @param key key
     * */
    public static String getKey(String key) {
        // [索引
        int index = key.indexOf("[");
        // 返回的key
        String result = key;

        if (index != -1) {
            result = result.substring(0, index);
        }

        return result;
    }

}