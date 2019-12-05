package com.easipass.EP_Util_Server.controller.formResult;

import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.entity.formResult.TongXunFormResult;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.formResult.TongXunFormResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tongXunFormResult")
@ResponseBody
public class TongXunFormResultController {

    @Autowired
    TongXunFormResultService tongXunFormResultService;

    /**
     * 上传通讯回执
     * */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Response upload(@RequestBody(required = false) TongXunFormResult tongXunFormResult){
        //检查参数
        if(tongXunFormResult==null||!tongXunFormResult.check()){
            return new Response("F","","请求参数有误或缺失",null);
        }

        try {
            tongXunFormResultService.upload(tongXunFormResult);
            return new Response("T","","",null);
        }catch (TipException e){
            return new Response("F","",e.getMessage(),null);
        }
    }

}