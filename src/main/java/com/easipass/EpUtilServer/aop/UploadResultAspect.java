package com.easipass.EpUtilServer.aop;

import com.easipass.EpUtilServer.annotation.UploadResultAnnotation;
import com.easipass.EpUtilServer.config.ProjectConfig;
import com.easipass.EpUtilServer.entity.Sftp;
import com.easipass.EpUtilServer.exception.ResponseException;
import com.easipass.EpUtilServer.service.BaseService;
import com.zj0724.uiAuto.WebDriver;
import com.zj0724.uiAuto.webDriver.ChromeWebDriver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UploadResultAspect {

    @Pointcut("@annotation(com.easipass.EpUtilServer.annotation.UploadResultAnnotation)")
    public void UploadResultAspect(){}

    @Before("UploadResultAspect()")
    public void before(JoinPoint point) {
        // 获取注解上的参数值
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        UploadResultAnnotation uploadResultAnnotation = methodSignature.getMethod().getAnnotation(UploadResultAnnotation.class);
        boolean isDisposable = uploadResultAnnotation.isDisposable();

        if (isDisposable) {

        }
        if (BaseService.SFTP_THREAD_LOCAL.get() == null) {
            Sftp sftp = Sftp.getSftp83();
            System.out.println("连接sftp...");
            if (!sftp.connect()) {
                throw new ResponseException("sftp:" + sftp.getUrl() + "连接失败");
            }
            BaseService.SFTP_THREAD_LOCAL.set(sftp);
        }

        if (BaseService.WEB_DRIVER_THREAD_LOCAL.get() == null) {
            WebDriver webDriver = new ChromeWebDriver(ProjectConfig.CHROME_DRIVER);
            System.out.println("创建webdriver");
            BaseService.WEB_DRIVER_THREAD_LOCAL.set(webDriver);
        }
    }

    @After("UploadResultAspect()")
    public void after(JoinPoint point) {
        // 获取注解上的参数值
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        UploadResultAnnotation uploadResultAnnotation = methodSignature.getMethod().getAnnotation(UploadResultAnnotation.class);
        boolean isDisposable = uploadResultAnnotation.isDisposable();

        if (!isDisposable) {
            BaseService.SFTP_THREAD_LOCAL.get().close();
            BaseService.WEB_DRIVER_THREAD_LOCAL.get().close();
            BaseService.SFTP_THREAD_LOCAL.set(null);
            BaseService.WEB_DRIVER_THREAD_LOCAL.set(null);
            System.out.println("sftp已关闭");
            System.out.println("webdriver已关闭");
        }
    }

}
