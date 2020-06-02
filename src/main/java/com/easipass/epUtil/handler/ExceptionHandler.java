package com.easipass.epUtil.handler;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.exception.OracleException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    /**
     * 返回响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(OracleException.class)
    public Response responseException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

}