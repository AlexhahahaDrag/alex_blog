package com.alex.blog.miaosha.common.handle;

import com.alex.blog.miaosha.common.common.Result;
import com.alex.blog.miaosha.common.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLDataException;
import java.time.format.DateTimeParseException;


/**
 *description:  异常统一处理类
 *author:       majf
 *createDate:   2022/6/23 17:05
 *version:      1.0.0
 */
@RestControllerAdvice
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        e.printStackTrace();
        log.error("系统内部异常，异常信息：" + e);
        return Result.error(ResultEnum.SYSTEM_ERROR.getCode(), ResultEnum.SYSTEM_ERROR.getValue());
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, SQLDataException.class, DateTimeParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleParamsDateException(Exception e) {
        log.error(e.getMessage());
        return Result.error(ResultEnum.PARAM_ERROR.getCode(), "日期参数错误");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleParamsInvalidException(Exception e) {
        log.error(e.getMessage());
        return Result.error(ResultEnum.PARAM_ERROR.getCode(), "类型参数错误");
    }
}
