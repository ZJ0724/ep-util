package com.easipass.EpUtilServer.entity.DTO;

import org.springframework.stereotype.Component;

@Component
public class AgentResultDTO extends ResultDTO {

    /**
     * 10位海关编码
     * */
    private String tradeCode;

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    /**
     * 获取代理委托编号
     * */
    public static String getAgentSeqNo(String ediNo) {
        return "agentSeqNo000" + ediNo.substring(ediNo.length() - 5);
    }

}
