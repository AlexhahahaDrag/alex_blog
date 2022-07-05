package com.alex.blog.miaosha.common.enums;

/**
 *description:  结果枚举
 *author:       majf
 *createDate:   2022/7/4 17:36
 *version:      1.0.0
 */
public enum ResultEnum {

    SUCCESS("200", "success"),
    PARAM_ERROR("400", "param_fail"),
    SYSTEM_ERROR("500", "system_fail"),
    //订单模块
    ORDER_NOT_EXISTS("60001", "订单不存在")
    ;

    ResultEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
