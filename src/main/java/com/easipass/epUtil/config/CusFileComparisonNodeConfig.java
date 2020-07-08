package com.easipass.epUtil.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 报文比对节点配置
 *
 * @author ZJ
 * */
public final class CusFileComparisonNodeConfig {

    /**
     * 表头
     *
     * key: 报文节点名
     * value: 数据库节点名
     * */
    public static final Map<String, String> FORM_HEAD = new LinkedHashMap<>();

    static {
        FORM_HEAD.put("SeqNo", "SEQ_NO");
        FORM_HEAD.put("AgentCode", "AGENT_CODE");
        FORM_HEAD.put("AgentName", "AGENT_NAME");
        FORM_HEAD.put("ApprNo", "APPR_NO");
        FORM_HEAD.put("BillNo", "BILL_NO");
        FORM_HEAD.put("ContrNo", "CONTR_NO");
        FORM_HEAD.put("CustomMaster", "DECL_PORT");
        FORM_HEAD.put("CutMode", "CUT_MODE");
        FORM_HEAD.put("DistinatePort", "DISTINATE_PORT_STD");
        FORM_HEAD.put("FeeCurr", "FEE_CURR");
        FORM_HEAD.put("FeeMark", "FEE_MARK");
        FORM_HEAD.put("FeeRate", "FEE_RATE");
        FORM_HEAD.put("GrossWet", "GROSS_WT");
        FORM_HEAD.put("IEDate", "I_E_DATE");
        FORM_HEAD.put("IEPort", "I_E_PORT");
        FORM_HEAD.put("InsurCurr", "INSUR_CURR");
        FORM_HEAD.put("InsurMark", "INSUR_MARK");
        FORM_HEAD.put("InsurRate", "INSUR_RATE");
        FORM_HEAD.put("LicenseNo", "LICENSE_NO");
        FORM_HEAD.put("ManualNo", "MANUAL_NO");
        FORM_HEAD.put("NetWt", "NET_WT");
//        FORM_HEAD.put("NoteS", "NOTE_S");
        FORM_HEAD.put("OtherCurr", "OTHER_CURR");
        FORM_HEAD.put("OtherMark", "OTHER_MARK");
        FORM_HEAD.put("OtherRate", "OTHER_RATE");
        FORM_HEAD.put("OwnerCode", "OWNER_CODE");
        FORM_HEAD.put("OwnerName", "OWNER_NAME");
        FORM_HEAD.put("PackNo", "PACK_NO");
        FORM_HEAD.put("TradeCode", "TRADE_CO");
        FORM_HEAD.put("TradeCountry", "TRADE_COUNTRY_STD");
        FORM_HEAD.put("TradeMode", "TRADE_MODE_STD");
        FORM_HEAD.put("TradeName", "TRADE_NAME");
        FORM_HEAD.put("TrafMode", "TRAF_MODE_STD");
        FORM_HEAD.put("TrafName", "TRAF_NAME");
        FORM_HEAD.put("TransMode", "TRANS_MODE");
        FORM_HEAD.put("WrapType", "WRAP_TYPE_STD");
        FORM_HEAD.put("TypistNo", "I_C_CODE");
        FORM_HEAD.put("BillType", "BILL_TYPE");
        FORM_HEAD.put("PromiseItmes", "PROMISE_ITMES");
        FORM_HEAD.put("TradeAreaCode", "TRADE_AREA_CODE_STD");
        FORM_HEAD.put("CheckFlow", "CHECK_FLOW");
        FORM_HEAD.put("TaxAaminMark", "TAX_AAMIN_MARK");
        FORM_HEAD.put("MarkNo", "MARK_NO");
        FORM_HEAD.put("DespPortCode", "DESP_PORT_CODE");
        FORM_HEAD.put("EntyPortCode", "ENTY_PORT_CODE");
        FORM_HEAD.put("GoodsPlace", "GOODS_PLACE");
        FORM_HEAD.put("BLNo", "B_L_NO");
        FORM_HEAD.put("InspOrgCode", "INSP_ORG_CODE");
        FORM_HEAD.put("SpecDeclFlag", "SPEC_DECL_FLAG");
        FORM_HEAD.put("PurpOrgCode", "PURP_ORG_CODE");
        FORM_HEAD.put("DespDate", "DESP_DATE");
        FORM_HEAD.put("CmplDschrgDt", "CMPL_DSCHRG_DT");
        FORM_HEAD.put("CorrelationReasonFlag", "CORRELATION_REASON_FLAG");
        FORM_HEAD.put("VsaOrgCode", "VSA_ORG_CODE");
        FORM_HEAD.put("OrigBoxFlag", "ORIG_BOX_FLAG");
        FORM_HEAD.put("DeclareName", "DECLARE_NAME");
        FORM_HEAD.put("NoOtherPack", "NO_OTHER_PACK");
        FORM_HEAD.put("OrgCode", "ORG_CODE");
        FORM_HEAD.put("OverseasConsignorCode", "OVERSEAS_CONSIGNOR_CODE");
        FORM_HEAD.put("OverseasConsignorCname", "OVERSEAS_CONSIGNOR_CNAME");
        FORM_HEAD.put("OverseasConsignorEname", "OVERSEAS_CONSIGNOR_ENAME");
        FORM_HEAD.put("OverseasConsignorAddr", "OVERSEAS_CONSIGNOR_ADDR");
        FORM_HEAD.put("OverseasConsigneeCode", "OVERSEAS_CONSIGNEE_CODE");
        FORM_HEAD.put("OverseasConsigneeEname", "OVERSEAS_CONSIGNEE_ENAME");
        FORM_HEAD.put("DomesticConsigneeEname", "DOMESTIC_CONSIGNEE_ENAME");
        FORM_HEAD.put("CorrelationNo", "CORRELATION_NO");
        FORM_HEAD.put("EdiRemark2", "EDI_REMARK_2");
        FORM_HEAD.put("TradeCoScc", "TRADE_CO_SCC");
        FORM_HEAD.put("AgentCodeScc", "AGENT_CODE_SCC");
        FORM_HEAD.put("OwnerCodeScc", "OWNER_CODE_SCC");
        FORM_HEAD.put("TradeCiqCode", "TRADE_CIQ_CODE");
        FORM_HEAD.put("OwnerCiqCode", "OWNER_CIQ_CODE");
        FORM_HEAD.put("DeclCiqCode", "DECL_CIQ_CODE");
    }

}