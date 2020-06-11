package com.easipass.epUtil.handler;

import com.easipass.epUtil.config.ErrorCodeConfig;
import com.easipass.epUtil.exception.ChromeDriverException;
import com.easipass.epUtil.exception.ConfigException;
import com.easipass.epUtil.module.Log;
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
        return Response.returnFalse("驱动异常，请更换驱动文件");
    }

}