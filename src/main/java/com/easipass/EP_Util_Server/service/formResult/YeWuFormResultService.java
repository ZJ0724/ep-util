package com.easipass.EP_Util_Server.service.formResult;

import com.easipass.EP_Util_Server.entity.formResult.YeWuFormResult;
import org.springframework.stereotype.Service;

@Service
public interface YeWuFormResultService {

    /**
     * 上传回执
     * */
    public void upload(YeWuFormResult yeWuFormResult);

}