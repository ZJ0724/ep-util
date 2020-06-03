package com.easipass.epUtil.handler;

import com.easipass.epUtil.entity.Response;
import com.easipass.epUtil.exception.OracleException;
import com.easipass.epUtil.exception.SftpException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandler {

    /**
     * sftp异常
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(SftpException.class)
    public Response sftpException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

    /**
     * oracle异常
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(OracleException.class)
    public Response oracleException(Exception e) {
        return Response.returnFalse(e.getMessage());
    }

}