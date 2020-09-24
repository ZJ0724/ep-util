package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.service.SystemService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "system")
public class SystemController {

    /**
     * 获取错误日志
     *
     * @return Response
     * */
    @GetMapping("getErrorLog")
    public Response getErrorLog() {
        return Response.returnTrue(new SystemService().getErrorLog());
    }

    /**
     * 清空错误日志
     *
     * @return Response
     * */
    @PostMapping("cleanErrorLog")
    public Response cleanErrorLog() {
        new SystemService().cleanErrorLog();
        return Response.returnTrue();
    }

}