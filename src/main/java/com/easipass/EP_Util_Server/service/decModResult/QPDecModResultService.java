package com.easipass.EP_Util_Server.service.decModResult;

import com.easipass.EP_Util_Server.entity.decModResult.QPDecModResultData;
import org.springframework.stereotype.Service;

@Service
public interface QPDecModResultService {

    /**
     * 上传回执
     * */
    public void upload(QPDecModResultData qpDecModResultData, String fileName);

}