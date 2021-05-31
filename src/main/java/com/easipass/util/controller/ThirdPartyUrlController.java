package com.easipass.util.controller;

import com.easipass.util.entity.po.ThirdPartyUrlPO;
import com.easipass.util.service.ThirdPartyUrlService;
import com.zj0724.common.util.MapUtil;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/thirdPartyUrl")
public final class ThirdPartyUrlController {

    @Resource
    private ThirdPartyUrlService thirdPartyUrlService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(thirdPartyUrlService.getAll());
    }

    @PostMapping("save")
    public Response save(@RequestBody(required = false) Map<String, Object> requestBody) {
        thirdPartyUrlService.save(ObjectUtil.parse(requestBody, ThirdPartyUrlPO.class));
        return Response.returnTrue();
    }

    @PostMapping("delete")
    public Response delete(@RequestBody(required = false) Map<String, Object> requestBody) {
        thirdPartyUrlService.delete(MapUtil.getValue(requestBody, "id", Long.class));
        return Response.returnTrue();
    }

}