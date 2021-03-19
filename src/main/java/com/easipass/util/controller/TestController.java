package com.easipass.util.controller;

import com.easipass.util.entity.cusresult.CustomsDeclarationCusResult;
import com.easipass.util.service.CusResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private CusResultService cusResultService;

}