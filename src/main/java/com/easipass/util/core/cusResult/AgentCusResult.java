package com.easipass.util.core.cusResult;

import com.easipass.util.core.CusResult;
import com.easipass.util.core.DTO.CusResultDTO;
import com.easipass.util.core.Resource;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.core.exception.CusResultException;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.XmlUtil;
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
        SWGDDatabase swgdDatabase = new SWGDDatabase();
        String agentCode = swgdDatabase.queryAgentCode(this.ediNo);

        if (agentCode == null) {
            throw new CusResultException("报关单: " + this.ediNo + "agentCode为null");
        }

        swgdDatabase.close();

        // 获取回执原节点
        Document document = XmlUtil.getDocument(Resource.AGENT_CUS_RESULT);

        // 获取根节点
        Element rootElement = document.getRootElement();

        // 替换数据
        Element ResponseInfo = rootElement.element("ResponseInfo");
        ResponseInfo.element("ResponseCode").setText(this.getChannel());
        ResponseInfo.element("ResponseNotes").setText(this.getNote());
        rootElement.element("ConsignNo").setText(getAgentSeqNo());
        rootElement.element("DecEntryID").setText(ediNo);
        ResponseInfo.element("CopCusCode").setText(agentCode);

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