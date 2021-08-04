package com.alex.blog.common.enums;

/**
 *description:  网站登录方式枚举
 *author:       alex
 *createDate:   2021/8/4 21:39
 *version:      1.0.0
 */
public enum ELoginType {

    PASSWORD("1", "password"),
    GITEE("2", "gitee"),
    GITHUB("3", "github"),
    QQ("4", "qq"),
    WECHAT("5", "wechat");

    private String code;

    private String value;

    ELoginType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
