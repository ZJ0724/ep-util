package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.result.formResult.YeWuFormResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.FormResultService;
import org.springframework.stereotype.Service;

@Service("YeWuFormResultServiceImpl")
public class YeWuFormResultServiceImpl implements FormResultService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO) {
        YeWuFormResult yeWuFormResult = new YeWuFormResult(ediNo, formResultDTO);

        resultModule.upload(yeWuFormResult);

        return Response.returnTrue();
    }

}