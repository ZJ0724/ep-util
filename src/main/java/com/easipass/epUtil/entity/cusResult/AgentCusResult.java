package com.easipass.epUtil.entity.cusResult;

import com.easipass.epUtil.entity.CusResult;
import com.easipass.epUtil.entity.DTO.CusResultDTO;
import com.easipass.epUtil.entity.oracle.SWGDOracle;
import com.easipass.epUtil.entity.resources.cusResult.AgentCusResultResource;
import com.easipass.epUtil.util.DateUtil;
import com.easipass.epUtil.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 代理委托回执
 *
 * @author ZJ
 * */
public final class AgentCusResult extends CusResult {

    /**
     * ediNo
     * */
    private final String ediNo;

    /**
     * 构造函数
     *
     * @param cusResultDTO cusResultDTO
     */
    public AgentCusResult(CusResultDTO cusResultDTO, String ediNo) {
        super(cusResultDTO);
        this.ediNo = ediNo;
    }

    @Override
    public String getData() {
        // 获取回执原节点
        AgentCusResultResource agentCusResultResource = AgentCusResultResource.getInstance();
        Document document = XmlUtil.getDocument(agentCusResultResource.getInputStream());
        agentCusResultResource.closeInputStream();

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(this.getChannel());
        ResponseInfo.element("ResponseNotes").setText(this.getNote());
        rootElement.element("ConsignNo").setText(getAgentSeqNo());
        rootElement.element("DecEntryID").setText(ediNo);
        ResponseInfo.element("CopCusCode").setText(new SWGDOracle().queryAgentCode(ediNo));

        return document.asXML();
    }

    @Override
    public String getName() {
        return "agentResult-" + this.ediNo + "-" + DateUtil.getTime();
    }

    /**
     * 获取agentSeqNo
     *
     * @return agentSeqNo
     * */
    private String getAgentSeqNo() {
        return "agentSeqNo000" + this.ediNo.substring(this.ediNo.length() - 5);
    }

}