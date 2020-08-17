package com.easipass.util.api.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面映射
 *
 * @author ZJ
 * */
@Controller
public class PageServiceApi {

    /**
     * 首页
     *
     * @return 首页地址
     * */
    @GetMapping("/")
    public String indexHtml() {
        return "/index.html";
    }

}