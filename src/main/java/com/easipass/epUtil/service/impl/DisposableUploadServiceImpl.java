package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.result.decModResult.QPDecModResult;
import com.easipass.epUtil.entity.result.decModResult.YeWuDecModResult;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.entity.result.formResult.YeWuFormResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.DisposableUploadService;
import org.springframework.stereotype.Service;

@Service
public class DisposableUploadServiceImpl implements DisposableUploadService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response formResult(String ediNo, ResultDTO formResultDTO) {
        // 通讯回执
        Result tongXun = new TongXunFormResult(ediNo, new ResultDTO("0", "通讯回执上传成功"));

        // 业务回执
        Result yeWu = new YeWuFormResult(ediNo, formResultDTO);

        resultModule.DisposableUpload(tongXun, yeWu);

        return Response.returnTrue();
    }

    @Override
    public Response decModResult(String preEntryId, ResultDTO formResultDTO) {
        // 通讯回执
        Result QP = new QPDecModResult(preEntryId, new ResultDTO("0", "QP回执上传成功"));

        // 业务回执
        Result yeWu = new YeWuDecModResult(preEntryId, formResultDTO);

        resultModule.DisposableUpload(QP, yeWu);

        return Response.returnTrue();
    }

}