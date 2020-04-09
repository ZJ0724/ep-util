package com.easipass.EpUtilServer.controller;

import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.service.DecModResultService;
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
    private DecModResultService QPDecMOdResult;

    @Resource
    @Qualifier("YeWuDecModResultServiceImpl")
    private DecModResultService YeWuDecMOdResult;

    /**
     * 设置文件名
     * */
    @RequestMapping(value = "setFileName", method = RequestMethod.POST)
    public Response setFileName(@RequestParam String preEntryId) {
        return QPDecMOdResult.setFileName(preEntryId);
    }

    /**
     * 上传QP回执
     * */
    @RequestMapping(value = "upload/QP", method = RequestMethod.POST)
    public Response uploadQP(@RequestParam String preEntryId, @RequestBody ResultDTO resultDTO) {
        return QPDecMOdResult.upload(preEntryId, resultDTO);
    }

    /**
     * 上传业务回执
     * */
    @RequestMapping(value = "upload/yeWu", method = RequestMethod.POST)
    public Response uploadYeWu(@RequestParam String preEntryId, @RequestBody ResultDTO resultDTO) {
        return YeWuDecMOdResult.upload(preEntryId, resultDTO);
    }

}
