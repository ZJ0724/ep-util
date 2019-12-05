package com.easipass.EP_Util_Server.controller.decModResult;

import com.easipass.EP_Util_Server.entity.Response;
import com.easipass.EP_Util_Server.entity.decModResult.QPDecModResult;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.decModResult.QPDecModResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/QPDecModResult")
@ResponseBody
public class QPDecModResultController {

    @Qualifier("QPDecModResultServiceImpl")
    @Autowired
    QPDecModResultService qpDecModResultService;

    /**
     * 上传QP回执
     * */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Response upload(@RequestBody(required = false) QPDecModResult qpDecModResult){
        //检查参数
        if(qpDecModResult==null||!qpDecModResult.check()){
            return new Response("F","","请求参数有误或缺失",null);
        }

        try {
            qpDecModResultService.upload(qpDecModResult.getQPDecModResultData(),qpDecModResult.getFileName());
            return new Response("T","","",null);
        }catch (TipException e){
            return new Response("F","",e.getMessage(),null);
        }
    }

}