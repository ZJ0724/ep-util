package com.easipass.util.controller;

import com.easipass.util.entity.cusresult.CustomsDeclarationCusResult;
import com.easipass.util.service.CusResultService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping(BaseController.API + "/cusResult")
public final class CusResultController {

    @Resource
    private CusResultService cusResultService;

    @PostMapping("uploadCustomsDeclaration")
    public Response uploadCustomsDeclaration(@RequestBody(required = false) CustomsDeclarationCusResult customsDeclarationCusResult) {
        cusResultService.uploadCustomsDeclaration(customsDeclarationCusResult);
        return Response.returnTrue();
    }

}