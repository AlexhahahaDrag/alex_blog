package com.alex.blog.common.enums;

/**
 *description:  平台枚举类
 *author:       alex
 *createDate:   2021/9/6 21:44
 *version:      1.0.0
 */
public enum PlatformEnums {
    ADMIN("admin", "管理员"),
    WEB("web", "web端");

    PlatformEnums(String code, String value) {
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
