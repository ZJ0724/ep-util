package com.easipass.util.api.service;

import com.easipass.util.core.CusMessage;
import com.easipass.util.core.Response;
import com.easipass.util.core.cusMessage.DecModCusMessage;
import com.easipass.util.core.cusMessage.FormCusMessage;
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

    private static final String UPLOAD = "upload/";

    /**
     * 上传报关单报文
     *
     * @param multipartFile 前端传过来的报文
     *
     * @return 响应结果
     * */
    @PostMapping(UPLOAD + "formCusMessage")
    public Response uploadFormCusMessage(@RequestParam("formCusMessage") MultipartFile multipartFile) {
        CusMessage cusMessage = new FormCusMessage(multipartFile);

        CusMessage.push(cusMessage);

        return Response.returnTrue(cusMessage.getId());
    }

    /**
     * 上传修撤单报文
     *
     * @param multipartFile 前端传过来的报文
     *
     * @return 响应结果
     * */
    @PostMapping(UPLOAD + "decModCusMessage")
    public Response uploadDecModCusMessage(@RequestParam("decModCusMessage") MultipartFile multipartFile) {
        CusMessage cusMessage = new DecModCusMessage(multipartFile);

        CusMessage.push(cusMessage);

        return Response.returnTrue(cusMessage.getId());
    }

}