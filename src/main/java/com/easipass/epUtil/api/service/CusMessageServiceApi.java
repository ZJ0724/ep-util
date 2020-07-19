package com.easipass.epUtil.api.service;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.cusMessage.FormCusMessage;
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
@RequestMapping(BaseServiceApi.URL + "cusMessage")
public class CusMessageServiceApi {

    /**
     * 上传报关单报文
     *
     * @param multipartFile 前端传过来的报文
     *
     * @return 响应结果
     * */
    @PostMapping("formCusMessageUpload")
    public Response upload(@RequestParam("cusFile") MultipartFile multipartFile) {
        String id = FormCusMessage.addFormCusMessage(multipartFile);

        return Response.returnTrue(id);
    }

}