package com.easipass.epUtil.api.service;

import com.easipass.epUtil.entity.CusResult;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.cusResult.AgentCusResult;
import com.easipass.epUtil.entity.cusResult.decModCusResult.QPDecModCusResult;
import com.easipass.epUtil.entity.cusResult.decModCusResult.YeWuDecModCusResult;
import com.easipass.epUtil.entity.cusResult.formCusResult.TongXunFormCusResult;
import com.easipass.epUtil.entity.cusResult.formCusResult.YeWuFormCusResult;
import com.easipass.epUtil.entity.DTO.CusResultDTO;
import com.zj0724.springbootUtil.annotation.NotNull;
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
     * @param cusResultDTO 请求体数据
     * @param ediNo ediNo
     *
     * @return 响应
     */
    @PostMapping(FORM_CUS_RESULT + "/tongXunUpload")
    public Response formCusResultUploadTongXun(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("ediNo") @NotNull String ediNo) {
        return new TongXunFormCusResult(cusResultDTO, ediNo).upload();
    }

    /**
     * 上传报关单业务回执
     *
     * @param cusResultDTO 请求体数据
     * @param ediNo ediNo
     *
     * @return 响应
     * */
    @PostMapping(FORM_CUS_RESULT + "yeWuUpload")
    public Response formCusResultUploadYeWu(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("ediNo") @NotNull String ediNo) {
        return new YeWuFormCusResult(cusResultDTO, ediNo).upload();
    }

    /**
     * 一次性上传报关单回执
     *
     * @param cusResultDTO 请求体数据
     * @param ediNo ediNo
     *
     * @return 响应
     * */
    @PostMapping(FORM_CUS_RESULT + "disposableUpload")
    public Response formCusResultDisposableUpload(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("ediNo") @NotNull String ediNo) {
        CusResult tongXun = new TongXunFormCusResult(new CusResultDTO("0", "通讯回执上传成功"), ediNo);
        CusResult yeWu = new YeWuFormCusResult(cusResultDTO, ediNo);

        return CusResult.disposableUpload(tongXun, yeWu);
    }

    /**
     * 上传修撤单QP回执
     *
     * @param cusResultDTO 请求体数据
     * @param preEntryId 报关单号
     *
     * @return 响应
     * */
    @PostMapping(DEC_MOD_RESULT + "QPUpload")
    public Response decModCusResultQPUpload(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("preEntryId") @NotNull String preEntryId) {
        return new QPDecModCusResult(cusResultDTO, preEntryId).upload();
    }

    /**
     * 上传修撤单业务回执
     *
     * @param cusResultDTO 请求体数据
     * @param preEntryId 报关单号
     *
     * @return 响应
     * */
    @PostMapping(DEC_MOD_RESULT + "yeWuUpload")
    public Response decModCusResultYeWuUpload(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("preEntryId") @NotNull String preEntryId) {
        return new YeWuDecModCusResult(cusResultDTO, preEntryId).upload();
    }

    /**
     * 一次性上传修撤单回执
     *
     * @param cusResultDTO 请求体数据
     * @param preEntryId 报关单号
     *
     * @return 响应
     * */
    @PostMapping(DEC_MOD_RESULT + "disposableUpload")
    public Response decModCusResultDisposableUpload(@RequestBody @NotNull CusResultDTO cusResultDTO, @RequestParam("preEntryId") @NotNull String preEntryId) {
        CusResult QP = new QPDecModCusResult(new CusResultDTO("0", "QP回执上传成功"), preEntryId);
        CusResult yeWu = new YeWuDecModCusResult(cusResultDTO, preEntryId);

        return CusResult.disposableUpload(QP, yeWu);
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
        return new AgentCusResult(cusResultDTO, ediNo).upload();
    }

}