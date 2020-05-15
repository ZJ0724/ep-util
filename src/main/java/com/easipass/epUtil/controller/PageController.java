package com.easipass.epUtil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    /**
     * 首页
     * */
    @GetMapping("/")
    public String index() {
        return "html/index.html";
    }

}
