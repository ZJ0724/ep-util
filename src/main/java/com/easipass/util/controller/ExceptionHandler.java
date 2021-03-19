package com.easipass.util.controller;

import com.easipass.util.config.ErrorCodeConfig;
import com.zj0724.common.exception.InfoException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Throwable.class)
    public Response throwable(Throwable e) {
        if (e instanceof InfoException) {
            return Response.returnFalse(ErrorCodeConfig.DEFAULT_INFO, e.getMessage());
        }
        e.printStackTrace();
        return Response.returnFalse(ErrorCodeConfig.ERROR, e.getMessage());
    }

}