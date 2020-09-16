package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.service.CusMessageService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

/**
 * CusMessageController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusMessage")
public final class CusMessageController {

    @Resource
    private CusMessageService cusMessageService;

    /**
     * formComparison
     *
     * @param multipartFile multipartFile
     *
     * @return Response
     * */
    @PostMapping("formComparison")
    public Response formComparison(@RequestParam("formCusMessage") MultipartFile multipartFile) {
        return Response.returnTrue(cusMessageService.formComparison(multipartFile).toString());
    }

}