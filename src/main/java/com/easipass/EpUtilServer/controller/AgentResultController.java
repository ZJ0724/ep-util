package com.easipass.EpUtilServer.controller;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.service.AgentResultService;
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
    public Response upload(@RequestParam String ediNo, @RequestBody ResultDTO resultDTO) {
        return agentResultService.upload(ediNo, resultDTO);
    }

}
