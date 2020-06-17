package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.DaKaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/daKa")
public class DaKaController {

    @Resource
    private DaKaService daKaService;

    @PostMapping("start")
    public Response start() {
        return daKaService.start();
    }

    @PostMapping("stop")
    public Response stop() {
        return daKaService.stop();
    }

    @GetMapping("getLog")
    public Response getLog() {
        return daKaService.getLog();
    }

    @GetMapping("getStatus")
    public Response getStatus() {
        return daKaService.getStatus();
    }

    @PostMapping("manualDaKa")
    public Response manualDaKa() {
        return daKaService.manualDaKa();
    }

}