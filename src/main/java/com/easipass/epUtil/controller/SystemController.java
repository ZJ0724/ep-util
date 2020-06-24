package com.easipass.epUtil.controller;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.service.SystemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 系统模块控制层
 *
 * @author ZJ
 * */
@RestController
@RequestMapping("system")
public class SystemController {

    /** 系统服务 */
    @Resource
    private SystemService systemService;

    @GetMapping("getVersion")
    public Response getVersion() {
        return systemService.getVersion();
    }

}