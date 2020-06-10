package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.ConfigService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PostMapping("set")
    public Response set(@RequestBody Map<String, Object> data) {
        return configService.set(data);
    }

    @GetMapping("get")
    public Response get() {
        return configService.get();
    }

}