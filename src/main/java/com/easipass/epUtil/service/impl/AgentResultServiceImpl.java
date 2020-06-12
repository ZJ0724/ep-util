package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.result.AgentResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.AgentResultService;
import org.springframework.stereotype.Service;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response upload(String ediNo, ResultDTO resultDTO) {
        Result result = new AgentResult(ediNo, resultDTO);

        resultModule.upload(result);

        return Response.returnTrue();
    }

}