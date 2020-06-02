package com.easipass.epUtil.entity;

import com.easipass.epUtil.exception.OracleException;
import com.zj0724.springbootUtil.annotation.NotNull;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResultDTO {

    /**
     * 状态
     * */
    @NotNull
    private String channel;

    /**
     * 备注
     * */
    @NotNull
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
        String declPort;
        Oracle oracle = Oracle.getSWGDOracle();
        if (!oracle.connect()) {
            throw OracleException.getOracleException("数据库：" + oracle.getUrl() + "连接失败");
        }
        ResultSet resultSet = oracle.query("SELECT DECL_PORT FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});
        try {
            resultSet.next();
            declPort = resultSet.getString("DECL_PORT");
        } catch (SQLException e) {
            throw OracleException.getOracleException("未找到报关单数据");
        }
        oracle.close();
        return declPort + "000000000" + ediNo.substring(ediNo.length() - 5);
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

    @Override
    public String toString() {
        return "ResultDTO{" +
                "channel='" + channel + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

}