package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.service.DisposableUploadService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("disposableUpload")
public class DisposableUploadController {

    @Resource
    private DisposableUploadService disposableUploadService;

    @PostMapping("formResult")
    public Response formResult(@RequestParam String ediNo, @RequestBody ResultDTO formResultDTO) {
        return disposableUploadService.formResult(ediNo, formResultDTO);
    }

    @PostMapping("decModResult")
    public Response decModResult(@RequestParam String preEntryId, @RequestBody ResultDTO formResultDTO) {
        return disposableUploadService.decModResult(preEntryId, formResultDTO);
    }

}