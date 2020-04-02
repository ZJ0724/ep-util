package com.easipass.EP_Util_Server.controller;

import com.easipass.EP_Util_Server.entity.DecMod;
import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.exception.TipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/decMod")
@ResponseBody
public class DecModController {

    @Autowired
    DecModController decModController;

    /**
     * 设置文件名
     * */
    @RequestMapping(value = "setFileName",method = RequestMethod.POST)
    public Response setFileName(@RequestBody(required = false) DecMod decMod){
        //检查参数
        if(decMod==null||!decMod.check()){
            return new Response("F","","请求参数有误或缺失",null);
        }

        try {
            decModController.setFileName(decMod);
            return new Response("T","","",null);
        }catch (TipException e){
            return new Response("F","",e.getMessage(),null);
        }
    }

}