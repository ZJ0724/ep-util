package com.easipass.util.api.service;

import com.zj0724.util.springboot.parameterCheck.Skip;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面映射
 *
 * @author ZJ
 * */
@Controller
@Skip
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