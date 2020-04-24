package com.easipass.EpUtilServer.aop;

import com.easipass.EpUtilServer.annotation.NotNull;
import com.easipass.EpUtilServer.exception.ErrorException;
import com.easipass.EpUtilServer.exception.ParamException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Aspect
@Component
public class ParamCheckAspect {

    @Pointcut("execution(* com.easipass.EpUtilServer.controller.*.*(..))")
    public void ParamCheckAspect(){}

    @Before("ParamCheckAspect()")
    public void before(JoinPoint joinPoint) {
        // 获取参数
        Object[] objects = joinPoint.getArgs();

        // 只校验@RequestBody
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        int index = 0;
        boolean isOk = false;
        Annotation[][] annotations = methodSignature.getMethod().getParameterAnnotations();
        for (Annotation[] annotations1 : annotations) {
            for (Annotation annotation : annotations1) {
                if (annotation.annotationType() == RequestBody.class) {
                    isOk = true;
                    break;
                }
            }
            if (isOk) {
                break;
            }
            index++;
        }

        // 实际需要检验的对象
        Object data = objects[index];

        // 获取class对象
        Class<?> dataClass = data.getClass();

        // 遍历字段
        Field[] fields = dataClass.getDeclaredFields();
        for (Field field : fields) {
            // 存在@NotNull注解就校验参数
            NotNull notNull = field.getAnnotation(NotNull.class);
            if (notNull != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(data) == null) {
                        throw new ParamException(notNull.errorMsg());
                    }
                } catch (IllegalAccessException e) {
                    throw ErrorException.getErrorException("校验参数出错");
                }
            }
        }
    }

}
