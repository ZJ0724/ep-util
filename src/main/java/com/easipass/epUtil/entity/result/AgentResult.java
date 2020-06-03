package com.easipass.epUtil.entity.result;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.service.impl.AgentResultServiceImpl;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;

public class AgentResult extends Result {

    /**
     * ediNo
     * */
    private String ediNo;

    /**
     * 构造函数
     */
    public AgentResult(String ediNo, ResultDTO resultDTO) {
        super(resultDTO);
        setEdiNo(ediNo);
        init();
    }

    /**
     * get,set
     * */
    public String getEdiNo() {
        return ediNo;
    }

    public void setEdiNo(String ediNo) {
        this.ediNo = ediNo;
    }

    /**
     * 获取agentSeqNo
     * */
    private String getAgentSeqNo() {
        return "agentSeqNo000" + ediNo.substring(ediNo.length() - 5);
    }

    @Override
    public String makeData() {
        // 获取回执原节点
        Document document = XmlUtil.getDocument(AgentResultServiceImpl.class.getResourceAsStream(ResourcePathConfig.AGENT_RESULT_PATH));

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(this.getChannel());
        ResponseInfo.element("ResponseNotes").setText(this.getNote());
        rootElement.element("ConsignNo").setText(getAgentSeqNo());
        rootElement.element("DecEntryID").setText(ediNo);
        // 查询AGENT_CODE
        SWGDOracle swgdOracle = new SWGDOracle();
        ResponseInfo.element("CopCusCode").setText(swgdOracle.queryAgentCode(ediNo));

        return document.asXML();
    }

    @Override
    public String makeFileName() {
        return "agentResult-" + ediNo + "-" + DateUtil.getTime();
    }

}