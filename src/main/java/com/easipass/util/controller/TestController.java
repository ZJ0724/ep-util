package com.easipass.util.controller;

import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test")
    public Response test(@RequestParam("a") String a) {
        if (a.equals("1")) {
            throw new RuntimeException("错误异常");
        }
        return Response.returnTrue();
    }

}