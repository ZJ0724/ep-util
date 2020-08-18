package com.easipass.util.api.service;

import com.easipass.util.core.DTO.cusResult.CusResultDTO;
import com.easipass.util.core.DTO.cusResult.TongXunFormCusResultDTO;
import com.easipass.util.core.DTO.cusResult.YeWuFormCusResultDTO;
import com.easipass.util.entity.Response;
import com.easipass.util.core.cusResult.AgentCusResult;
import com.easipass.util.core.cusResult.decModCusResult.QPDecModCusResult;
import com.easipass.util.core.cusResult.decModCusResult.YeWuDecModCusResult;
import com.easipass.util.core.cusResult.formCusResult.TongXunFormCusResult;
import com.easipass.util.core.cusResult.formCusResult.YeWuFormCusResult;
import com.zj0724.util.springboot.parameterCheck.NotNull;
import com.zj0724.util.springboot.parameterCheck.OpenParameterCheck;
import org.springframework.web.bind.annotation.*;

/**
 * 回执服务
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusResult")
@OpenParameterCheck
public class CusResultService {

    /**
     * 报关单回执请求路径
     * */
    private static final String FORM_CUS_RESULT = "formCusResult/";

    /**
     * 修撤单回执请求路径
     * */
    private static final String DEC_MOD_RESULT = "decModCusResult/";

    /**
     * 上传报关单通讯回执
     *
     * @param tongXunFormCusResultDTO tongXunFormCusResultDTO
     *
     * @return 响应
     */
    @PostMapping(FORM_CUS_RESULT + "/uploadTongXun")
    public Response formCusResultUploadTongXun(@RequestBody @NotNull TongXunFormCusResultDTO tongXunFormCusResultDTO) {
        TongXunFormCusResult tongXunFormCusResult = new TongXunFormCusResult(tongXunFormCusResultDTO);

        tongXunFormCusResult.upload();

        return Response.returnTrue("上传成功\nseqNo: " + tongXunFormCusResult.getSeqNo());
    }

    /**
     * 上传报关单业务回执
     *
     * @param yeWuFormCusResultDTO yeWuFormCusResultDTO
     *
     * @return 响应
     * */
    @PostMapping(FORM_CUS_RESULT + "uploadYeWu")
    public Response formCusResultUploadYeWu(@RequestBody @NotNull YeWuFormCusResultDTO yeWuFormCusResultDTO) {
        YeWuFormCusResult yeWuFormCusResult = new YeWuFormCusResult(yeWuFormCusResultDTO);

        yeWuFormCusResult.uploadAll();

        return Response.returnTrue("上传成功\nseqNo: " + yeWuFormCusResult.getSeqNo() + "\npreEntryId: " + yeWuFormCusResult.getPreEntryId());
    }

    /**
     * 一次性上传报关单回执
     *
     * @return 响应
     * */
    @Deprecated
    @PostMapping(FORM_CUS_RESULT + "disposableUpload")
    public Response formCusResultDisposableUpload() {
        return Response.returnFalse("已弃用");
    }

    /**
     * 上传修撤单QP回执
     *
     * @param cusResultDTO 请求体数据
     * @param preEntryId 报关单号
     *
     * @return 响应
     * */
    @PostMapping(DEC_MOD_RESULT + "uploadQP")
    public Response decModCusResultUploadQP(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("preEntryId") @NotNull String preEntryId) {
        new QPDecModCusResult(cusResultDTO, preEntryId).upload();

        return Response.returnTrue();
    }

    /**
     * 上传修撤单业务回执
     *
     * @param cusResultDTO 请求体数据
     * @param preEntryId 报关单号
     *
     * @return 响应
     * */
    @PostMapping(DEC_MOD_RESULT + "uploadYeWu")
    public Response decModCusResultUploadYeWu(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("preEntryId") @NotNull String preEntryId) {
        new YeWuDecModCusResult(cusResultDTO, preEntryId).upload();

        return Response.returnTrue();
    }

    /**
     * 一次性上传修撤单回执
     *
     * @return 响应
     * */
    @Deprecated
    @PostMapping(DEC_MOD_RESULT + "disposableUpload")
    public Response decModCusResultDisposableUpload() {
        return Response.returnFalse("已弃用");
    }

    /**
     * 上传代理委托回执
     *
     * @param cusResultDTO 请求体数据
     * @param ediNo ediNo
     *
     * @return 响应
     * */
    @PostMapping("agentCusResult/upload")
    public Response agentCusResultUpload(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("ediNo") @NotNull String ediNo) {
        new AgentCusResult(cusResultDTO, ediNo).upload();

        return Response.returnTrue();
    }

}