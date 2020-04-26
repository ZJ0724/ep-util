package com.easipass.EpUtilServer.handler;

import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.enumeration.ErrorCodeEnum;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.exception.ParamException;
import com.easipass.EpUtilServer.exception.ResponseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
        return Response.returnFalse(ErrorCodeEnum.ERROR, e.getMessage());
    }

    /**
     * 返回响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ResponseException.class)
    public Response responseException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

    /**
     * 请求参数为空
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public Response requestParamIsNull() {
        return Response.returnFalse(ErrorCodeEnum.PARAM_ERROR);
    }

    /**
     * 请求参数有误
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ParamException.class)
    public Response paramException(Exception e) {
        return Response.returnFalse(ErrorCodeEnum.PARAM_ERROR, e.getMessage());
    }

}
