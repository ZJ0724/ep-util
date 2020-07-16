package com.easipass.epUtil.api.service;

import com.easipass.epUtil.core.entity.CusFile;
import com.easipass.epUtil.core.entity.Response;
import com.zj0724.springbootUtil.annotation.SkipCheck;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 报文服务Api
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusFile")
@SkipCheck
public class CusFileServiceApi {

    /**
     * 上传报文
     *
     * @param multipartFile 前端传过来的报文
     *
     * @return 响应结果
     * */
    @PostMapping("upload")
    public Response upload(@RequestParam("cusFile") MultipartFile multipartFile) {
        String id = CusFile.addCusFile(multipartFile);

        return Response.returnTrue(id);
    }

}