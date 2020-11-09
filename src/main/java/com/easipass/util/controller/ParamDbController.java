package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.config.Project;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.service.CacheFileService;
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
     * cacheFileService
     * */
    private final CacheFileService cacheFileService = new CacheFileService();

    /**
     * paramDbService
     * */
    private final ParamDbService paramDbService = new ParamDbService();

    /**
     * mdb导入比对
     *
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("mdbImportComparator")
    public Response mdbImportComparator(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile == null) {
            return Response.returnFalse("请选择文件");
        }

        String fileName = multipartFile.getOriginalFilename();
        String newFileName;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            newFileName = cacheFileService.add(fileName, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        new TaskRunService("mdb导入比对：" + fileName) {
            @Override
            public String run() {
                return paramDbService.mdbImportComparator(new File(Project.CACHE_PATH, newFileName).getAbsolutePath()).toString();
            }

            @Override
            public void afterRun() {
                cacheFileService.delete(newFileName);
            }
        }.start();

        return Response.returnTrue("已放入后台进行比对");
    }

    /**
     * mdb导出比对
     *
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("mdbExportComparator")
    public Response mdbExportComparator(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile == null) {
            return Response.returnFalse("请选择文件");
        }

        String fileName = multipartFile.getOriginalFilename();
        String newFileName;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            newFileName = cacheFileService.add(fileName, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        new TaskRunService("mdb导出比对：" + fileName) {
            @Override
            public String run() {
                return paramDbService.mdbExportComparator(new File(Project.CACHE_PATH, newFileName).getAbsolutePath()).toString();
            }

            @Override
            public void afterRun() {
                cacheFileService.delete(newFileName);
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

        if (tableName == null) {
            return Response.returnFalse("请输入表名");
        }

        String fileName = multipartFile.getOriginalFilename();
        String newFileName;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            newFileName = cacheFileService.add(fileName, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        new TaskRunService("mdb导出比对：" + fileName) {
            @Override
            public String run() {
                return paramDbService.excelImportComparator(tableName, new File(Project.CACHE_PATH, newFileName).getAbsolutePath()).toString();
            }

            @Override
            public void afterRun() {
                cacheFileService.delete(newFileName);
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

    /**
     * mdb导入
     *
     * @param multipartFile 文件
     * @return Response
     */
    @PostMapping("mdbImport")
    public Response mdbImport(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
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

        new TaskRunService("mdb导入：" + file.getName()) {
            @Override
            public String run() {
                return new ParamDbService().mdbImport(file.getAbsolutePath(), true).toString();
            }
        }.start();

        return Response.returnTrue("已放入后台进行导入");
    }

}