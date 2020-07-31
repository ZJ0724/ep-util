package com.easipass.util.api.service;

import com.easipass.util.entity.Project;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务api
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "system")
public class SystemServiceApi {

    /**
     * 获取系统版本
     *
     * @return response
     * */
    @GetMapping("getVersion")
    public Response getVersion() {
        return Response.returnTrue(Project.getInstance().getVersion());
    }

}