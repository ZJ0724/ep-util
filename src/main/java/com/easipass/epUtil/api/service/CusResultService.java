package com.easipass.epUtil.api.service;

import com.easipass.epUtil.core.entity.Response;
import com.easipass.epUtil.core.entity.cusResult.formCusResult.TongXunFormCusResult;
import com.easipass.epUtil.core.entity.dto.CusResultDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 回执服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusResult")
public class CusResultService {

    /**
     * 上传报关单通讯回执
     *
     * @param cusResultDTO 请求体数据
     * @param ediNo ediNo
     *
     * @return 响应
     */
    @PostMapping("formCusResult/UploadTongXun")
    public Response formCusResultUploadTongXun(@RequestBody CusResultDTO cusResultDTO, @RequestParam("ediNo") String ediNo) {
        return new TongXunFormCusResult(cusResultDTO, ediNo).upload();
    }

}