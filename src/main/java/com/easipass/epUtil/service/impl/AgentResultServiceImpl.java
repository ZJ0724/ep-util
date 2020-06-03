package com.easipass.epUtil.service.impl;

import com.easipass.epUtil.entity.*;
import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.result.AgentResult;
import com.easipass.epUtil.entity.sftp.Sftp83;
import com.easipass.epUtil.service.AgentResultService;
import org.springframework.stereotype.Service;

@Service
public class AgentResultServiceImpl implements AgentResultService {

    @Override
    public Response upload(String ediNo, ResultDTO resultDTO) {
        Result result = new AgentResult(ediNo, resultDTO);

        Sftp83 sftp83 = new Sftp83();
        sftp83.connect();
        sftp83.uploadResult(result);
        sftp83.close();

        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.swgdRecvRun();
        chromeDriver.close();

        return Response.returnTrue();
    }

}