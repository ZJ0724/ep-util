package com.easipass.epUtil.service;

import com.easipass.epUtil.entity.dto.ResultDTO;
import com.easipass.epUtil.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AgentResultService {

    /**
     * 上传代理委托回执
     * */
    Response upload(String ediNo, ResultDTO resultDTO);

}