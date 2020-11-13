package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.config.SWGDPARAFileConfig;
import com.easipass.util.core.entity.DatabaseInfo;
import com.easipass.util.core.service.SystemService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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
     * @param requestBody requestBody
     *
     * @return Response
     * */
    @PostMapping("cleanErrorLog")
    public Response cleanErrorLog(@RequestBody Map<String, String> requestBody) {
        new SystemService().cleanErrorLog(requestBody.get("key"));
        return Response.returnTrue();
    }

    /**
     * 辣鸡回收
     *
     * @return Response
     * */
    @PostMapping("gc")
    public Response gc() {
        System.gc();
        return Response.returnTrue();
    }

    /**
     * 获取SWGDPARA数据库配置
     *
     * @return SWGDPARA数据库配置
     * */
    @GetMapping("getSWGDPARAConfig")
    public Response getSWGDPARAConfig() {
        return Response.returnTrue(new DatabaseInfo(SWGDPARAFileConfig.currentFile));
    }

}