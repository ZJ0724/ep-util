package com.easipass.epUtil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadResultAnnotation {

    /**
     * 是否是一次上传
     * */
    boolean isDisposable() default false;

}
