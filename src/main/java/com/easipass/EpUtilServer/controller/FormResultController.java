package com.easipass.EpUtilServer.controller;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.service.FormResultService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/formResult")
@ResponseBody
public class FormResultController {

    @Resource
    @Qualifier("tongXun")
    private FormResultService tongXun;

    @Resource
    @Qualifier("yeWu")
    private FormResultService yeWu;

    /**
     * 上传通讯回执
     * */
    @RequestMapping(value = "upload/tongXun", method = RequestMethod.POST)
    public Response uploadTongXun(@RequestParam String ediNo, @RequestBody ResultDTO formResultDTO) {
        return tongXun.upload(ediNo, formResultDTO, false, null, null);
    }

    /**
     * 上传业务回执
     * */
    @RequestMapping(value = "upload/yeWu", method = RequestMethod.POST)
    public Response uploadYeWu(@RequestParam String ediNo, @RequestBody ResultDTO formResultDTO) {
        return yeWu.upload(ediNo, formResultDTO, false, null, null);
    }

    /**
     * 一次性上传回执
     * */
    @RequestMapping(value = "disposableUpload", method = RequestMethod.POST)
    public Response disposableUpload(@RequestParam String ediNo, @RequestBody ResultDTO formResultDTO) {
        return yeWu.disposableUpload(ediNo, formResultDTO);
    }

    /**
     * 批量上传
     * */
    @RequestMapping(value = "uploadMore", method = RequestMethod.POST)
    public Response uploadMore(@RequestParam String ediNo, @RequestBody List<ResultDTO> resultDTOS) {
        return yeWu.uploadMore(ediNo, resultDTOS);
    }

}
