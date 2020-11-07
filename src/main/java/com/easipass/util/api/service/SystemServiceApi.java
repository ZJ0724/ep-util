package com.easipass.util.api.service;

import com.easipass.util.core.config.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.entity.Response;
import com.easipass.util.core.util.DateUtil;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return Response.returnTrue(Project.VERSION);
    }

    /**
     * 获取系统时间
     *
     * @return Response
     * */
    @GetMapping("getTime")
    public Response getTime() {
        return Response.returnTrue(DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + " " + DateUtil.getWeek());
    }

    /**
     * 缓存目录管理
     *
     * @return Response
     * */
    @GetMapping("getCache")
    public Response getCache() {
        File file = new File(Project.CACHE_PATH);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new ErrorException("创建缓存目录失败");
            }
        }

        File[] files = file.listFiles();
        List<String> fileList = new ArrayList<>();

        if (files == null) {
            throw new ErrorException("files is null");
        }

        for (File file1 : files) {
            if (file1.isFile()) {
                fileList.add(file1.getName());
            }
        }

        return Response.returnTrue(fileList);
    }

    /**
     * 删除缓存文件
     *
     * @param requestData 请求数据
     *
     * @return Response
     * */
    @PostMapping("deleteCacheFile")
    public Response deleteCacheFile(@RequestBody Map<String, String> requestData) {
        // 密码
        String password = requestData.get("password");

        if (StringUtil.isEmpty(password)) {
            return Response.returnFalse("请输入密码");
        }

        if (!password.equals("ZJ")) {
            return Response.returnFalse("密码错误");
        }

        String fileName = requestData.get("fileName");
        File file = new File(Project.CACHE_PATH, fileName);

        if (!file.exists()) {
            return Response.returnFalse("文件不存在");
        }

        if (!file.delete()) {
            throw new ErrorException("删除缓存文件失败");
        }

        return Response.returnTrue();
    }

}