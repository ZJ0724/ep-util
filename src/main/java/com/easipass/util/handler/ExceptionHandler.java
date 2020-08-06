package com.easipass.util.handler;

import com.easipass.util.entity.Log;
import com.easipass.util.entity.Response;
import com.easipass.util.exception.BaseException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 *
 * @author ZJ
 * */
@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    /**
     * 基础异常
     *
     * @param e 异常
     *
     * @return 响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public Response baseException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

}