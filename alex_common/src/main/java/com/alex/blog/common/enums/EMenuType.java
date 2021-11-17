package com.alex.blog.common.enums;

/**
 *description:  菜单类型枚举类
 *author:       alex
 *createDate:   2021/11/17 20:30
 *version:      1.0.0
 */
public enum EMenuType {
    MENU(0, "菜单"),
    BUTTON(1, "按钮");

    EMenuType() {
    }

    EMenuType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;

    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
