package com.easipass.EpUtilServer.handler;

import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.exception.ResponseException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    /**
     * 错误异常处理
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorException.class)
    public Response errorException(Exception e) {
        return Response.error();
    }

    /**
     * 返回响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ResponseException.class)
    public Response responseException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

}
