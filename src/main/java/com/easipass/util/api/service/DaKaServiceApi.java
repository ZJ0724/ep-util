package com.easipass.util.api.service;

import com.easipass.util.core.DaKa;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打卡服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "daKa")
public class DaKaServiceApi {

    /**
     * 打卡
     * */
    private static final DaKa DA_KA = DaKa.getInstance();

    /**
     * 开启自动打卡
     *
     * @return 响应
     * */
    @PostMapping("openAutoDaKa")
    public Response openAutoDaKa() {
        DA_KA.OpenAutoDaKa();

        return Response.returnTrue();
    }

    /**
     * 关闭打卡
     *
     * @return 响应
     * */
    @PostMapping("closeAutoDaKa")
    public Response closeAutoDaKa() {
        DA_KA.closeAutoDaKa();

        return Response.returnTrue();
    }

    /**
     * 获取自动打卡状态
     *
     * @return 响应
     * */
    @GetMapping("getStatus")
    public Response getStatus() {
        return Response.returnTrue(DA_KA.getStatus());
    }
    /**
     * 清空日志
     *
     * @return 响应
     * */
    @PostMapping("cleanLog")
    public Response cleanLog() {
        DA_KA.cleanLog();

        return Response.returnTrue();
    }

    /**
     * 手动打卡
     *
     * @return 响应
     * */
    @PostMapping("manualKaKa")
    public Response manualKaKa() {
        DA_KA.manualKaKa();

        return Response.returnTrue();
    }

}