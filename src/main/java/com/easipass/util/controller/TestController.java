package com.easipass.util.controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("test/**")
    public String addUser(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRequestURL());
        return httpServletRequest.getRequestURI();
    }

}