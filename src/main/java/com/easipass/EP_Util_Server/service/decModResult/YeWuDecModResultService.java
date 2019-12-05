package com.easipass.EP_Util_Server.service.decModResult;

import com.easipass.EP_Util_Server.entity.decModResult.YeWuDecModResultData;
import org.springframework.stereotype.Service;

@Service
public interface YeWuDecModResultService {

    /**
     * 上传回执
     * */
    public void upload(YeWuDecModResultData yeWuDecModResultData, String fileName);

}