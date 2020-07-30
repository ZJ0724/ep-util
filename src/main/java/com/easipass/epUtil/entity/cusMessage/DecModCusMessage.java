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
        Element EntryId = rootElement.element("EntryId");

        if (EntryId == null) {
            throw new CusMessageException("报文中 <EntryId> 缺失");
        }

        String EntryIdS = EntryId.getText();

        if ("".equals(EntryIdS)) {
            throw new CusMessageException("报文中 <EntryId> 缺失");
        }

        this.preEntryId = EntryIdS;

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

            if (NodeMapping.checkResultSet(decModHeadResultSet, decModHeadMessage, baseWebsocketApi)) {
                ResultSet formHeadResult = SWGDOracle.queryFormHeadByPreEntryId(this.preEntryId);

                if (NodeMapping.checkResultSet(formHeadResult, "[报关单表头]", baseWebsocketApi)) {
                    for (NodeMapping nodeMapping : DecModCusMessageNodeMapping.DEC_MOD_HEAD_MAPPING) {
                        nodeMapping.loadData(this.DecModMessage, decModHeadResultSet);

                        String node = nodeMapping.getNode();

                        // CustomsCode
                        if ("CustomsCode".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "DECL_PORT"));
                        }

                        // TradeCode
                        if ("TradeCode".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "TRADE_CO"));
                        }

                        // TradeName
                        if ("TradeName".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "TRADE_NAME"));
                        }

                        // AgentName
                        if ("AgentName".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "AGENT_NAME"));
                        }

                        // IeFlag
                        if ("IeFlag".equals(node)) {
                            // IE_FLAG
                            String ieFlag = NodeMapping.getDbValue(formHeadResult, "IE_FLAG");

                            nodeMapping.setDbValue(NodeMapping.getIeFlag(ieFlag));
                        }

                        // IcCode
                        if ("IcCode".equals(node)) {
                            String CERT_NO = NodeMapping.getDbValue(decModHeadResultSet, "CERT_NO");

                            nodeMapping.setDbValue(nodeMapping.getDbValue() + "|" + CERT_NO);
                        }

                        // TradeCreditCode
                        if ("TradeCreditCode".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "TRADE_CO_SCC"));
                        }

                        // AgentCreditCode
                        if ("AgentCreditCode".equals(node)) {
                            nodeMapping.setDbValue(NodeMapping.getDbValue(formHeadResult, "AGENT_CODE_SCC"));
                        }

                        nodeMapping.comparison(baseWebsocketApi);
                    }
                }
            }

            // 比对表体
            int No = 1;

            for (Element element : this.ItemList) {
                String FieldName = NodeMapping.getNodeValue(element, "FieldName");
                String decModListMessage = "[修撤单表体 - " + FieldName + "]";

                baseWebsocketApi.sendMessage(CusMessageComparisonVO.getTitleType(decModListMessage));

                ResultSet decModListResultSet = SWGDOracle.queryDecModList(this.preEntryId, NodeMapping.getNodeValue(element, "FieldCode"));

                if (!NodeMapping.checkResultSet(decModListResultSet, decModListMessage, baseWebsocketApi)) {
                    continue;
                }

                for (NodeMapping nodeMapping : DecModCusMessageNodeMapping.DEC_MOD_LIST_MAPPING) {
                    nodeMapping.loadData(element, decModListResultSet);

                    //No
                    if ("No".equals(nodeMapping.getNode())) {
                        nodeMapping.setDbValue(No + "");
                        No++;
                    }

                    nodeMapping.comparison(baseWebsocketApi);
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
        private static final List<NodeMapping> DEC_MOD_HEAD_MAPPING = new ArrayList<>();

        static {
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("DecModSeqNo", "修撤单统一编号", "DECMODSEQNO"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("Version", "?", null));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("DecModType", "修撤单类型", "DECMODTYPE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("EntryId", "报关单号", "PRE_ENTRY_ID"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("CustomsCode", "申报地海关", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("TradeCode", "收发货人代码", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("TradeName", "收发货人名称", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("AgentCode", "企业代码", "TRADE_CODE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("AgentName", "企业名称", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("DecModNote", "修撤单原因", "DECMODNOTE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("CheckMark", "审查表识", "CHECKMARK"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("DecSeqNo", "报关单统一编号", "DECSEQNO"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("Sign", "加签信息", "SIGNTXT", true));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("SignTime", "加签时间", "SIGN_TIME"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("IeFlag", "进出口标识", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("OperType", "操作类型", "OPERTYPE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("IcCode", "IC卡号", "IC_CODE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("EntOpName", "联系人", "ENTOPNAME"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("EntOpTele", "联系方式", "ENTOPTELE"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("FeedDept", "岗位", "FEEDDEPT"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("TradeCreditCode", "境内收发货人统一社会信用代码", "?"));
            DEC_MOD_HEAD_MAPPING.add(new NodeMapping("AgentCreditCode", "申报单位统一社会信用代码", "?"));
        }

        /**
         * 表体
         * */
        private static final List<NodeMapping> DEC_MOD_LIST_MAPPING = new ArrayList<>();

        static {
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("No", "序号", "?"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("FieldCode", "字段代码", "QP_FIELDCODE"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("FieldName", "字段名", "QP_FIELDNAME"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("OldValue", "字段原值", "OLDVALUE"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("NewValue", "字段新值", "NEWVALUE"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("OldName", "字段原值参数表对应中文名称", "OLDNAME"));
            DEC_MOD_LIST_MAPPING.add(new NodeMapping("NewName", "字段新值参数表对应中文名称", "NEWNAME"));
        }

    }

}