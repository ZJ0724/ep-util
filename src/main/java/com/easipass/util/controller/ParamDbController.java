package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.service.ParamDbService;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.core.util.StringUtil;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * ParamDbController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "paramDb2.0")
public class ParamDbController {

    /**
     * 导入比对
     *
     * @param groupName 组名
     * @param multipartFile 文件
     *
     * @return Response
     * */
    @PostMapping("importComparator")
    public Response importComparator(@RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (StringUtil.isEmpty(groupName)) {
            return Response.returnFalse("组名不能为空");
        }
        if (multipartFile == null) {
            return Response.returnFalse("请选择文件");
        }

        File file;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            file = new File(Project.CACHE_PATH, DateUtil.getTime() + "-" + multipartFile.getOriginalFilename());
            FileUtil.createFile(file, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        ParamDbService paramDbService = new ParamDbService();
        ParamDbService.Result result = paramDbService.importComparator(groupName, file.getAbsolutePath());

        FileUtil.delete(file);

        if (result.flag) {
            return Response.returnTrue(result.message);
        } else {
            return Response.returnFalse(result.message);
        }
    }

    /**
     * 导出比对
     *
     * @param groupName 组名
     * @param multipartFile 文件
     *
     * @return Response
     * */
    @PostMapping("exportComparator")
    public Response exportComparator(@RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (StringUtil.isEmpty(groupName)) {
            return Response.returnFalse("组名不能为空");
        }
        if (multipartFile == null) {
            return Response.returnFalse("请选择文件");
        }

        File file;

        try {
            InputStream inputStream = multipartFile.getInputStream();
            file = new File(Project.CACHE_PATH, DateUtil.getTime() + "-" + multipartFile.getOriginalFilename());
            FileUtil.createFile(file, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        ParamDbService paramDbService = new ParamDbService();
        ParamDbService.Result result = paramDbService.exportComparator(groupName, file.getAbsolutePath());

        FileUtil.delete(file);

        if (result.flag) {
            return Response.returnTrue(result.message);
        } else {
            return Response.returnFalse(result.message);
        }
    }

}