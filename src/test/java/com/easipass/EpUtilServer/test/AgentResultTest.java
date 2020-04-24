package com.easipass.EpUtilServer.test;

import com.easipass.EpUtilServer.entity.ResultDTO;
import com.easipass.EpUtilServer.service.AgentResultService;
import org.junit.Test;
import javax.annotation.Resource;

public class AgentResultTest extends BaseTest {

    @Resource
    private AgentResultService agentResultService;

    @Test
    public void upload() {
        String ediNo = "EDI208000001745769";
        ResultDTO resultDTO = new ResultDTO("0", "通过");

        super.assertResponse(agentResultService.upload(ediNo, resultDTO));
    }

}
