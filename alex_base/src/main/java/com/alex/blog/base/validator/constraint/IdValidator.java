package com.alex.blog.base.validator.constraint;

import cn.hutool.core.util.StrUtil;
import com.alex.blog.base.validator.annotion.IdValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *description:  id校验器   ConstraintValidator校验器在springboot2.3之后需要引入
 * 		<dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-validation</artifactId>
 *         </dependency>
 *author:       alex
 *createDate:   2021/7/11 20:59
 *version:      1.0.0
 */
public class IdValidator implements ConstraintValidator<IdValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StrUtil.isBlank(value) || StrUtil.isEmptyIfStr(value)) {
            return false;
        }
        return false;
    }

    @Override
    public void initialize(IdValid constraintAnnotation) {
    }
}
