package com.alex.blog.common.enums;

/**
 *description:  是否是原创枚举类
 *author:       alex
 *createDate:   2021/12/22 21:29
 *version:      1.0.0
 */
public enum EOriginal {
    ORIGINAL("1", "原创"),
    UNORIGINAL("0", "非原创");

    EOriginal(String code, String value) {
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
