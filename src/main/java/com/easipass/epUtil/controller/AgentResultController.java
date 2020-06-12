package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.AgentResultService;
import com.zj0724.springbootUtil.annotation.Length;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/agentResult")
@ResponseBody
public class AgentResultController {

    @Resource
    private AgentResultService agentResultService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Response upload(@RequestParam @Length(min = 18) String ediNo, @RequestBody ResultDTO resultDTO) {
        return agentResultService.upload(ediNo, resultDTO);
    }

}