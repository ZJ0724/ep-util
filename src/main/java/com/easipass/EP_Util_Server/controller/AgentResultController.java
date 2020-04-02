package com.easipass.EP_Util_Server.controller;

import com.easipass.EP_Util_Server.entity.AgentResult;
import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.service.AgentResultService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/agentResult")
@ResponseBody
public class AgentResultController {

    @Resource
    private AgentResultService agentResultService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Response upload(@RequestBody AgentResult agentResult) {
        return agentResultService.upload(agentResult);
    }

}
