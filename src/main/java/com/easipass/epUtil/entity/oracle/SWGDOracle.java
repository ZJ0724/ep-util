package com.easipass.epUtil.entity.oracle;

import com.easipass.epUtil.entity.Config;
import com.easipass.epUtil.entity.Oracle;
import com.easipass.epUtil.entity.config.Swgd;
import com.easipass.epUtil.exception.OracleException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SWGDOracle extends Oracle {

    /**
     * 构造函数
     */
    public SWGDOracle() {
        Swgd swgd = Config.getConfig().getSwgd();

        this.setUrl(swgd.getUrl());
        this.setPort(swgd.getPort());
        this.setSid(swgd.getSid());
        this.setUsername(swgd.getUsername());
        this.setPassword(swgd.getPassword());
    }

    /**
     * 查询declPort
     * */
    public String queryDeclPort(String ediNo) {
        String declPort;

        this.connect();

        ResultSet resultSet = this.query("SELECT DECL_PORT FROM SWGD.T_SWGD_FORM_HEAD WHERE EDI_NO = ?", new Object[]{ediNo});
        try {
            resultSet.next();
            declPort = resultSet.getString("DECL_PORT");
        } catch (SQLException e) {
            throw OracleException.queryError("未找到报关单数据");
        } finally {
            this.close();
        }

        return declPort;
    }

    /**
     * 设置修撤单文件名
     * */
    public void updateDecModFileName(String preEntryId, String fileName) {
        this.connect();
        this.update("UPDATE SWGD.T_SWGD_DECMOD_HEAD SET FILE_NAME = ? WHERE PRE_ENTRY_ID = ?", new Object[]{fileName, preEntryId});
        this.close();
    }

    /**
     * 查询AGENT_CODE
     * */
    public String queryAgentCode(String ediNo) {
        String agentCode;

        this.connect();
        ResultSet resultSet = this.query("SELECT AGENT_CODE FROM SWGD.T_SWGD_AGENT_LIST WHERE EDI_NO = ?", new Object[]{ediNo});
        try {
            resultSet.next();
            agentCode = resultSet.getString("AGENT_CODE");
        } catch (SQLException e) {
            throw OracleException.queryError("未找到数据");
        } finally {
            this.close();
        }

        return agentCode;
    }

}