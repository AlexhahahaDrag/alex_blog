package com.alex.blog.base.validator.annotion;

import com.alex.blog.base.validator.Message;
import com.alex.blog.base.validator.constraint.IdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 *description:  id校验
 *author:       alex
 *createDate:   2021/7/11 20:33
 *version:      1.0.0
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdValidator.class})
public @interface IdValid {

    boolean required() default true;

    String message() default Message.CK_NOT_BLACK_DEFAULT;

    String vlaue() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
