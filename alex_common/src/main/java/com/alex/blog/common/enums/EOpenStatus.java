package com.alex.blog.common.enums;

/**
 *description:  开启状态枚举类
 *author:       alex
 *createDate:   2021/8/5 6:34
 *version:      1.0.0
 */
public enum EOpenStatus {
    CLOSE("0", "close"),
    OPEN("1", "open");

    private String code;

    private String value;

    EOpenStatus(String code, String value) {
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
