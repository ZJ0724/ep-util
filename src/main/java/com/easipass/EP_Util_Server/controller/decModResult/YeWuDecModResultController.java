package com.easipass.EP_Util_Server.controller.decModResult;

import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.entity.decModResult.YeWuDecModResult;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.decModResult.YeWuDecModResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/yeWuDecModResult")
@ResponseBody
public class YeWuDecModResultController {

    @Autowired
    YeWuDecModResultService yeWuDecModResultService;

    /**
     * 上传业务回执
     * */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Response upload(@RequestBody(required = false) YeWuDecModResult yeWuDecModResult){
        //检查参数
        if(yeWuDecModResult==null||!yeWuDecModResult.check()){
            return new Response("F","","请求参数有误或缺失",null);
        }

        try {
            yeWuDecModResultService.upload(yeWuDecModResult.getYeWuDecModResultData(),yeWuDecModResult.getFileName());
            return new Response("T","","",null);
        }catch (TipException e){
            return new Response("F","",e.getMessage(),null);
        }
    }

}