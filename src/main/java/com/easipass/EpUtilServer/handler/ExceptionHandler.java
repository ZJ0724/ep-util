package com.easipass.EpUtilServer.handler;

import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.exception.ErrorException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    /**
     * 错误异常处理
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorException.class)
    public Response error(Exception e) {
        return Response.returnFalse(500, e.getMessage());
    }

}
