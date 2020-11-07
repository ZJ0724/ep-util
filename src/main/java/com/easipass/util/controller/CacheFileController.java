package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.service.CacheFileService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * CacheFileController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cacheFile")
public class CacheFileController {

    /**
     * cacheFileService
     * */
    private final CacheFileService cacheFileService = new CacheFileService();

    /**
     * 获取所有缓存文件
     *
     * @return 响应
     * */
    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(cacheFileService.getAll());
    }

    /**
     * 删除缓存文件
     *
     * @param requestBody 请求参数
     *
     * @return 响应
     * */
    @PostMapping("delete")
    public Response delete(@RequestBody Map<String, String> requestBody) {
        cacheFileService.delete(requestBody.get("fileName"));
        return Response.returnTrue("删除成功");
    }

}