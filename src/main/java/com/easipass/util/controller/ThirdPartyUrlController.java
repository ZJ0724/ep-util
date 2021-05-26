package com.easipass.util.controller;

import com.easipass.util.service.ThirdPartyUrlService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping(BaseController.API + "/thirdPartyUrl")
public final class ThirdPartyUrlController {

    @Resource
    private ThirdPartyUrlService thirdPartyUrlService;

    @GetMapping("getAll")
    public Response getAll() {
        return Response.returnTrue(thirdPartyUrlService.getAll());
    }

}