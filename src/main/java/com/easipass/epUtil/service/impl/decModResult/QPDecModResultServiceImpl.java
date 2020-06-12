package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.result.decModResult.QPDecModResult;
import com.easipass.epUtil.module.ResultModule;
import com.easipass.epUtil.service.DecModResultService;
import org.springframework.stereotype.Service;

@Service("QPDecModResultServiceImpl")
public class QPDecModResultServiceImpl implements DecModResultService {

    /** 回执模块 */
    private final ResultModule resultModule = ResultModule.getResultModule();

    @Override
    public Response upload(String preEntryId, ResultDTO resultDTO) {
        Result result = new QPDecModResult(preEntryId, resultDTO);

        resultModule.upload(result);

        return Response.returnTrue();
    }

}
