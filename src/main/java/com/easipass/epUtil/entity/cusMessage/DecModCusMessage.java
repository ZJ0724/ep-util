package com.easipass.epUtil.entity.cusMessage;

import com.easipass.epUtil.api.websocket.BaseWebsocketApi;
import com.easipass.epUtil.entity.CusMessage;
import com.easipass.epUtil.entity.VO.CusMessageComparisonVO;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.exception.CusMessageException;
import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.*;

/**
 * 修撤单报文
 *
 * @author ZJ
 * */
public final class DecModCusMessage extends CusMessage {

    /**
     * 报关单号
     * */
    private final String preEntryId;

    /**
     * DecModMessage
     * */
    private final Element DecModMessage;

    /**
     * ItemList
     * */
    private final List<Element> ItemList = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param multipartFile 前端传过来的报文
     * */
    public DecModCusMessage(MultipartFile multipartFile) {
        // DecModMessage
        Element rootElement;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            rootElement = XmlUtil.getDocument_v2(inputStream).getRootElement();
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        } catch (DocumentException e) {
            throw new CusMessageException("不是正确的修撤单报文");
        }

        this.DecModMessage = rootElement;

        // preEntryId
        String EntryId = rootElement.element("EntryId").getText();

        if (EntryId == null || "".equals(EntryId)) {
            throw new CusMessageException("报文中 <EntryId> 缺失");
        }
        this.preEntryId = EntryId;

        // items
        Element Items = this.DecModMessage.element("Items");

