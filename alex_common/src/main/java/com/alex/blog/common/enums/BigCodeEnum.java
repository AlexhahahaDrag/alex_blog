package com.alex.blog.common.enums;

import com.alex.blog.common.constants.AlexCode;

public enum BigCodeEnum {

    UNKNOW_EXCEPTION(AlexCode.SYS_CODE + AlexCode.RESULT_CODE_SYSTEM_UNKNOWN_ERROR,"系统未知异常"),
    VAILD_EXCEPTION(AlexCode.SYS_CODE + AlexCode.RESULT_CODE_INVALID_INPUT,"参数格式校验失败"),
    TO_MANY_REQUEST(AlexCode.SYS_CODE + AlexCode.RESULT_CODE_INNER_ERROR,"请求流量过大，请稍后再试"),
    SMS_CODE_EXCEPTION(AlexCode.SYS_CODE + AlexCode.RESULT_CODE_FQS_LARGER_ERROR,"验证码获取频率太高，请稍后再试"),
    ;

    BigCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
