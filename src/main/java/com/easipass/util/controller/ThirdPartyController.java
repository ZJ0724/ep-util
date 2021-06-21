package com.easipass.util.controller;

import com.easipass.util.service.ThirdPartyService;
import com.zj0724.common.util.MapUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/thirdParty")
public final class ThirdPartyController {

    @Resource
    private ThirdPartyService thirdPartyService;

    @PostMapping("send")
    @SuppressWarnings("unchecked")
    public Response send(@RequestBody(required = false) Map<String, Object> requestBody) {
        return Response.returnTrue(thirdPartyService.send(
                MapUtil.getValue(requestBody, "userCode", String.class),
                MapUtil.getValue(requestBody, "url", String.class),
                (Map<String, String>) MapUtil.getValue(requestBody, "requestHeader", Map.class),
                MapUtil.getValue(requestBody, "requestData", String.class)));
    }

    @GetMapping("getUsers")
    public Response getUsers() {
        return Response.returnTrue(thirdPartyService.getUsers());
    }

}