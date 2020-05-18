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

}
