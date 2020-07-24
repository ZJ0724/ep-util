package com.easipass.epUtil.api.service;

import com.easipass.epUtil.entity.CusMessage;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.cusMessage.FormCusMessage;
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
@RequestMapping(BaseServiceApi.URL + "cusMessage")
@SkipCheck
public class CusMessageServiceApi {

    /**
     * 上传报关单报文
     *
     * @param multipartFile 前端传过来的报文
     *
     * @return 响应结果
     * */
    @PostMapping("formCusMessageUpload")
    public Response upload(@RequestParam("formCusMessage") MultipartFile multipartFile) {
        CusMessage cusMessage = new FormCusMessage(multipartFile);

        cusMessage.push();

        return Response.returnTrue(cusMessage.getId());
    }

}