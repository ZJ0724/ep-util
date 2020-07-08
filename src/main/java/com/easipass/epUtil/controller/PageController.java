package com.easipass.epUtil.controller;

import com.easipass.epUtil.config.ResourcePathConfig;
import com.zj0724.springbootUtil.annotation.SkipCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SkipCheck
public class PageController {

    /**
     * 首页
     * */
    @GetMapping("/")
    public String index() {
        return ResourcePathConfig.INDEX_HTML;
    }

    /**
     * 报文比对页面
     *
     * @return 页面路径
     * */
    @GetMapping("/cusFileComparison")
    public String cusFileComparison() {
        return ResourcePathConfig.CUS_FILE_COMPARISON_HTMLComparison;
    }

}