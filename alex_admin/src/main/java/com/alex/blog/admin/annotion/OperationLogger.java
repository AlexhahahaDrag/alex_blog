package com.alex.blog.admin.annotion;

import com.alex.blog.common.enums.PlatformEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *description:  标记该注解的方法需要记录操作日志
 *author:       alex
 *createDate:   2021/9/6 21:40
 *version:      1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogger {

    //业务名称
    String value() default "";

    PlatformEnums platform() default PlatformEnums.ADMIN;

    boolean save() default true;
}
