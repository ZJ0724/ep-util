package com.easipass.EP_Util_Server.service;

import com.easipass.EP_Util_Server.entity.AgentResult;
import com.easipass.EP_Util_Server.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AgentResultService {

    /**
     * 上传代理委托回执
     * */
    Response upload(AgentResult agentResult);

}
