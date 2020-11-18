package com.easipass.util.core.service;

import com.easipass.util.core.ChromeDriver;
import com.easipass.util.core.config.CusResultConfig;
import com.easipass.util.core.entity.AgentCusResultNew;
import com.easipass.util.core.sftp.CusResultUploadSftp;

/**
 * 报关单服务
 *
 * @author ZJ
 * */
public final class CusResultService {

    /**
     * 上传新代理委托回执
     *
     * @param agentCusResultNew 回执
     * */
    public void uploadNewAgentCusResult(AgentCusResultNew agentCusResultNew) {
        CusResultUploadSftp.myUpload(CusResultConfig.NEW_AGENT_CUS_RESULT_UPLOAD_PATH, agentCusResultNew.getFileName(), agentCusResultNew.getData());
        ChromeDriver.swgdAgentRecvNew();
    }

}