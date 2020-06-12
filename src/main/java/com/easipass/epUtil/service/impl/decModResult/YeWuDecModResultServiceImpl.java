package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.entity.Result;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.result.decModResult.YeWuDecModResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.DecModResultService;
import org.springframework.stereotype.Service;

@Service("YeWuDecModResultServiceImpl")
public class YeWuDecModResultServiceImpl implements DecModResultService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        Result result = new YeWuDecModResult(preEntryId, resultDTO);

        resultModule.upload(result);

        return Response.returnTrue();
    }

}