package com.easipass.EpUtilServer.entity;

import org.springframework.stereotype.Component;

@Component
public class ResultDTO {

    /**
     * 状态
     * */
    private String channel;

    /**
     * 备注
     * */
    private String note;

    public ResultDTO() {}

    public ResultDTO(String channel, String note) {
        this.channel = channel;
        this.note = note;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取报关单号
     * */
    public static String getPreEntryId(String ediNo) {
        return "2217000000000" + ediNo.substring(ediNo.length() - 5);
    }

    /**
     * 获取seqNo
     * */
    public static String getSeqNo(String ediNo) {
        return "seqNo00000000" + ediNo.substring(ediNo.length() - 5);
    }

    /**
     * 获取文件名
     * */
    public static String getFileName(String preEntryId) {
        return "CUS" + preEntryId + ".xml";
    }

    /**
     * 获取decModSeqNo
     * */
    public static String getDecModSeqNo(String preEntryId) {
        return "decModSeqNo00" + preEntryId.substring(preEntryId.length() - 5);
    }

    /**
     * 获取代理委托编号
     * */
    public static String getAgentSeqNo(String ediNo) {
        return "agentSeqNo000" + ediNo.substring(ediNo.length() - 5);
    }

}
