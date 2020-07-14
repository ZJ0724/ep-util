package com.easipass.epUtil.handler;

import com.easipass.epUtil.config.ErrorCodeConfig;
import com.easipass.epUtil.exception.*;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.entity.Response;
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
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

    /**
     * oracle异常
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(OracleException.class)
    public Response oracleException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

    /**
     * config异常
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ConfigException.class)
    public Response configException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(ErrorCodeConfig.CONFIG_ERROR);
    }

    /**
     * 谷歌驱动异常
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(ChromeDriverException.class)
    public Response chromeDriverException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

    /**
     * 一次上传异常
     *
     * @return 响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(DisposableUploadException.class)
    public Response disposableUploadException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

    /**
     * 报文异常
     *
     * @param e 异常
     *
     * @return 响应结果
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler(CusFileException.class)
    public Response cusFileException(Exception e) {
        Log.getLog().error(e.getMessage());
        return Response.returnFalse(e.getMessage());
    }

}