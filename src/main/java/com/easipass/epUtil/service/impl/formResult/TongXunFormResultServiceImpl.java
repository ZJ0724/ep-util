package com.easipass.epUtil.service.impl.formResult;

import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.result.FormResult;
import com.easipass.epUtil.entity.result.formResult.TongXunFormResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.FormResultService;
import org.springframework.stereotype.Service;

@Service("TongXunFormResultServiceImpl")
public class TongXunFormResultServiceImpl implements FormResultService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response upload(String ediNo, ResultDTO formResultDTO) {
        FormResult formResult = new TongXunFormResult(ediNo, formResultDTO);

        resultModule.upload(formResult);

        return Response.returnTrue();
    }

}