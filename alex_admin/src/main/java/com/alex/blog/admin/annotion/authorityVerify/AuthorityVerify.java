package com.alex.blog.admin.annotion.authorityVerify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *description:  自定义权限校验接口
 *author:       alex
 *createDate:   2021/9/6 21:37
 *version:      1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityVerify {

    String value() default "";
}
