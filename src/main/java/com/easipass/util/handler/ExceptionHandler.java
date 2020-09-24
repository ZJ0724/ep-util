package com.easipass.util.handler;

import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.entity.Response;
import com.easipass.util.core.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 基础异常
     *
     * @param e 异常
     *
     * @return 响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public Response baseException(Exception e) {
        log.info(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

    /**
     * 错误异常
     *
     * @param e 异常
     *
     * @return 响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorException.class)
    public Response errorException(Exception e) {
        return Response.error(e.getMessage());
    }

    /**
     * 上传文件异常
     *
     * @param e e
     *
     * @return Resource
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.multipart.MultipartException.class)
    public Response multipartException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

}