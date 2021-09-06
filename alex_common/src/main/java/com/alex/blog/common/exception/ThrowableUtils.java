package com.alex.blog.common.exception;

import cn.hutool.core.collection.CollectionUtil;
import com.alex.blog.base.global.Constants;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;

/**
 *description:  抛出异常工具类
 *author:       alex
 *createDate:   2021/9/6 21:50
 *version:      1.0.0
 */
public class ThrowableUtils {

    /**
     * @param result
     * @description:  校验参数
     * @author:       alex
     * @return:       void
     */
    public static void checkParamArgument(BindingResult result) {
        if (result != null && result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            if (CollectionUtil.isNotEmpty(fieldErrors)) {
                FieldError error = fieldErrors.get(0);
                String rejectedValue = Objects.toString(error.getRejectedValue(), "");
                String defaultMessage = error.getDefaultMessage();
                //排除类上面的注释提示
                if (rejectedValue.contains(Constants.DELIMITER_TO)) {
                    //自己确定错误字段
                    sb.append(defaultMessage);
                } else {
                    sb.append(error.getField()).append(" ").append(defaultMessage);
                }
            } else {
                String msg = result.getAllErrors().get(0).getDefaultMessage();
                sb.append(msg);
            }
            throw new AlexException(sb.toString());
        }
    }
}
