package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.service.ParamDbService;
import com.easipass.util.core.service.TaskRunService;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
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
     * mdb导入比对
     *
     * @param groupName     组名
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("mdbImportComparator")
    public Response mdbImportComparator(@RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
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

        new TaskRunService("导入比对：" + groupName) {
            @Override
            public String run() {
                return new ParamDbService().mdbImportComparator(groupName, file.getAbsolutePath(), true).toString();
            }
        }.start();

        return Response.returnTrue("已放入后台进行比对");
    }

    /**
     * mdb导出比对
     *
     * @param groupName     组名
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("mdbExportComparator")
    public Response mdbExportComparator(@RequestParam(value = "groupName", required = false) String groupName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
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

        new TaskRunService("导出比对：" + groupName) {
            @Override
            public String run() {
                return new ParamDbService().mdbExportComparator(groupName, file.getAbsolutePath(), true).toString();
            }
        }.start();

        return Response.returnTrue("已放入后台进行比对");
    }

    /**
     * excel导入比对
     *
     * @param tableName     表名
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("excelImportComparator")
    public Response excelImportComparator(@RequestParam(value = "tableName", required = false) String tableName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
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

        new TaskRunService("excel导入比对：" + tableName) {
            @Override
            public String run() {
                return new ParamDbService().excelImportComparator(tableName, file.getAbsolutePath(), true).toString();
            }
        }.start();

        return Response.returnTrue("已放入后台进行比对");
    }

    /**
     * excel导入
     *
     * @param tableName     表名
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("excelImport")
    public Response excelImport(@RequestParam(value = "tableName", required = false) String tableName, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
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

        new TaskRunService("excel导入：" + tableName) {
            @Override
            public String run() {
                return new ParamDbService().excelImport(tableName, file.getAbsolutePath(), true).toString();
            }
        }.start();

        return Response.returnTrue("已放入后台进行导入");
    }

}