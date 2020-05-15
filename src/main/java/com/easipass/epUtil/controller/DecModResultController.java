package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.service.DecModResultService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/decModResult")
@ResponseBody
public class DecModResultController {

    @Resource
    @Qualifier("BaseDecModResultServiceImpl")
    private DecModResultService baseDecModResultService;

    @Resource
    @Qualifier("QPDecModResultServiceImpl")
    private DecModResultService QPDecModResultService;

    @Resource
    @Qualifier("YeWuDecModResultServiceImpl")
    private DecModResultService YeWuDecModResultService;

    @RequestMapping(value = "setFileName", method = RequestMethod.POST)
    public Response setFileName(@RequestParam String preEntryId) {
        return baseDecModResultService.setFileName(preEntryId);
    }

    @RequestMapping(value = "upload/QP", method = RequestMethod.POST)
    public Response uploadQP(@RequestParam String preEntryId, @RequestBody ResultDTO resultDTO) {
        return QPDecModResultService.upload(preEntryId, resultDTO);
    }

    @RequestMapping(value = "upload/yeWu", method = RequestMethod.POST)
    public Response uploadYeWu(@RequestParam String preEntryId, @RequestBody ResultDTO resultDTO) {
        return YeWuDecModResultService.upload(preEntryId, resultDTO);
    }

}
