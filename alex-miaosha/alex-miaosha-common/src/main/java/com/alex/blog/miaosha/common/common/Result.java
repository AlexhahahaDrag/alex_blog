package com.alex.blog.miaosha.common.common;

import com.alex.blog.miaosha.common.enums.ResultEnum;
import lombok.Data;

/**
 *description:  结果信息
 *author:       majf
 *createDate:   2022/7/4 17:33
 *version:      1.0.0
 */
@Data
public class Result<T> {

    private String code;

    private String message;

    private static final String success = ResultEnum.SUCCESS.getCode();

    private static final String param_fail = ResultEnum.PARAM_FAIL.getCode();

    private static final String system_fail = ResultEnum.SYSTEM_FAIL.getCode();

    private T data;

    private Result() {
    }

    public static<T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(success);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(success);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static<T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(success);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    public static<T> Result<T> paramError(String message) {
        Result<T> result = new Result<>();
        result.setCode(param_fail);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> systemError(String message) {
        Result<T> result = new Result<>();
        result.setCode(system_fail);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> systemError(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(system_fail);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static<T> Result<T> error(String code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> error(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
