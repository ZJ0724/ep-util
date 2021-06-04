package com.easipass.util.controller;

import com.easipass.util.entity.CusResult;
import com.easipass.util.service.CusResultService;
import com.zj0724.common.exception.InfoException;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/cusResult")
public final class CusResultController {

    @Resource
    private CusResultService cusResultService;

    @PostMapping("uploadCustomsDeclaration")
    public Response uploadCustomsDeclaration(@RequestBody(required = false) Map<String, Object> requestBody) {
        if (requestBody == null) {
            throw new InfoException("请求参数缺失");
        }
        String customsDeclarationNumber = requestBody.get("customsDeclarationNumber") == null ? null : requestBody.get("customsDeclarationNumber").toString();
        CusResult tongXunCusResult = requestBody.get("tongXunCusResult") == null ? null : ObjectUtil.parse(requestBody.get("tongXunCusResult"), CusResult.class);
        CusResult yeWuCusResult = requestBody.get("yeWuCusResult") == null ? null : ObjectUtil.parse(requestBody.get("yeWuCusResult"), CusResult.class);

        cusResultService.uploadCustomsDeclaration(customsDeclarationNumber, tongXunCusResult, yeWuCusResult);
        return Response.returnTrue();
    }

    @PostMapping("uploadAgentResult")
    public Response uploadAgentResult(@RequestBody(required = false) Map<String, Object> requestBody) {
        String customsDeclarationNumber = requestBody.get("customsDeclarationNumber") == null ? null : requestBody.get("customsDeclarationNumber").toString();
        CusResult cusResult = requestBody.get("cusResult") == null ? null : ObjectUtil.parse(requestBody.get("cusResult"), CusResult.class);

        cusResultService.uploadAgentResult(customsDeclarationNumber, cusResult);

        return Response.returnTrue();
    }

}