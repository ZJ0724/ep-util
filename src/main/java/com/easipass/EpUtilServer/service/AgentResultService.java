package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AgentResultService {

    /**
     * 上传代理委托回执
     * */
    Response upload(String ediNo, ResultDTO resultDTO);

}
