package com.easipass.EP_Util_Server.controller;

import com.easipass.EP_Util_Server.entity.formResult.TongXunFormResultBean;
import com.easipass.EP_Util_Server.entity.formResult.YeWuFormResultBean;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.ResultService;
import com.easipass.EP_Util_Server.service.impl.TongXunFormResultServiceImpl;
import com.easipass.EP_Util_Server.service.impl.YeWuFormResultServiceImpl;
import com.easipass.EP_Util_Server.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/EP_Util/formResult")
@ResponseBody
public class FormResultController {

    /**
     * 上传通讯回执
     * */
    @RequestMapping(value = "uploadTongXun",method = RequestMethod.POST)
    public String uploadTongXun(@RequestBody TongXunFormResultBean tongXunFormResultBean){

        ResultService resultServer =new TongXunFormResultServiceImpl(tongXunFormResultBean);

        try {
            resultServer.upload();
        }catch (TipException e){
            return RequestUtil.setResponseData("F","",e.getMessage(),null);
        }

        return RequestUtil.setResponseData("T","","",null);

    }

    /**
     * 上传业务回执
     * */
    @RequestMapping(value = "uploadYeWu",method = RequestMethod.POST)
    public String uploadYeWu(@RequestBody YeWuFormResultBean yeWuFormResultBean){

        ResultService resultServer =new YeWuFormResultServiceImpl(yeWuFormResultBean);

        try {
            resultServer.upload();
        }catch (TipException e){
            return RequestUtil.setResponseData("F","",e.getMessage(),null);
        }

        return RequestUtil.setResponseData("T","","",null);

    }

}