package com.easipass.util.aop;

import com.easipass.util.core.BaseException;
import com.easipass.util.core.exception.ErrorException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 异常切面
 *
 * @author ZJ
 * */
@Component
@Aspect
public final class ExceptionAspect {

    /**
     * controller切点
     * */
    @Pointcut("execution(public * com.easipass.util.controller.*.*(..))")
    public void exceptionAspect() {}

    /**
     * 后置异常处理
     *
     * @param e 异常
     * */
    @AfterThrowing(value = "exceptionAspect()", throwing = "e")
    public void exception(Throwable e) {
        if (!(e instanceof BaseException)) {
            throw new ErrorException(e.getMessage());
        }
    }

}