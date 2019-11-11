package com.easipass.EP_Util_Server.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtil {

    /**
     * 设置响应参数
     */
    public static String setResponseData(String flag,String errorCode,String errorMsg,Object data){

        String result="";
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("flag",flag);
        map.put("errorCode",errorCode);
        map.put("errorMsg",errorMsg);
        map.put("data",data);
        result= JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
        return result;

    }

}
