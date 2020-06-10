package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.ConfigService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PostMapping("set")
    public Response set(@RequestBody String data) {
        return configService.set(data);
    }

    @GetMapping("get")
    public Response get() {
        return configService.get();
    }

}