        if (Items != null) {
            ItemList.addAll(Items.elements("Item"));
        }
    }


    @Override
    public void comparison(BaseWebsocketApi baseWebsocketApi) {
        // 数据库
        SWGDOracle SWGDOracle = new SWGDOracle();

        try {
            SWGDOracle.connect();

            // 报关单号
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType("[preEntryId: " + this.preEntryId + "]"));

            // 比对表头头
            String decModHeadMessage = "[修撤单表头]";

            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decModHeadMessage));

            ResultSet decModHeadResultSet = SWGDOracle.queryDecModHead(this.preEntryId);

            if (checkResultSet(decModHeadResultSet, decModHeadMessage, baseWebsocketApi)) {
                ResultSet formHeadResult = SWGDOracle.queryFormHeadByPreEntryId(this.preEntryId);

                if (checkResultSet(formHeadResult, "[报关单表头]", baseWebsocketApi)) {
                    Set<String> keys = DecModCusMessageNodeMapping.DEC_MOD_HEAD_MAPPING.keySet();

                    for (String key : keys) {
                        MapKeyValue mapKeyValue = getKeyValue(
                                this.DecModMessage,
                                DecModCusMessageNodeMapping.DEC_MOD_HEAD_MAPPING,
                                key,
                                decModHeadResultSet
                        );
                        String key1 = mapKeyValue.getKey1();

                        // CustomsCode
                        if ("CustomsCode".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "DECL_PORT"));
                        }

                        // TradeCode
                        if ("TradeCode".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "TRADE_CO"));
                        }

                        // TradeName
                        if ("TradeName".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "TRADE_NAME"));
                        }

                        // AgentName
                        if ("AgentName".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "AGENT_NAME"));
                        }

                        // IeFlag
                        if ("IeFlag".equals(key1)) {
                            // IE_FLAG
                            String ieFlag = getDbValue(formHeadResult, "IE_FLAG");
                            String dbValue = "";

                            switch (ieFlag) {
                                case "0" : case "2" : case "4" : case "6" : case "8" : case "A" :
                                    dbValue = "E";
                                    break;

                                case "1" : case "3" : case "5" : case "7" : case "9" : case "B" :
                                case "D" :  case "F" :
                                    dbValue = "I";
                                    break;
                            }
                            mapKeyValue.setDbValue(dbValue);
                        }

                        // IcCode
                        if ("IcCode".equals(key1)) {
                            mapKeyValue.setDbValue(mapKeyValue.getDbValue() + "|0107d927");
                        }

                        // TradeCreditCode
                        if ("TradeCreditCode".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "TRADE_CO_SCC"));
                        }

                        // AgentCreditCode
                        if ("AgentCreditCode".equals(key1)) {
                            mapKeyValue.setDbValue(getDbValue(formHeadResult, "AGENT_CODE_SCC"));
                        }

                        // Sign
                        if ("Sign".equals(key1) && "".equals(mapKeyValue.getNodeValue())) {
                            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getComparisonFalseType(mapKeyValue.getKey()));
                        } else {
                            comparison(mapKeyValue, baseWebsocketApi);
                        }
                    }
                }
            }

            // 比对表体
            int No = 1;

            for (Element element : this.ItemList) {
                String FieldName = getNodeValue(element, "FieldName");
                String decModListMessage = "[修撤单表体 - " + FieldName + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decModListMessage));

                ResultSet decModListResultSet = SWGDOracle.queryDecModList(this.preEntryId, getNodeValue(element, "FieldCode"));

                if (!checkResultSet(decModListResultSet, decModListMessage, baseWebsocketApi)) {
                    continue;
                }

                Set<String> keys = DecModCusMessageNodeMapping.DEC_MOD_LIST_MAPPING.keySet();

                for (String key : keys) {
                    MapKeyValue mapKeyValue = getKeyValue(
                            element,
                            DecModCusMessageNodeMapping.DEC_MOD_LIST_MAPPING,
                            key,
                            decModListResultSet
                    );

                    //No
                    if ("No".equals(mapKeyValue.getKey1())) {
                        mapKeyValue.setDbValue(No + "");
                        No++;
                    }

                    comparison(mapKeyValue, baseWebsocketApi);
                }
            }
        } catch (OracleException e) {
            baseWebsocketApi.sendMessage(CusMessageComparisonVO.getErrorType(e.getMessage()));
        } finally {
            SWGDOracle.close();
            baseWebsocketApi.close();
        }
    }

    /**
     * 修撤单节点映射
     *
     * @author ZJ
     * */
    private static class DecModCusMessageNodeMapping {

        /**
         * 表头
         * */
        private static final Map<String, String> DEC_MOD_HEAD_MAPPING = new LinkedHashMap<>();

        static {
            DEC_MOD_HEAD_MAPPING.put("DecModSeqNo[修撤单统一编号]", "DECMODSEQNO");
            DEC_MOD_HEAD_MAPPING.put("Version", null);
            DEC_MOD_HEAD_MAPPING.put("DecModType[修撤单类型]", "DECMODTYPE");
            DEC_MOD_HEAD_MAPPING.put("EntryId[报关单号]", "PRE_ENTRY_ID");
            DEC_MOD_HEAD_MAPPING.put("CustomsCode[申报地海关]", "?");
            DEC_MOD_HEAD_MAPPING.put("TradeCode[收发货人代码]", "?");
            DEC_MOD_HEAD_MAPPING.put("TradeName[收发货人名称]", "?");
            DEC_MOD_HEAD_MAPPING.put("AgentCode[企业代码]", "TRADE_CODE");
            DEC_MOD_HEAD_MAPPING.put("AgentName[企业名称]", "?");
            DEC_MOD_HEAD_MAPPING.put("DecModNote[修撤单原因]", "DECMODNOTE");
            DEC_MOD_HEAD_MAPPING.put("CheckMark[审查表识]", "CHECKMARK");
            DEC_MOD_HEAD_MAPPING.put("DecSeqNo[报关单统一编号]", "DECSEQNO");
            DEC_MOD_HEAD_MAPPING.put("Sign[加签信息]", "SIGNTXT");
            DEC_MOD_HEAD_MAPPING.put("SignTime[加签时间]", "SIGN_TIME");
            DEC_MOD_HEAD_MAPPING.put("IeFlag[进出口标识]", "?");
            DEC_MOD_HEAD_MAPPING.put("OperType[操作类型]", "OPERTYPE");
            DEC_MOD_HEAD_MAPPING.put("IcCode[IC卡号]", "IC_CODE");
            DEC_MOD_HEAD_MAPPING.put("EntOpName[联系人]", "ENTOPNAME");
            DEC_MOD_HEAD_MAPPING.put("EntOpTele[联系方式]", "ENTOPTELE");
            DEC_MOD_HEAD_MAPPING.put("FeedDept[岗位]", "FEEDDEPT");
            DEC_MOD_HEAD_MAPPING.put("TradeCreditCode[境内收发货人统一社会信用代码]", "?");
            DEC_MOD_HEAD_MAPPING.put("AgentCreditCode[申报单位统一社会信用代码]", "?");
        }

        /**
         * 表体
         * */
        private static final Map<String, String> DEC_MOD_LIST_MAPPING = new LinkedHashMap<>();

        static {
            DEC_MOD_LIST_MAPPING.put("No", "?");
            DEC_MOD_LIST_MAPPING.put("FieldCode[字段代码]", "QP_FIELDCODE");
            DEC_MOD_LIST_MAPPING.put("FieldName[字段名]", "QP_FIELDNAME");
            DEC_MOD_LIST_MAPPING.put("OldValue[字段原值]", "OLDVALUE");
            DEC_MOD_LIST_MAPPING.put("NewValue[字段新值]", "NEWVALUE");
            DEC_MOD_LIST_MAPPING.put("OldName[字段原值参数表对应中文名称]", "OLDNAME");
            DEC_MOD_LIST_MAPPING.put("NewName[字段新值参数表对应中文名称]", "NEWNAME");
        }

    }

}