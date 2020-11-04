package com.easipass.util.core.config;

import com.easipass.util.core.entity.ParamDbTableMapping;
import java.util.*;

/**
 * 参数库表映射配置
 *
 * @author ZJ
 * */
public final class ParamDbTableMappingConfig {

    /**
     * 映射
     * */
    public static final List<ParamDbTableMapping> MAPPINGS = new ArrayList<>();

    static {
        LinkedHashMap<String, String> CAR_HS = new LinkedHashMap<>();
        CAR_HS.put("HS", "HS");
        CAR_HS.put("TYPE", "TYPE");
        MAPPINGS.add(new ParamDbTableMapping("CAR_HS", "CAR_HS", CAR_HS));

        LinkedHashMap<String, String> CAR_OTHER = new LinkedHashMap<>();
        CAR_OTHER.put("TYPE", "TYPE");
        CAR_OTHER.put("CAR_CODE", "CAR_CODE");
        MAPPINGS.add(new ParamDbTableMapping("CAR_OTHER", "CAR_OTHER", CAR_OTHER));

        LinkedHashMap<String, String> CHARACTERISTIC_CODE = new LinkedHashMap<>();
        CHARACTERISTIC_CODE.put("CODE", "CODE");
        CHARACTERISTIC_CODE.put("CNAME", "CNAME");
        CHARACTERISTIC_CODE.put("TYPE_CODE", "TYPE_CODE");
        CHARACTERISTIC_CODE.put("TYPE_CNAME", "TYPE_CNAME");
        CHARACTERISTIC_CODE.put("TEU", "TEU");
        MAPPINGS.add(new ParamDbTableMapping("CHARACTERISTIC_CODE", "CHARACTERISTICCODE", CHARACTERISTIC_CODE));

        LinkedHashMap<String, String> EPCOMPARE_WD_SWGD = new LinkedHashMap<>();
        EPCOMPARE_WD_SWGD.put("FIELD_ID", "FieldId");
        EPCOMPARE_WD_SWGD.put("QP_FIELD_CODE", "QP_FieldCode");
        EPCOMPARE_WD_SWGD.put("QP_FIELD_NAME", "QP_FieldName");
        EPCOMPARE_WD_SWGD.put("EP_TABLE_CODE", "EP_TableCode");
        EPCOMPARE_WD_SWGD.put("EP_TABLE_NAME", "EP_TableName");
        EPCOMPARE_WD_SWGD.put("EP_FIELD_CODE", "EP_FieldCode");
        EPCOMPARE_WD_SWGD.put("MEMO", "Memo");
        MAPPINGS.add(new ParamDbTableMapping("EPCOMPARE_WD_SWGD", "EPCompare_wd_swgd", EPCOMPARE_WD_SWGD));

        LinkedHashMap<String, String> AREA_PRE = new LinkedHashMap<>();
        AREA_PRE.put("TRADE_MODE", "TRADE_MODE");
        AREA_PRE.put("DOCU_CODE", "DOCU_CODE");
        AREA_PRE.put("DISTRICT_T", "DISTRICT_T");
        AREA_PRE.put("USE_TO", "USE_TO");
        AREA_PRE.put("CODE_FLAG", "CODE_FLAG");
        AREA_PRE.put("GOODS_T1", "GOODS_T1");
        AREA_PRE.put("GOODS_T2", "GOODS_T2");
        AREA_PRE.put("GOODS_S1", "GOODS_S1");
        AREA_PRE.put("GOODS_S2", "GOODS_S2");
        AREA_PRE.put("DOCU_CO_MO", "DOCU_CO_MO");
        AREA_PRE.put("NOTE_S", "NOTE_S");
        AREA_PRE.put("TRADE_COUN", "TRADE_COUN");
        AREA_PRE.put("ORIGIN_COU", "ORIGIN_COU");
        MAPPINGS.add(new ParamDbTableMapping("AREA_PRE", "AREA_PRE", AREA_PRE));

        LinkedHashMap<String, String> CHINA_REGIONALISM = new LinkedHashMap<>();
        CHINA_REGIONALISM.put("ITEMCODE", "ITEMCODE");
        CHINA_REGIONALISM.put("INDEXID", "INDEXID");
        CHINA_REGIONALISM.put("CNAME", "CNAME");
        CHINA_REGIONALISM.put("ENAME", "ENAME");
        CHINA_REGIONALISM.put("CLASSIFY_CODE", "CLASSIFY_CODE");
        CHINA_REGIONALISM.put("FLAG", "FLAG");
        CHINA_REGIONALISM.put("LASTUPDATE", "LASTUPDATE");
        CHINA_REGIONALISM.put("VERSION", "VERSION");
        CHINA_REGIONALISM.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        CHINA_REGIONALISM.put("ECIQ_UUID", "ECIQ_UUID");
        CHINA_REGIONALISM.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("CHINA_REGIONALISM", "ChinaRegionalism", CHINA_REGIONALISM));

