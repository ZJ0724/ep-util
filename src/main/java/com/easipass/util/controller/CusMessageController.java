package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.service.CusMessageService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * CusMessageController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusMessage")
public class CusMessageController {

    @Resource
    private CusMessageService cusMessageService;

    /**
     * formCusMessageComparison
     *
     * @param multipartFile multipartFile
     *
     * @return Response
     * */
    @PostMapping("formCusMessageComparison")
    public Response formCusMessageComparison(@RequestParam("formCusMessage") MultipartFile multipartFile) {
        InputStream inputStream;
        CusMessageService.ComparisonMessage comparisonMessage;
        try {
            inputStream = multipartFile.getInputStream();
            comparisonMessage = cusMessageService.formComparison(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        if (comparisonMessage.isFlag()) {
            return Response.returnTrue(comparisonMessage.getMessages());
        } else {
            return Response.returnFalse(comparisonMessage.getMessages());
        }
    }

    /**
     * 比对修撤单
     *
     * @param multipartFile multipartFile
     *
     * @return Response
     * */
    @PostMapping("decModCusMessageComparison")
    public Response decModCusMessageComparison(@RequestParam("decModCusMessage") MultipartFile multipartFile) {
        InputStream inputStream;
        CusMessageService.ComparisonMessage comparisonMessage;
        try {
            inputStream = multipartFile.getInputStream();
            comparisonMessage = cusMessageService.decModComparison(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new ErrorException(e.getMessage());
        }

        if (comparisonMessage.isFlag()) {
            return Response.returnTrue(comparisonMessage.getMessages());
        } else {
            return Response.returnFalse(comparisonMessage.getMessages());
        }
    }

}