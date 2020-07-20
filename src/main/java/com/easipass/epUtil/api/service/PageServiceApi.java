package com.easipass.epUtil.api.service;

import com.zj0724.springbootUtil.annotation.SkipCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面映射
 *
 * @author ZJ
 * */
@Controller
@SkipCheck
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