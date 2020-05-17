package com.easipass.epUtil.aop;

import com.easipass.epUtil.annotation.UploadResultAnnotation;
import com.easipass.epUtil.config.ProjectConfig;
import com.easipass.epUtil.entity.Sftp;
import com.easipass.epUtil.exception.ResponseException;
import com.easipass.epUtil.service.BaseService;
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

    private static final ThreadLocal<Boolean> isDisposableThreadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(com.easipass.epUtil.annotation.UploadResultAnnotation)")
    public void UploadResultAspect(){}

    @Before("UploadResultAspect()")
    public void before(JoinPoint point) {
        // 获取注解上的参数值
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        UploadResultAnnotation uploadResultAnnotation = methodSignature.getMethod().getAnnotation(UploadResultAnnotation.class);
        boolean isDisposable = uploadResultAnnotation.isDisposable();

        // 一次上传
        if (isDisposable) {
            isDisposableThreadLocal.set(true);
        }

        if ((isDisposableThreadLocal.get() == null && !isDisposable) || (isDisposableThreadLocal.get() && isDisposable)) {
            Sftp sftp = Sftp.getSftp83();
            System.out.println("连接sftp...");
            if (!sftp.connect()) {
                throw new ResponseException("sftp:" + sftp.getUrl() + "连接失败");
            }
            BaseService.SFTP_THREAD_LOCAL.set(sftp);

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

        if ((isDisposableThreadLocal.get() == null && !isDisposable) || (isDisposableThreadLocal.get() && isDisposable)) {
            Sftp sftp = BaseService.SFTP_THREAD_LOCAL.get();
            WebDriver webDriver = BaseService.WEB_DRIVER_THREAD_LOCAL.get();
            if (sftp != null) {
                sftp.close();
                BaseService.SFTP_THREAD_LOCAL.set(null);
                System.out.println("sftp已关闭");
            }
            if (webDriver != null) {
                webDriver.close();
                BaseService.WEB_DRIVER_THREAD_LOCAL.set(null);
                System.out.println("webdriver已关闭");
            }
            isDisposableThreadLocal.set(null);
        }
    }

}
