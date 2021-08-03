package com.alex.blog.common.enums;

/**
 *description:  账号枚举类
 *author:       alex
 *createDate:   2021/8/4 7:13
 *version:      1.0.0
 */
public enum EAccountType {
    EMAIL("0", "email"),
    QQNUMBER("1", "qq号"),
    QQGROUP("2", "qq群组"),
    GITHUB("3", "github"),
    GITEE("4", "gitee"),
    WECHAT("5", "weChat")
    ;

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    EAccountType(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
