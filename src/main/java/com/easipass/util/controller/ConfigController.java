package com.easipass.util.controller;

import com.easipass.util.entity.po.ConfigPO;
import com.easipass.util.service.ConfigService;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/config")
public final class ConfigController {

    @Resource
    private ConfigService configService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(configService.getAll());
    }

    @PostMapping("save")
    public Response save(@RequestBody(required = false) Map<String, Object> requestBody) {
        configService.save(ObjectUtil.parse(requestBody, ConfigPO.class));
        return Response.returnTrue();
    }

}