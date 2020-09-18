package com.easipass.util.core.service;

import com.alibaba.fastjson.JSONObject;
import com.easipass.util.core.CusMessage;
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
     * @param inputStream multipartFile
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

                    // 如果IE_TYPE为0，则DeclareName可以为null
                    if ("0".equals(ieType)) {
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
                    String[] values = new String[]{"TYPE_2", "TYPE_3", "TYPE_4", "TYPE_5"};
                    String newDbValue = "  ";

                    if ("".equals(nodeValue)) {
                        newDbValue = "";
                    }

                    for (String value : values) {
                        String s = getDbValue(databaseFormHead, value);
                        newDbValue = StringUtil.append(newDbValue, s);
                    }
                    dbValue = newDbValue;
                }

                result.comparison(nodeValue, dbValue, nodeMapping, "表头");
            }
        }

        // 比对表体
        // 数据库表体数据
        List<JSONObject> databaseFormListList = SWGDDatabase.queryBySql("SELECT * FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_LIST WHERE HEAD_ID = (SELECT ID FROM " + SWGDDatabase.SWGD + ".T_SWGD_FORM_HEAD WHERE EDI_NO = '" + ediNo + "')");
        // 报文DecLists节点
        Element DecLists = rootElement.element("DecLists");

        // 数据库表体数据必须和子节点数一致
        if (databaseFormListList.size() != DecLists.elements().size()) {
            result.addMessage("[表体] 节点与数据库数量不一致");
        } else {
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
                    result.addMessage("[表体 - " + (i + 1) + "] 数据库不存在");
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
                    result.addMessage("[表体 - " + (i + 1) + "] 报文不存在");
                    continue;
                }


            }
        }

        if (result.getMessages().size() == 0) {
            result.setFlag(true);
            result.addMessage("比对完成，无差异");
        } else {
            result.setFlag(false);
        }

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
     * 比对信息
     *
     * @author ZJ
     * */
    public static final class ComparisonMessage {

        /**
         * 状态
         * */
        private boolean flag;

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
         * @param title 标题
         * */
        public void comparison(String nodeValue, String dbValue, NodeMapping nodeMapping, String title) {
            if (nodeMapping.notNull) {
                if (nodeValue == null) {
                    this.addMessage("[" + title + "] - <" + nodeMapping.node + "(" + nodeMapping.name + ")> 为空");
                }
            }

            if (dbValue == null) {
                dbValue = "";
            }
            if (nodeValue == null) {
                nodeValue = "";
            }

            if (!dbValue.equals(nodeValue)) {
                this.addMessage("[" + title + "] - <" + nodeMapping.node + "(" + nodeMapping.name + ")>, 数据库值: " + dbValue + "; 节点值: " + nodeValue);
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

            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));
            DecListNodeMapping.add(new NodeMapping("", "", ""));

            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("", "", ""));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("", "", ""));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("", "", ""));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("OrigPlaceCode", "原产地区代码", "ORIG_PLACE_CODE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("Purpose", "用途代码", "PURPOSE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("ProdValidDt", "产品有效期", "PROD_VALID_DT"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("ProdQgp", "产品保质期", "PROD_QGP"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("GoodsAttr", "货物属性代码", "GOODS_ATTR"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("Stuff", "成份/原料/组份", "STUFF"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("Uncode", "UN编码", "UN_CODE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("DangName", "危险货物名称", "DANG_NAME"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("DangPackType", "危包类别", "DANG_PACK_TYPE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("DangPackSpec", "危包规格", "DANG_PACK_SPEC"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("EngManEntCnm", "境外生产企业名称", "ENG_MAN_ENT_CNM"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("NoDangFlag", "?", "NO_DANG_FLAG"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("DestCode", "目的地代码", "DEST_CODE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("GoodsSpec", "检验检疫货物规格", "GOODS_SPEC"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("GoodsModel", "货物型号", "GOODS_MODEL"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("GoodsBrand", "货物品牌", "GOODS_BRAND"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("ProduceDate", "生产日期", "PRODUCE_DATE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("ProdBatchNo", "生产批号", "PROD_BATCH_NO"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("DistrictCode", "境内目的地/境内货源地", "DISTRICT_CODE"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("CiqName", "检验检疫名称", "CIQ_NAME"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("MnufctrRegno", "生产单位注册号", "MNUFCTR_REGNO"));
            FORM_LIST_MAPPING.add(new CusMessage.NodeMapping("MnufctrRegName", "生产单位名称", "MNUFCTR_REG_NAME"));
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