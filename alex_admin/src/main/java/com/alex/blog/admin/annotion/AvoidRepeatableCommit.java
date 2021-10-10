package com.alex.blog.admin.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *description:  自定义注解 避免接口重复提交
 *author:       alex
 *createDate:   2021/10/5 19:38
 *version:      1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidRepeatableCommit {

    /**
     * @description:  指定时间内不可重复提交，单位毫秒默认1秒
     * @author:       alex
     * @return:       long
    */
    long timeout() default 1000;
}
