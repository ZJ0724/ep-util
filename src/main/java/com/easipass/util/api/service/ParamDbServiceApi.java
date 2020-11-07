package com.easipass.util.api.service;

import com.easipass.util.core.config.Project;
import com.easipass.util.core.util.DateUtil;
import com.easipass.util.core.util.FileUtil;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * ParamDbServiceApi
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "paramDb")
public class ParamDbServiceApi {

    /**
     * 上传文件
     *
     * @param multipartFile multipartFile
     *
     * @return Response
     * */
    @PostMapping("upload")
    public Response upload(@RequestParam("mdb") MultipartFile multipartFile) {
        InputStream inputStream = null;
        Response response;

        try {
            inputStream = multipartFile.getInputStream();
            File file = new File(Project.CACHE_PATH, DateUtil.getTime() + "-" + multipartFile.getOriginalFilename());
            FileUtil.createFile(file, inputStream);
            response = Response.returnTrue(file.getName());
        } catch (IOException e) {
            response = Response.error(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                response = Response.error(e.getMessage());
            }
        }

        return response;
    }

}