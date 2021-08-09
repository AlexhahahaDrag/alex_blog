package com.alex.blog.common.exception;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
public class AlexException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private String code = "500";

    public AlexException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AlexException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public AlexException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AlexException(String code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
