package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.service.DaKaService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DaKaController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "daKa2.0")
public class DaKaController {

    /**
     * 打卡服务
     * */
    private static final DaKaService DA_KA_SERVICE = new DaKaService();

    /**
     * 开启自动打卡
     *
     * @return Response
     * */
    @PostMapping("open")
    public Response open() {
        DA_KA_SERVICE.open();

        return Response.returnTrue();
    }

    /**
     * 关闭自动打卡
     *
     * @return Response
     * */
    @PostMapping("close")
    public Response close() {
        DA_KA_SERVICE.close();

        return Response.returnTrue();
    }

    /**
     * 手动打卡
     *
     * @return Response
     * */
    @PostMapping("manual")
    public Response manual() {
        DA_KA_SERVICE.manualDaKa();

        return Response.returnTrue("手动打卡完成");
    }

    /**
     * 获取自动打卡状态
     *
     * @return Response
     * */
    @GetMapping("getStatus")
    public Response getStatus() {
        return Response.returnTrue(DA_KA_SERVICE.getStatus());
    }

    /**
     * 获取日志
     *
     * @return Response
     * */
    @GetMapping("getLog")
    public Response getLog() {
        return Response.returnTrue(DA_KA_SERVICE.getLog());
    }

    /**
     * 清空日志
     *
     * @return Response
     * */
    @PostMapping("cleanLog")
    public Response cleanLog() {
        DA_KA_SERVICE.cleanLog();

        return Response.returnTrue();
    }

}