        LinkedHashMap<String, String> CIQ_CODE = new LinkedHashMap<>();
        CIQ_CODE.put("ECIQ_UUID", "ECIQ_UUID");
        CIQ_CODE.put("HS_CODE", "HS_CODE");
        CIQ_CODE.put("INDEXID", "INDEXID");
        CIQ_CODE.put("HS_CNAME", "HS_CNAME");
        CIQ_CODE.put("EXPAND_NO", "EXPAND_NO");
        CIQ_CODE.put("GOODS_NAME", "GOODS_NAME");
        CIQ_CODE.put("CLASSIFY_CODE", "CLASSIFY_CODE");
        CIQ_CODE.put("CLASSIFY_CNNM", "CLASSIFY_CNNM");
        CIQ_CODE.put("EXPORT_GOODS_CLASS", "EXPORT_GOODS_CLASS");
        CIQ_CODE.put("IMPORT_GOODS_CLASS", "IMPORT_GOODS_CLASS");
        CIQ_CODE.put("FLAG", "FLAG");
        CIQ_CODE.put("LASTUPDATE", "LASTUPDATE");
        CIQ_CODE.put("VERSION", "VERSION");
        CIQ_CODE.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        CIQ_CODE.put("CIQ_CODE", "CIQ_CODE");
        CIQ_CODE.put("SENIOR_CLASSIFY_CODE", "SENIOR_CLASSIFY_CODE");
        CIQ_CODE.put("POST_GOODS_FLAG", "POST_GOODS_FLAG");
        CIQ_CODE.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("CIQ_CODE", "CiqCode", CIQ_CODE));

        LinkedHashMap<String, String> CIQ_ORG = new LinkedHashMap<>();
        CIQ_ORG.put("CODE", "CODE");
        CIQ_ORG.put("FULL_NAME", "FULL_NAME");
        CIQ_ORG.put("ABBR_NAME", "ABBR_NAME");
        MAPPINGS.add(new ParamDbTableMapping("CIQ_ORG", "CiqOrg", CIQ_ORG));

        LinkedHashMap<String, String> CLASSIFY = new LinkedHashMap<>();
        CLASSIFY.put("G_NAME", "G_NAME");
        CLASSIFY.put("KEY_NAME", "KEY_NAME");
        CLASSIFY.put("CLASS_SPEC", "CLASS_SPEC");
        CLASSIFY.put("CODE_T", "CODE_T");
        CLASSIFY.put("CODE_S", "CODE_S");
        CLASSIFY.put("CLASS_NO", "CLASS_NO");
        MAPPINGS.add(new ParamDbTableMapping("CLASSIFY", "classify", CLASSIFY));

        LinkedHashMap<String, String> CLASSIFY_NEW_EN = new LinkedHashMap<>();
        CLASSIFY_NEW_EN.put("HSCODE", "商品编码");
        CLASSIFY_NEW_EN.put("HSNAME", "商品名称");
        CLASSIFY_NEW_EN.put("REMARK", "备注");
        CLASSIFY_NEW_EN.put("ELEMENT1", "要素1");
        CLASSIFY_NEW_EN.put("SIGN1", "标志1");
        CLASSIFY_NEW_EN.put("CHOICE1", "可选1");
        CLASSIFY_NEW_EN.put("CHOICESIGN1", "可选标志1");
        CLASSIFY_NEW_EN.put("EXPLAIN1", "说明1");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN1", "说明标志1");
        CLASSIFY_NEW_EN.put("ELEMENT2", "要素2");
        CLASSIFY_NEW_EN.put("SIGN2", "标志2");
        CLASSIFY_NEW_EN.put("CHOICE2", "可选2");
        CLASSIFY_NEW_EN.put("CHOICESIGN2", "可选标志2");
        CLASSIFY_NEW_EN.put("EXPLAIN2", "说明2");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN2", "说明标志2");
        CLASSIFY_NEW_EN.put("ELEMENT3", "要素3");
        CLASSIFY_NEW_EN.put("SIGN3", "标志3");
        CLASSIFY_NEW_EN.put("CHOICE3", "可选3");
        CLASSIFY_NEW_EN.put("CHOICESIGN3", "可选标志3");
        CLASSIFY_NEW_EN.put("EXPLAIN3", "说明3");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN3", "说明标志3");
        CLASSIFY_NEW_EN.put("ELEMENT4", "要素4");
        CLASSIFY_NEW_EN.put("SIGN4", "标志4");
        CLASSIFY_NEW_EN.put("CHOICE4", "可选4");
        CLASSIFY_NEW_EN.put("CHOICESIGN4", "可选标志4");
        CLASSIFY_NEW_EN.put("EXPLAIN4", "说明4");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN4", "说明标志4");
        CLASSIFY_NEW_EN.put("ELEMENT5", "要素5");
        CLASSIFY_NEW_EN.put("SIGN5", "标志5");
        CLASSIFY_NEW_EN.put("CHOICE5", "可选5");
        CLASSIFY_NEW_EN.put("CHOICESIGN5", "可选标志5");
        CLASSIFY_NEW_EN.put("EXPLAIN5", "说明5");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN5", "说明标志5");
        CLASSIFY_NEW_EN.put("ELEMENT6", "要素6");
        CLASSIFY_NEW_EN.put("SIGN6", "标志6");
        CLASSIFY_NEW_EN.put("CHOICE6", "可选6");
        CLASSIFY_NEW_EN.put("CHOICESIGN6", "可选标志6");
        CLASSIFY_NEW_EN.put("EXPLAIN6", "说明6");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN6", "说明标志6");
        CLASSIFY_NEW_EN.put("ELEMENT7", "要素7");
        CLASSIFY_NEW_EN.put("SIGN7", "标志7");
        CLASSIFY_NEW_EN.put("CHOICE7", "可选7");
        CLASSIFY_NEW_EN.put("CHOICESIGN7", "可选标志7");
        CLASSIFY_NEW_EN.put("EXPLAIN7", "说明7");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN7", "说明标志7");
        CLASSIFY_NEW_EN.put("ELEMENT8", "要素8");
        CLASSIFY_NEW_EN.put("SIGN8", "标志8");
        CLASSIFY_NEW_EN.put("CHOICE8", "可选8");
        CLASSIFY_NEW_EN.put("CHOICESIGN8", "可选标志8");
        CLASSIFY_NEW_EN.put("EXPLAIN8", "说明8");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN8", "说明标志8");
        CLASSIFY_NEW_EN.put("ELEMENT9", "要素9");
        CLASSIFY_NEW_EN.put("SIGN9", "标志9");
        CLASSIFY_NEW_EN.put("CHOICE9", "可选9");
        CLASSIFY_NEW_EN.put("CHOICESIGN9", "可选标志9");
        CLASSIFY_NEW_EN.put("EXPLAIN9", "说明9");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN9", "说明标志9");
        CLASSIFY_NEW_EN.put("ELEMENT10", "要素10");
        CLASSIFY_NEW_EN.put("SIGN10", "标志10");
        CLASSIFY_NEW_EN.put("CHOICE10", "可选10");
        CLASSIFY_NEW_EN.put("CHOICESIGN10", "可选标志10");
        CLASSIFY_NEW_EN.put("EXPLAIN10", "说明10");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN10", "说明标志10");
        CLASSIFY_NEW_EN.put("ELEMENT11", "要素11");
        CLASSIFY_NEW_EN.put("SIGN11", "标志11");
        CLASSIFY_NEW_EN.put("CHOICE11", "可选11");
        CLASSIFY_NEW_EN.put("CHOICESIGN11", "可选标志11");
        CLASSIFY_NEW_EN.put("EXPLAIN11", "说明11");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN11", "说明标志11");
        CLASSIFY_NEW_EN.put("ELEMENT12", "要素12");
        CLASSIFY_NEW_EN.put("SIGN12", "标志12");
        CLASSIFY_NEW_EN.put("CHOICE12", "可选12");
        CLASSIFY_NEW_EN.put("CHOICESIGN12", "可选标志12");
        CLASSIFY_NEW_EN.put("EXPLAIN12", "说明12");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN12", "说明标志12");
        CLASSIFY_NEW_EN.put("ELEMENT13", "要素13");
        CLASSIFY_NEW_EN.put("SIGN13", "标志13");
        CLASSIFY_NEW_EN.put("CHOICE13", "可选13");
        CLASSIFY_NEW_EN.put("CHOICESIGN13", "可选标志13");
        CLASSIFY_NEW_EN.put("EXPLAIN13", "说明13");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN13", "说明标志13");
        CLASSIFY_NEW_EN.put("ELEMENT14", "要素14");
        CLASSIFY_NEW_EN.put("SIGN14", "标志14");
        CLASSIFY_NEW_EN.put("CHOICE14", "可选14");
        CLASSIFY_NEW_EN.put("CHOICESIGN14", "可选标志14");
        CLASSIFY_NEW_EN.put("EXPLAIN14", "说明14");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN14", "说明标志14");
        CLASSIFY_NEW_EN.put("ELEMENT15", "要素15");
        CLASSIFY_NEW_EN.put("SIGN15", "标志15");
        CLASSIFY_NEW_EN.put("CHOICE15", "可选15");
        CLASSIFY_NEW_EN.put("CHOICESIGN15", "可选标志15");
        CLASSIFY_NEW_EN.put("EXPLAIN15", "说明15");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN15", "说明标志15");
        CLASSIFY_NEW_EN.put("ELEMENT16", "要素16");
        CLASSIFY_NEW_EN.put("SIGN16", "标志16");
        CLASSIFY_NEW_EN.put("CHOICE16", "可选16");
        CLASSIFY_NEW_EN.put("CHOICESIGN16", "可选标志16");
        CLASSIFY_NEW_EN.put("EXPLAIN16", "说明16");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN16", "说明标志16");
        CLASSIFY_NEW_EN.put("ELEMENT17", "要素17");
        CLASSIFY_NEW_EN.put("SIGN17", "标志17");
        CLASSIFY_NEW_EN.put("CHOICE17", "可选17");
        CLASSIFY_NEW_EN.put("CHOICESIGN17", "可选标志17");
        CLASSIFY_NEW_EN.put("EXPLAIN17", "说明17");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN17", "说明标志17");
        CLASSIFY_NEW_EN.put("ELEMENT18", "要素18");
        CLASSIFY_NEW_EN.put("SIGN18", "标志18");
        CLASSIFY_NEW_EN.put("CHOICE18", "可选18");
        CLASSIFY_NEW_EN.put("CHOICESIGN18", "可选标志18");
        CLASSIFY_NEW_EN.put("EXPLAIN18", "说明18");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN18", "说明标志18");
        CLASSIFY_NEW_EN.put("ELEMENT19", "要素19");
        CLASSIFY_NEW_EN.put("SIGN19", "标志19");
        CLASSIFY_NEW_EN.put("CHOICE19", "可选19");
        CLASSIFY_NEW_EN.put("CHOICESIGN19", "可选标志19");
        CLASSIFY_NEW_EN.put("EXPLAIN19", "说明19");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN19", "说明标志19");
        CLASSIFY_NEW_EN.put("ELEMENT20", "要素20");
        CLASSIFY_NEW_EN.put("SIGN20", "标志20");
        CLASSIFY_NEW_EN.put("CHOICE20", "可选20");
        CLASSIFY_NEW_EN.put("CHOICESIGN20", "可选标志20");
        CLASSIFY_NEW_EN.put("EXPLAIN20", "说明20");
        CLASSIFY_NEW_EN.put("EXPLAINSIGN20", "说明标志20");
        MAPPINGS.add(new ParamDbTableMapping("CLASSIFY_NEW_EN", "Classify_New", CLASSIFY_NEW_EN));

        LinkedHashMap<String, String> CO_TYPE = new LinkedHashMap<>();
        CO_TYPE.put("CO_OWNER", "CO_OWNER");
        CO_TYPE.put("CO_OWNERSH", "CO_OWNERSH");
        MAPPINGS.add(new ParamDbTableMapping("CO_TYPE", "CO_TYPE", CO_TYPE));

        LinkedHashMap<String, String> COMPLEX = new LinkedHashMap<>();
        COMPLEX.put("CODE_T", "CODE_T");
        COMPLEX.put("CODE_S", "CODE_S");
        COMPLEX.put("G_NAME", "G_NAME");
        COMPLEX.put("LOW_RATE", "LOW_RATE");
        COMPLEX.put("HIGH_RATE", "HIGH_RATE");
        COMPLEX.put("OUT_RATE", "OUT_RATE");
        COMPLEX.put("REG_MARK", "REG_MARK");
        COMPLEX.put("REG_RATE", "REG_RATE");
        COMPLEX.put("TAX_TYPE", "TAX_TYPE");
        COMPLEX.put("TAX_RATE", "TAX_RATE");
        COMPLEX.put("COMM_RATE", "COMM_RATE");
        COMPLEX.put("TAIWAN_RAT", "TAIWAN_RAT");
        COMPLEX.put("OTHER_TYPE", "OTHER_TYPE");
        COMPLEX.put("OTHER_RATE", "OTHER_RATE");
        COMPLEX.put("UNIT_1", "UNIT_1");
        COMPLEX.put("UNIT_2", "UNIT_2");
        COMPLEX.put("ILOW_PRICE", "ILOW_PRICE");
        COMPLEX.put("IHIGH_PRIC", "IHIGH_PRIC");
        COMPLEX.put("ELOW_PRICE", "ELOW_PRICE");
        COMPLEX.put("EHIGH_PRIC", "EHIGH_PRIC");
        COMPLEX.put("MAX_IN", "MAX_IN");
        COMPLEX.put("MAX_OUT", "MAX_OUT");
        COMPLEX.put("CONTROL_MA", "CONTROL_MA");
        COMPLEX.put("CHK_PRICE", "CHK_PRICE");
        COMPLEX.put("TARIF_MARK", "TARIF_MARK");
        COMPLEX.put("NOTE_S", "NOTE_S");
        MAPPINGS.add(new ParamDbTableMapping("COMPLEX", "complex", COMPLEX));

        LinkedHashMap<String, String> CONDITION_CHECK = new LinkedHashMap<>();
        CONDITION_CHECK.put("ID", "ID");
        CONDITION_CHECK.put("SYSTEM_ID", "SYSTEM_ID");
        CONDITION_CHECK.put("SECURITY_ID", "SECURITY_ID");
        CONDITION_CHECK.put("MAINTAINANCE", "MAINTAINANCE");
        CONDITION_CHECK.put("CATEGORY", "CATEGORY");
        CONDITION_CHECK.put("NOTE", "NOTE");
        MAPPINGS.add(new ParamDbTableMapping("CONDITION_CHECK", "condition_check", CONDITION_CHECK));

        LinkedHashMap<String, String> CONDITION_CHECK_STRING = new LinkedHashMap<>();
        CONDITION_CHECK_STRING.put("SYSTEM_ID", "SYSTEM_ID");
        CONDITION_CHECK_STRING.put("SECURITY_ID", "SECURITY_ID");
        CONDITION_CHECK_STRING.put("ID", "ID");
        CONDITION_CHECK_STRING.put("STRING_NO", "STRING_NO");
        CONDITION_CHECK_STRING.put("STRING", "STRING");
        MAPPINGS.add(new ParamDbTableMapping("CONDITION_CHECK_STRING", "condition_check_string", CONDITION_CHECK_STRING));

        LinkedHashMap<String, String> CONTA_MODEL = new LinkedHashMap<>();
        CONTA_MODEL.put("CONTA_MODEL_CO", "CONTA_MODEL_CO");
        CONTA_MODEL.put("CONTA_MODEL_NAME", "CONTA_MODEL_NAME");
        MAPPINGS.add(new ParamDbTableMapping("CONTA_MODEL", "CONTA_MODEL", CONTA_MODEL));

        LinkedHashMap<String, String> COUNTRY = new LinkedHashMap<>();
        COUNTRY.put("COUNTRY_CO", "COUNTRY_CO");
        COUNTRY.put("ISO_E", "ISO_E");
        COUNTRY.put("COUNTRY_NA", "COUNTRY_NA");
        COUNTRY.put("COUNTRY_EN", "COUNTRY_EN");
        COUNTRY.put("HIGH_LOW", "HIGH_LOW");
        COUNTRY.put("EXAM_MARK", "EXAM_MARK");
        MAPPINGS.add(new ParamDbTableMapping("COUNTRY", "COUNTRY", COUNTRY));

        LinkedHashMap<String, String> CONTA_MODEL_STD = new LinkedHashMap<>();
        CONTA_MODEL_STD.put("CODESTD", "CODESTD");
        CONTA_MODEL_STD.put("NAMESTD", "NAMESTD");
        CONTA_MODEL_STD.put("CODECUS", "CODECUS");
        CONTA_MODEL_STD.put("NAMECUS", "NAMECUS");
        CONTA_MODEL_STD.put("CODECIQ", "CODECIQ");
        CONTA_MODEL_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("CONTA_MODEL_STD", "ContainerModelStd", CONTA_MODEL_STD));

        LinkedHashMap<String, String> CORRELATION_REASON = new LinkedHashMap<>();
        CORRELATION_REASON.put("CCM_UUID", "CCM_UUID");
        CORRELATION_REASON.put("INDEXID", "INDEXID");
        CORRELATION_REASON.put("CODE", "CODE");
        CORRELATION_REASON.put("CNAME", "CNAME");
        CORRELATION_REASON.put("ENAME", "ENAME");
        CORRELATION_REASON.put("SEQ_NO", "SEQ_NO");
        CORRELATION_REASON.put("ALIAS1", "ALIAS1");
        CORRELATION_REASON.put("ALIAS2", "ALIAS2");
        CORRELATION_REASON.put("ALIAS3", "ALIAS3");
        CORRELATION_REASON.put("FLAG", "FLAG");
        CORRELATION_REASON.put("LASTUPDATE", "LASTUPDATE");
        CORRELATION_REASON.put("VERSION", "VERSION");
        CORRELATION_REASON.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        CORRELATION_REASON.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("CORRELATION_REASON", "CorrelationReason", CORRELATION_REASON));

        LinkedHashMap<String, String> COUNTRY_STD = new LinkedHashMap<>();
        COUNTRY_STD.put("CODESTD", "CODESTD");
        COUNTRY_STD.put("NAMESTD", "NAMESTD");
        COUNTRY_STD.put("ENAMESTD", "ENAMESTD");
        COUNTRY_STD.put("CODECUS", "CODECUS");
        COUNTRY_STD.put("NAMECUS", "NAMECUS");
        COUNTRY_STD.put("CODECIQ", "CODECIQ");
        COUNTRY_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("COUNTRY_STD", "CountryStd", COUNTRY_STD));

        LinkedHashMap<String, String> CTA_INF_REC = new LinkedHashMap<>();
        CTA_INF_REC.put("COND_ID", "cond_id");
        CTA_INF_REC.put("T_FLAG", "t_flag");
        CTA_INF_REC.put("ITEM_ID", "item_id");
        CTA_INF_REC.put("B_CUT", "b_cut");
        CTA_INF_REC.put("E_CUT", "e_cut");
        CTA_INF_REC.put("BULL_FLAG", "bull_flag");
        CTA_INF_REC.put("CONTENTS_0", "contents_0");
        CTA_INF_REC.put("CONTENTS_1", "contents_1");
        MAPPINGS.add(new ParamDbTableMapping("CTA_INF_REC", "CTA_INF_REC", CTA_INF_REC));

        LinkedHashMap<String, String> CTA_MES_REC = new LinkedHashMap<>();
        CTA_MES_REC.put("MESS_CODE", "mess_code");
        CTA_MES_REC.put("MESS_LEVEL", "mess_level");
        CTA_MES_REC.put("MESS", "mess");
        MAPPINGS.add(new ParamDbTableMapping("CTA_MES_REC", "CTA_MES_REC", CTA_MES_REC));

        LinkedHashMap<String, String> CTA_NAM_REC = new LinkedHashMap<>();
        CTA_NAM_REC.put("ITEM_CODE", "item_code");
        CTA_NAM_REC.put("ITEM_NAME", "item_name");
        CTA_NAM_REC.put("ITEM_FLAG", "item_flag");
        CTA_NAM_REC.put("ITEM_TYPE", "item_type");
        MAPPINGS.add(new ParamDbTableMapping("CTA_NAM_REC", "CTA_NAM_REC", CTA_NAM_REC));

        LinkedHashMap<String, String> CURR = new LinkedHashMap<>();
        CURR.put("CURR_CODE", "CURR_CODE");
        CURR.put("CURR_SYMB", "CURR_SYMB");
        CURR.put("CURR_NAME", "CURR_NAME");
        MAPPINGS.add(new ParamDbTableMapping("CURR", "CURR", CURR));

        LinkedHashMap<String, String> CURR_STD = new LinkedHashMap<>();
        CURR_STD.put("CODESTD", "CODESTD");
        CURR_STD.put("NAMESTD", "NAMESTD");
        CURR_STD.put("ENAMESTD", "ENAMESTD");
        CURR_STD.put("CODECUS", "CODECUS");
        CURR_STD.put("NAMECUS", "NAMECUS");
        CURR_STD.put("CODECIQ", "CODECIQ");
        CURR_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("CURR_STD", "CurrStd", CURR_STD));

        LinkedHashMap<String, String> CUSTOMS = new LinkedHashMap<>();
        CUSTOMS.put("CUSTOMS_CO", "CUSTOMS_CO");
        CUSTOMS.put("CUSTOMS_NA", "CUSTOMS_NA");
        MAPPINGS.add(new ParamDbTableMapping("CUSTOMS", "CUSTOMS", CUSTOMS));

        LinkedHashMap<String, String> DANG_PACK_SPEC = new LinkedHashMap<>();
        DANG_PACK_SPEC.put("CODE", "CODE");
        DANG_PACK_SPEC.put("FULL_NAME", "FULL_NAME");
        DANG_PACK_SPEC.put("ABBR_NAME", "ABBR_NAME");
        MAPPINGS.add(new ParamDbTableMapping("DANG_PACK_SPEC", "DangPackSpec", DANG_PACK_SPEC));

        LinkedHashMap<String, String> DANG_PACK_TYPE = new LinkedHashMap<>();
        DANG_PACK_TYPE.put("CODE", "CODE");
        DANG_PACK_TYPE.put("FULL_NAME", "FULL_NAME");
        DANG_PACK_TYPE.put("ABBR_NAME", "ABBR_NAME");
        MAPPINGS.add(new ParamDbTableMapping("DANG_PACK_TYPE", "DangPackType", DANG_PACK_TYPE));

        LinkedHashMap<String, String> DECLARATION_MATERIAL_CODE = new LinkedHashMap<>();
        DECLARATION_MATERIAL_CODE.put("ITEMCODE", "ITEMCODE");
        DECLARATION_MATERIAL_CODE.put("ITEMNAME", "ITEMNAME");
        DECLARATION_MATERIAL_CODE.put("EXP_IMP_FLAG", "EXP_IMP_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("DECLARATION_MATERIAL_CODE", "DeclarationMaterialCode", DECLARATION_MATERIAL_CODE));

        LinkedHashMap<String, String> DISTRICT = new LinkedHashMap<>();
        DISTRICT.put("DISTRICT_C", "DISTRICT_C");
        DISTRICT.put("DISTRICT_N", "DISTRICT_N");
        DISTRICT.put("DISTRICT_T", "DISTRICT_T");
        MAPPINGS.add(new ParamDbTableMapping("DISTRICT", "DISTRICT", DISTRICT));

        LinkedHashMap<String, String> DOMESTIC_PORT = new LinkedHashMap<>();
        DOMESTIC_PORT.put("CODE", "CODE");
        DOMESTIC_PORT.put("CNAME", "CNAME");
        DOMESTIC_PORT.put("ENAME", "ENAME");
        MAPPINGS.add(new ParamDbTableMapping("DOMESTIC_PORT", "DomesticPort", DOMESTIC_PORT));

        LinkedHashMap<String, String> EDOC_CODE = new LinkedHashMap<>();
        EDOC_CODE.put("EDOC_CODE", "Edoc_Code");
        EDOC_CODE.put("EDOC_NAME", "Edoc_Name");
        EDOC_CODE.put("EDOC_TYPE", "Edoc_Type");
        MAPPINGS.add(new ParamDbTableMapping("EDOC_CODE", "Edoc_Code", EDOC_CODE));

        LinkedHashMap<String, String> EXCHSOUR = new LinkedHashMap<>();
        EXCHSOUR.put("EX_SOURCE", "EX_SOURCE");
        EXCHSOUR.put("ABBR_SOURC", "ABBR_SOURC");
        EXCHSOUR.put("FULL_SOURC", "FULL_SOURC");
        MAPPINGS.add(new ParamDbTableMapping("EXCHSOUR", "EXCHSOUR", EXCHSOUR));

        LinkedHashMap<String, String> FEE_MARK = new LinkedHashMap<>();
        FEE_MARK.put("CODE", "Code");
        FEE_MARK.put("NAME", "Name");
        MAPPINGS.add(new ParamDbTableMapping("FEE_MARK", "Fee_Mark", FEE_MARK));

        LinkedHashMap<String, String> GOODS_ATTR = new LinkedHashMap<>();
        GOODS_ATTR.put("CCM_UUID", "CCM_UUID");
        GOODS_ATTR.put("INDEXID", "INDEXID");
        GOODS_ATTR.put("CODE", "CODE");
        GOODS_ATTR.put("CNAME", "CNAME");
        GOODS_ATTR.put("ENAME", "ENAME");
        GOODS_ATTR.put("SEQ_NO", "SEQ_NO");
        GOODS_ATTR.put("ALIAS1", "ALIAS1");
        GOODS_ATTR.put("ALIAS2", "ALIAS2");
        GOODS_ATTR.put("ALIAS3", "ALIAS3");
        GOODS_ATTR.put("FLAG", "FLAG");
        GOODS_ATTR.put("LASTUPDATE", "LASTUPDATE");
        GOODS_ATTR.put("VERSION", "VERSION");
        GOODS_ATTR.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        GOODS_ATTR.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("GOODS_ATTR", "GoodsAttr", GOODS_ATTR));

        LinkedHashMap<String, String> HS_301 = new LinkedHashMap<>();
        HS_301.put("HSCODE", "HsCode");
        HS_301.put("TAX", "Tax");
        MAPPINGS.add(new ParamDbTableMapping("HS_301", "HS_301", HS_301));

        LinkedHashMap<String, String> HZZS_COMPANY = new LinkedHashMap<>();
        HZZS_COMPANY.put("COMPANY_CODE", "Company_Code");
        HZZS_COMPANY.put("SUMMARY_TAX", "Summary_Tax");
        HZZS_COMPANY.put("INDEPENDENT_TAX", "Independent_Tax");
        MAPPINGS.add(new ParamDbTableMapping("HZZS_COMPANY", "hzzs_Company", HZZS_COMPANY));

        LinkedHashMap<String, String> HZZS_CUSTOM = new LinkedHashMap<>();
        HZZS_CUSTOM.put("CUSTOM_CO", "Custom_Co");
        HZZS_CUSTOM.put("CUSTOM_NAME", "Custom_Name");
        MAPPINGS.add(new ParamDbTableMapping("HZZS_CUSTOM", "hzzs_Custom", HZZS_CUSTOM));

        LinkedHashMap<String, String> IC_CARD_LIMIT = new LinkedHashMap<>();
        IC_CARD_LIMIT.put("CUSTOMS_CODE", "CUSTOMS_CODE");
        IC_CARD_LIMIT.put("CUSTOMS_TYPE", "CUSTOMS_TYPE");
        IC_CARD_LIMIT.put("MEMO1", "MEMO1");
        IC_CARD_LIMIT.put("MEMO2", "MEMO2");
        MAPPINGS.add(new ParamDbTableMapping("IC_CARD_LIMIT", "ICCardLimit", IC_CARD_LIMIT));

        LinkedHashMap<String, String> IMPORT_SURTAXES = new LinkedHashMap<>();
        IMPORT_SURTAXES.put("CODE_TS", "CODE_TS");
        IMPORT_SURTAXES.put("COUNTRY_CODE", "COUNTRY_CODE");
        IMPORT_SURTAXES.put("COMPANY_NAME_CN", "COMPANY_NAME_CN");
        IMPORT_SURTAXES.put("COMPANY_NAME_EN", "COMPANY_NAME_EN");
        IMPORT_SURTAXES.put("TAX_TYPE", "TAX_TYPE");
        IMPORT_SURTAXES.put("ANTI_RATE", "ANTI_RATE");
        IMPORT_SURTAXES.put("BEGIN_DATE", "BEGIN_DATE");
        IMPORT_SURTAXES.put("END_DATE", "END_DATE");
        IMPORT_SURTAXES.put("NOTE_S", "NOTE_S");
        IMPORT_SURTAXES.put("G_MODEL", "G_MODEL");
        IMPORT_SURTAXES.put("PROMISE_PRICSE", "PROMISE_PRICSE");
        IMPORT_SURTAXES.put("PROMISE_PRICE_CURR", "PROMISE_PRICE_CURR");
        IMPORT_SURTAXES.put("PROMISE_PRICE_UNIT", "PROMISE_PRICE_UNIT");
        IMPORT_SURTAXES.put("ACTION_NO", "ACTION_NO");
        MAPPINGS.add(new ParamDbTableMapping("IMPORT_SURTAXES", "IMPORT_SURTAXES", IMPORT_SURTAXES));

        LinkedHashMap<String, String> INSUR_MARK = new LinkedHashMap<>();
        INSUR_MARK.put("CODE", "Code");
        INSUR_MARK.put("NAME", "Name");
        MAPPINGS.add(new ParamDbTableMapping("INSUR_MARK", "Insur_Mark", INSUR_MARK));

        LinkedHashMap<String, String> IOF_CERT_BILL = new LinkedHashMap<>();
        IOF_CERT_BILL.put("CCM_UUID", "CCM_UUID");
        IOF_CERT_BILL.put("INDEXID", "INDEXID");
        IOF_CERT_BILL.put("CODE", "CODE");
        IOF_CERT_BILL.put("CNAME", "CNAME");
        IOF_CERT_BILL.put("ENAME", "ENAME");
        IOF_CERT_BILL.put("SEQ_NO", "SEQ_NO");
        IOF_CERT_BILL.put("ALIAS1", "ALIAS1");
        IOF_CERT_BILL.put("ALIAS2", "ALIAS2");
        IOF_CERT_BILL.put("ALIAS3", "ALIAS3");
        IOF_CERT_BILL.put("FLAG", "FLAG");
        IOF_CERT_BILL.put("LASTUPDATE", "LASTUPDATE");
        IOF_CERT_BILL.put("VERSION", "VERSION");
        IOF_CERT_BILL.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        IOF_CERT_BILL.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("IOF_CERT_BILL", "IOFCertBill", IOF_CERT_BILL));

        LinkedHashMap<String, String> LC_TYPE = new LinkedHashMap<>();
        LC_TYPE.put("PAY_WAY", "PAY_WAY");
        LC_TYPE.put("PAY_NAME", "PAY_NAME");
        MAPPINGS.add(new ParamDbTableMapping("LC_TYPE", "LC_TYPE", LC_TYPE));

        LinkedHashMap<String, String> LEVYMODE = new LinkedHashMap<>();
        LEVYMODE.put("DUTY_MODE", "DUTY_MODE");
        LEVYMODE.put("DUTY_SPEC", "DUTY_SPEC");
        MAPPINGS.add(new ParamDbTableMapping("LEVYMODE", "LEVYMODE", LEVYMODE));

        LinkedHashMap<String, String> LEVYTYPE = new LinkedHashMap<>();
        LEVYTYPE.put("CUT_MODE", "CUT_MODE");
        LEVYTYPE.put("ABBR_CUT", "ABBR_CUT");
        LEVYTYPE.put("FULL_CUT", "FULL_CUT");
        MAPPINGS.add(new ParamDbTableMapping("LEVYTYPE", "LEVYTYPE", LEVYTYPE));

        LinkedHashMap<String, String> LICENSED = new LinkedHashMap<>();
        LICENSED.put("DOCU_CODE", "DOCU_CODE");
        LICENSED.put("DOCU_NAME", "DOCU_NAME");
        MAPPINGS.add(new ParamDbTableMapping("LICENSED", "LICENSED", LICENSED));

        LinkedHashMap<String, String> LICENSEN = new LinkedHashMap<>();
        LICENSEN.put("CODE_T", "CODE_T");
        LICENSEN.put("CODE_S", "CODE_S");
        LICENSEN.put("IE_MARK", "IE_MARK");
        LICENSEN.put("IE_NOTE", "IE_NOTE");
        MAPPINGS.add(new ParamDbTableMapping("LICENSEN", "LICENSEN", LICENSEN));

        LinkedHashMap<String, String> CLIENT_MAPPING = new LinkedHashMap<>();
        CLIENT_MAPPING.put("OBJECTID", "ObjectID");
        CLIENT_MAPPING.put("ENTABLENAME", "entablename");
        CLIENT_MAPPING.put("CHTABLENAME", "chtablename");
        CLIENT_MAPPING.put("ENCOLMNNAME", "encolmnname");
        CLIENT_MAPPING.put("CHCOLMNNAME", "chcolmnname");
        MAPPINGS.add(new ParamDbTableMapping("CLIENT_MAPPING", "Mapping", CLIENT_MAPPING));

        LinkedHashMap<String, String> OTHER_MARK = new LinkedHashMap<>();
        OTHER_MARK.put("CODE", "Code");
        OTHER_MARK.put("NAME", "Name");
        MAPPINGS.add(new ParamDbTableMapping("OTHER_MARK", "Other_Mark", OTHER_MARK));

        LinkedHashMap<String, String> PAY_MODE = new LinkedHashMap<>();
        PAY_MODE.put("PAY_CODE", "PAY_CODE");
        PAY_MODE.put("PAY_NAME", "PAY_NAME");
        MAPPINGS.add(new ParamDbTableMapping("PAY_MODE", "PAY_MODE", PAY_MODE));

        LinkedHashMap<String, String> PORT = new LinkedHashMap<>();
        PORT.put("PORT_CODE", "PORT_CODE");
        PORT.put("PORT_NAME", "PORT_NAME");
        MAPPINGS.add(new ParamDbTableMapping("PORT", "PORT", PORT));

        LinkedHashMap<String, String> PORT_STD = new LinkedHashMap<>();
        PORT_STD.put("CODESTD", "CODESTD");
        PORT_STD.put("NAMESTD", "NAMESTD");
        PORT_STD.put("ENAMESTD", "ENAMESTD");
        PORT_STD.put("CODECUS", "CODECUS");
        PORT_STD.put("NAMECUS", "NAMECUS");
        PORT_STD.put("CODECIQ", "CODECIQ");
        PORT_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("PORT_STD", "PortStd", PORT_STD));

        LinkedHashMap<String, String> PRODUCT_PERMISSION_TYPE = new LinkedHashMap<>();
        PRODUCT_PERMISSION_TYPE.put("CCM_UUID", "CCM_UUID");
        PRODUCT_PERMISSION_TYPE.put("INDEXID", "INDEXID");
        PRODUCT_PERMISSION_TYPE.put("ITEMCODE", "ITEMCODE");
        PRODUCT_PERMISSION_TYPE.put("ITEMNAME", "ITEMNAME");
        PRODUCT_PERMISSION_TYPE.put("MNGTNONAME", "MNGTNONAME");
        PRODUCT_PERMISSION_TYPE.put("ENFORCELEVELCODE", "ENFORCELEVELCODE");
        PRODUCT_PERMISSION_TYPE.put("ENFORCELEVELNAME", "ENFORCELEVELNAME");
        PRODUCT_PERMISSION_TYPE.put("CERTTYPECODE", "CERTTYPECODE");
        PRODUCT_PERMISSION_TYPE.put("CERTTYPENAME", "CERTTYPENAME");
        PRODUCT_PERMISSION_TYPE.put("PARENTCODE", "PARENTCODE");
        PRODUCT_PERMISSION_TYPE.put("FLAG", "FLAG");
        PRODUCT_PERMISSION_TYPE.put("LASTUPDATE", "LASTUPDATE");
        PRODUCT_PERMISSION_TYPE.put("LEAFFLAG", "LEAFFLAG");
        PRODUCT_PERMISSION_TYPE.put("VERSION", "VERSION");
        PRODUCT_PERMISSION_TYPE.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        PRODUCT_PERMISSION_TYPE.put("QUALIF_TYPE", "QUALIF_TYPE");
        PRODUCT_PERMISSION_TYPE.put("EXP_IMP_FLAG", "EXP_IMP_FLAG");
        PRODUCT_PERMISSION_TYPE.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("PRODUCT_PERMISSION_TYPE", "ProductPermissionType", PRODUCT_PERMISSION_TYPE));

        LinkedHashMap<String, String> QTY_1_RANGE = new LinkedHashMap<>();
        QTY_1_RANGE.put("CODE_TS", "code_ts");
        QTY_1_RANGE.put("QTY_1_RANGE", "qty_1_range");
        QTY_1_RANGE.put("TIPS", "tips");
        QTY_1_RANGE.put("REFUSE", "refuse");
        MAPPINGS.add(new ParamDbTableMapping("QTY_1_RANGE", "qty_1_range", QTY_1_RANGE));

        LinkedHashMap<String, String> QUATA = new LinkedHashMap<>();
        QUATA.put("COUNTRY_CO", "COUNTRY_CO");
        QUATA.put("CODE_T", "CODE_T");
        QUATA.put("CODE_S", "CODE_S");
        MAPPINGS.add(new ParamDbTableMapping("QUATA", "QUATA", QUATA));

        LinkedHashMap<String, String> SPEC_DECL = new LinkedHashMap<>();
        SPEC_DECL.put("CCM_UUID", "CCM_UUID");
        SPEC_DECL.put("INDEXID", "INDEXID");
        SPEC_DECL.put("CODE", "CODE");
        SPEC_DECL.put("CNAME", "CNAME");
        SPEC_DECL.put("ENAME", "ENAME");
        SPEC_DECL.put("SEQ_NO", "SEQ_NO");
        SPEC_DECL.put("ALIAS1", "ALIAS1");
        SPEC_DECL.put("ALIAS2", "ALIAS2");
        SPEC_DECL.put("ALIAS3", "ALIAS3");
        SPEC_DECL.put("FLAG", "FLAG");
        SPEC_DECL.put("LASTUPDATE", "LASTUPDATE");
        SPEC_DECL.put("VERSION", "VERSION");
        SPEC_DECL.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        SPEC_DECL.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("SPEC_DECL", "SpecDecl", SPEC_DECL));

        LinkedHashMap<String, String> SPECIALC = new LinkedHashMap<>();
        SPECIALC.put("CODE_T", "CODE_T");
        SPECIALC.put("CODE_S", "CODE_S");
        SPECIALC.put("TRADE_CO", "TRADE_CO");
        MAPPINGS.add(new ParamDbTableMapping("SPECIALC", "SPECIALC", SPECIALC));

        LinkedHashMap<String, String> TABLE_INFO = new LinkedHashMap<>();
        TABLE_INFO.put("OBJECTID", "objectID");
        TABLE_INFO.put("ENTABLENAME", "entablename");
        TABLE_INFO.put("CHTABLENAME", "chtablename");
        TABLE_INFO.put("ENCOLMNNAME", "encolmnname");
        TABLE_INFO.put("CHCOLMNNAME", "chcolmnname");
        MAPPINGS.add(new ParamDbTableMapping("TABLE_INFO", "TableInfo", TABLE_INFO));

        LinkedHashMap<String, String> TAX_TYPE = new LinkedHashMap<>();
        TAX_TYPE.put("TAX_CODE", "TAX_CODE");
        TAX_TYPE.put("TAX_SPEC", "TAX_SPEC");
        MAPPINGS.add(new ParamDbTableMapping("TAX_TYPE", "TAXTYPE", TAX_TYPE));

        LinkedHashMap<String, String> TRADE_MODE = new LinkedHashMap<>();
        TRADE_MODE.put("TRADE_MODE", "TRADE_MODE");
        TRADE_MODE.put("ABBR_TRADE", "ABBR_TRADE");
        TRADE_MODE.put("FULL_TRADE", "FULL_TRADE");
        MAPPINGS.add(new ParamDbTableMapping("TRADE_MODE", "TRADE", TRADE_MODE));

        LinkedHashMap<String, String> TRADE_MO = new LinkedHashMap<>();
        TRADE_MO.put("TRADE_MODE", "TRADE_MODE");
        TRADE_MO.put("DISTRICT_T", "DISTRICT_T");
        TRADE_MO.put("BASIC_IM", "BASIC_IM");
        TRADE_MO.put("BASIC_EX", "BASIC_EX");
        TRADE_MO.put("IM_CONTROL", "IM_CONTROL");
        TRADE_MO.put("EX_CONTROL", "EX_CONTROL");
        MAPPINGS.add(new ParamDbTableMapping("TRADE_MO", "TRADE_MO", TRADE_MO));

        LinkedHashMap<String, String> TRADE_MODE_STD = new LinkedHashMap<>();
        TRADE_MODE_STD.put("CODESTD", "CODESTD");
        TRADE_MODE_STD.put("NAMESTD", "NAMESTD");
        TRADE_MODE_STD.put("CODECUS", "CODECUS");
        TRADE_MODE_STD.put("NAMECUS", "NAMECUS");
        TRADE_MODE_STD.put("CODECIQ", "CODECIQ");
        TRADE_MODE_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("TRADE_MODE_STD", "TradeModeStd", TRADE_MODE_STD));

        LinkedHashMap<String, String> TRAF_MODE_STD = new LinkedHashMap<>();
        TRAF_MODE_STD.put("CODESTD", "CODESTD");
        TRAF_MODE_STD.put("NAMESTD", "NAMESTD");
        TRAF_MODE_STD.put("CODECUS", "CODECUS");
        TRAF_MODE_STD.put("NAMECUS", "NAMECUS");
        TRAF_MODE_STD.put("CODECIQ", "CODECIQ");
        TRAF_MODE_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("TRAF_MODE_STD", "TrafModeStd", TRAF_MODE_STD));

        LinkedHashMap<String, String> TRANS_FLAG = new LinkedHashMap<>();
        TRANS_FLAG.put("TRANS_FLAG_CO", "TRANS_FLAG_CO");
        TRANS_FLAG.put("TRANS_FLAG_NAME", "TRANS_FLAG_NAME");
        MAPPINGS.add(new ParamDbTableMapping("TRANS_FLAG", "TRANS_FLAG", TRANS_FLAG));

        LinkedHashMap<String, String> TRANSAC = new LinkedHashMap<>();
        TRANSAC.put("TRANS_MODE", "TRANS_MODE");
        TRANSAC.put("TRANS_SPEC", "TRANS_SPEC");
        MAPPINGS.add(new ParamDbTableMapping("TRANSAC", "TRANSAC", TRANSAC));

        LinkedHashMap<String, String> TRANSF = new LinkedHashMap<>();
        TRANSF.put("TRAF_CODE", "TRAF_CODE");
        TRANSF.put("TRAF_SPEC", "TRAF_SPEC");
        MAPPINGS.add(new ParamDbTableMapping("TRANSF", "TRANSF", TRANSF));

        LinkedHashMap<String, String> UNIT = new LinkedHashMap<>();
        UNIT.put("UNIT_CODE", "UNIT_CODE");
        UNIT.put("UNIT_NAME", "UNIT_NAME");
        UNIT.put("CONV_CODE", "CONV_CODE");
        UNIT.put("CONV_RATIO", "CONV_RATIO");
        MAPPINGS.add(new ParamDbTableMapping("UNIT", "UNIT", UNIT));

        LinkedHashMap<String, String> UNIT_STD = new LinkedHashMap<>();
        UNIT_STD.put("CODESTD", "CODESTD");
        UNIT_STD.put("NAMESTD", "NAMESTD");
        UNIT_STD.put("CODECUS", "CODECUS");
        UNIT_STD.put("NAMECUS", "NAMECUS");
        UNIT_STD.put("CODECIQ", "CODECIQ");
        UNIT_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("UNIT_STD", "UnitStd", UNIT_STD));

        LinkedHashMap<String, String> USE_TO = new LinkedHashMap<>();
        USE_TO.put("USE_TO_COD", "USE_TO_COD");
        USE_TO.put("USE_TO_NAM", "USE_TO_NAM");
        MAPPINGS.add(new ParamDbTableMapping("USE_TO", "USE_TO", USE_TO));

        LinkedHashMap<String, String> USE_PURPOSE = new LinkedHashMap<>();
        USE_PURPOSE.put("CCM_UUID", "CCM_UUID");
        USE_PURPOSE.put("INDEXID", "INDEXID");
        USE_PURPOSE.put("CODE", "CODE");
        USE_PURPOSE.put("CNAME", "CNAME");
        USE_PURPOSE.put("ENAME", "ENAME");
        USE_PURPOSE.put("SEQ_NO", "SEQ_NO");
        USE_PURPOSE.put("ALIAS1", "ALIAS1");
        USE_PURPOSE.put("ALIAS2", "ALIAS2");
        USE_PURPOSE.put("ALIAS3", "ALIAS3");
        USE_PURPOSE.put("FLAG", "FLAG");
        USE_PURPOSE.put("LASTUPDATE", "LASTUPDATE");
        USE_PURPOSE.put("VERSION", "VERSION");
        USE_PURPOSE.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        USE_PURPOSE.put("OTHER_FLAG", "OTHER_FLAG");
        USE_PURPOSE.put("ALIAS4", "ALIAS4");
        USE_PURPOSE.put("ALIAS5", "ALIAS5");
        USE_PURPOSE.put("ALIAS6", "ALIAS6");
        USE_PURPOSE.put("ALIAS7", "ALIAS7");
        USE_PURPOSE.put("ALIAS8", "ALIAS8");
        USE_PURPOSE.put("ALIAS9", "ALIAS9");
        MAPPINGS.add(new ParamDbTableMapping("USE_PURPOSE", "UsePurpose", USE_PURPOSE));

        LinkedHashMap<String, String> Z_CCM_WORLDFIRSTDISTRICT = new LinkedHashMap<>();
        Z_CCM_WORLDFIRSTDISTRICT.put("CCM_UUID", "CCM_UUID");
        Z_CCM_WORLDFIRSTDISTRICT.put("INDEXID", "INDEXID");
        Z_CCM_WORLDFIRSTDISTRICT.put("ITEMCODE", "ITEMCODE");
        Z_CCM_WORLDFIRSTDISTRICT.put("ISO2", "ISO2");
        Z_CCM_WORLDFIRSTDISTRICT.put("ISO3", "ISO3");
        Z_CCM_WORLDFIRSTDISTRICT.put("ITEMNAME", "ITEMNAME");
        Z_CCM_WORLDFIRSTDISTRICT.put("ENAME", "ENAME");
        Z_CCM_WORLDFIRSTDISTRICT.put("STATE", "STATE");
        Z_CCM_WORLDFIRSTDISTRICT.put("CIQ", "CIQ");
        Z_CCM_WORLDFIRSTDISTRICT.put("OLDCODE", "OLDCODE");
        Z_CCM_WORLDFIRSTDISTRICT.put("OLDCNAME", "OLDCNAME");
        Z_CCM_WORLDFIRSTDISTRICT.put("PARENTCODE", "PARENTCODE");
        Z_CCM_WORLDFIRSTDISTRICT.put("FLAG", "FLAG");
        Z_CCM_WORLDFIRSTDISTRICT.put("LASTUPDATE", "LASTUPDATE");
        Z_CCM_WORLDFIRSTDISTRICT.put("LEAFFLAG", "LEAFFLAG");
        Z_CCM_WORLDFIRSTDISTRICT.put("VERSION", "VERSION");
        Z_CCM_WORLDFIRSTDISTRICT.put("FMK_ARCHIVE_FLAG", "FMK_ARCHIVE_FLAG");
        Z_CCM_WORLDFIRSTDISTRICT.put("OTHER_FLAG", "OTHER_FLAG");
        MAPPINGS.add(new ParamDbTableMapping("Z_CCM_WORLDFIRSTDISTRICT", "WorldFirstDistrict", Z_CCM_WORLDFIRSTDISTRICT));

        LinkedHashMap<String, String> WRAP_TYPE = new LinkedHashMap<>();
        WRAP_TYPE.put("WRAP_CODE", "WRAP_CODE");
        WRAP_TYPE.put("WRAP_NAME", "WRAP_NAME");
        MAPPINGS.add(new ParamDbTableMapping("WRAP_TYPE", "WRAP_TYPE", WRAP_TYPE));

        LinkedHashMap<String, String> WRAP_TYPE_STD = new LinkedHashMap<>();
        WRAP_TYPE_STD.put("CODESTD", "CODESTD");
        WRAP_TYPE_STD.put("NAMESTD", "NAMESTD");
        WRAP_TYPE_STD.put("CODECUS", "CODECUS");
        WRAP_TYPE_STD.put("NAMECUS", "NAMECUS");
        WRAP_TYPE_STD.put("CODECIQ", "CODECIQ");
        WRAP_TYPE_STD.put("NAMECIQ", "NAMECIQ");
        MAPPINGS.add(new ParamDbTableMapping("WRAP_TYPE_STD", "WrapTypeStd", WRAP_TYPE_STD));

        LinkedHashMap<String, String> YLXQ_CHECK = new LinkedHashMap<>();
        YLXQ_CHECK.put("HS", "HS");
        MAPPINGS.add(new ParamDbTableMapping("YLXQ_CHECK", "YLQX_check", YLXQ_CHECK));
    }

    /**
     * 根据资源表名获取映射
     *
     * @param resourceTableName 资源表名
     *
     * @return 参数库表映射
     * */
    public static ParamDbTableMapping getByResourceTableName(String resourceTableName) {
        for (ParamDbTableMapping paramDbTableMapping : MAPPINGS) {
            if (paramDbTableMapping.getResourceTableName().equals(resourceTableName)) {
                return paramDbTableMapping;
            }
        }

        return null;
    }

    /**
     * 根据数据库表名获取映射
     *
     * @param dbTableName 数据库表名
     *
     * @return 参数库表映射
     * */
    public static ParamDbTableMapping getByDbTableName(String dbTableName) {
        for (ParamDbTableMapping paramDbTableMapping : MAPPINGS) {
            if (paramDbTableMapping.getDbTableName().equals(dbTableName)) {
                return paramDbTableMapping;
            }
        }

        return null;
    }

}