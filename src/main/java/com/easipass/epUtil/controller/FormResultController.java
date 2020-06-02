package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.FormResultService;
import com.zj0724.springbootUtil.annotation.Length;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/formResult")
@ResponseBody
public class FormResultController {

    @Resource
    @Qualifier("TongXunFormResultServiceImpl")
    private FormResultService tongXunFormResultService;

    @Resource
    @Qualifier("YeWuFormResultServiceImpl")
    private FormResultService yeWuFormResultService;

    @Resource
    @Qualifier("BaseFormResultServiceImpl")
    private FormResultService baseFormResultService;


    @RequestMapping(value = "upload/tongXun", method = RequestMethod.POST)
    public Response uploadTongXun(@RequestParam @Length(min = 18) String ediNo, @RequestBody ResultDTO formResultDTO) {
        return tongXunFormResultService.upload(ediNo, formResultDTO);
    }

    @RequestMapping(value = "upload/yeWu", method = RequestMethod.POST)
    public Response uploadYeWu(@RequestParam @Length(min = 18) String ediNo, @RequestBody ResultDTO formResultDTO) {
        return yeWuFormResultService.upload(ediNo, formResultDTO);
    }

    @RequestMapping(value = "disposableUpload", method = RequestMethod.POST)
    public Response disposableUpload(@RequestParam @Length(min = 18) String ediNo, @RequestBody ResultDTO formResultDTO) {
        return baseFormResultService.disposableUpload(ediNo, formResultDTO);
    }

}