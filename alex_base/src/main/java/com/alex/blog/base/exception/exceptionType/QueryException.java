package com.alex.blog.base.exception.exceptionType;

import java.io.Serializable;

/**
 *description:  自定义查询相关异常
 *author:       alex
 *createDate:   2021/8/3 7:19
 *version:      1.0.0
 */
public class QueryException extends RuntimeException implements Serializable {

    private String code;

    public QueryException(String message) {
        super(message);
        // TODO: 2021/8/3 添加统一编码
        this.code = "00100";
    }

    public QueryException(String code, String message) {
        super(message);
        this.code = code;
    }

    public QueryException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public QueryException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public QueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
