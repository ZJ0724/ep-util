package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.entity.AgentCusResultNew;
import com.easipass.util.core.service.CusResultService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * CusResultController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "cusResult")
public class CusResultController {

    /**
     * 上传新代理委托回执
     *
     * @param requestBody requestBody
     *
     * @return 响应
     * */
    @PostMapping("uploadNewAgentCusResult")
    public Response uploadNewAgentCusResult(@RequestBody Map<String, String> requestBody) {
        AgentCusResultNew cusResult = new AgentCusResultNew(requestBody.get("ediNo"), requestBody.get("channel"), requestBody.get("message"));

        CusResultService cusResultService = new CusResultService();
        cusResultService.uploadNewAgentCusResult(cusResult);

        return Response.returnTrue("上传成功");
    }

}