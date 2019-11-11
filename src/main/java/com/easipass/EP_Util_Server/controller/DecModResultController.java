package com.easipass.EP_Util_Server.controller;

import com.alibaba.fastjson.JSONObject;
import com.easipass.EP_Util_Server.entity.decModResult.QPDecModResultBean;
import com.easipass.EP_Util_Server.entity.decModResult.YeWuDecModResultBean;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.service.DecModResultService;
import com.easipass.EP_Util_Server.service.impl.QPDecModResultServiceImpl;
import com.easipass.EP_Util_Server.service.impl.YeWuDecModResultServiceImpl;
import com.easipass.EP_Util_Server.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/EP_Util/decModResult")
@ResponseBody
public class DecModResultController {

    /**
     * 上传QP回执
     * */
    @RequestMapping(value = "uploadQP",method = RequestMethod.POST)
    public String uploadQP(@RequestBody String requestDataByString){

        //请求数据
        JSONObject requestData=JSONObject.parseObject(requestDataByString);

        //文件名
        String fileName=requestData.getString("fileName");

        //data节点
        JSONObject data=JSONObject.parseObject(requestData.getString("data"));

        //QPDecModResultBean
        QPDecModResultBean qpDecModResultBean=new QPDecModResultBean();
        qpDecModResultBean.setDecModSeqNo(data.getString("decModSeqNo"));
        qpDecModResultBean.setResultMessage(data.getString("resultMessage"));
        qpDecModResultBean.setResultCode(data.getString("resultCode"));

        DecModResultService decModResultService=new QPDecModResultServiceImpl(fileName,qpDecModResultBean);
        try {
            decModResultService.upload();
        }catch (TipException e){
            return RequestUtil.setResponseData("F","",e.getMessage(),null);
        }

        return RequestUtil.setResponseData("T","","",null);

    }

    /**
     * 上传业务回执
     * */
    @RequestMapping(value = "uploadYeWu",method = RequestMethod.POST)
    public String uploadYeWu(@RequestBody String requestDataByString){

        //请求数据
        JSONObject requestData=JSONObject.parseObject(requestDataByString);

        //文件名
        String fileName=requestData.getString("fileName");

        //data节点
        JSONObject data=JSONObject.parseObject(requestData.getString("data"));

        //QPDecModResultBean
        YeWuDecModResultBean yeWuDecModResultBean=new YeWuDecModResultBean();
        yeWuDecModResultBean.setDestResourceId(data.getString("destResourceId"));
        yeWuDecModResultBean.setFeedbackResults(data.getString("feedbackResults"));
        yeWuDecModResultBean.setResultNote(data.getString("resultNote"));

        DecModResultService decModResultService=new YeWuDecModResultServiceImpl(fileName,yeWuDecModResultBean);
        try {
            decModResultService.upload();
        }catch (TipException e){
            return RequestUtil.setResponseData("F","",e.getMessage(),null);
        }

        return RequestUtil.setResponseData("T","","",null);

    }

    /**
     * 设置文件名
     * */
    @RequestMapping(value = "setFileName",method = RequestMethod.POST)
    public String setFileName(@RequestBody String requestDataByString){

        //请求数据
        JSONObject requestData=JSONObject.parseObject(requestDataByString);

        //报关单号
        String preEntryId=requestData.getString("preEntryId");
        //文件名
        String fileName=requestData.getString("fileName");

        DecModResultService decModResultService=new QPDecModResultServiceImpl();
        try {
            decModResultService.setFileName(preEntryId,fileName);
        }catch (TipException e){
            return RequestUtil.setResponseData("F","",e.getMessage(),null);
        }

        return RequestUtil.setResponseData("T","","",null);

    }

}