package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.service.DecModResultService;
import com.zj0724.springbootUtil.annotation.Length;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/decModResult")
@ResponseBody
public class DecModResultController {

    @Resource
    @Qualifier("QPDecModResultServiceImpl")
    private DecModResultService QPDecModResultService;

    @Resource
    @Qualifier("YeWuDecModResultServiceImpl")
    private DecModResultService YeWuDecModResultService;

    @RequestMapping(value = "upload/QP", method = RequestMethod.POST)
    public Response uploadQP(@RequestParam @Length(min = 18) String preEntryId, @RequestBody ResultDTO resultDTO) {
        return QPDecModResultService.upload(preEntryId, resultDTO);
    }

    @RequestMapping(value = "upload/yeWu", method = RequestMethod.POST)
    public Response uploadYeWu(@RequestParam @Length(min = 18) String preEntryId, @RequestBody ResultDTO resultDTO) {
        return YeWuDecModResultService.upload(preEntryId, resultDTO);
    }

}
