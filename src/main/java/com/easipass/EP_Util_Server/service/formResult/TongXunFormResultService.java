package com.easipass.EP_Util_Server.service.formResult;

import com.easipass.EP_Util_Server.entity.formResult.TongXunFormResult;
import org.springframework.stereotype.Service;

@Service
public interface TongXunFormResultService {

    /**
     * 上传回执
     * */
    public void upload(TongXunFormResult tongXunFormResult);

}