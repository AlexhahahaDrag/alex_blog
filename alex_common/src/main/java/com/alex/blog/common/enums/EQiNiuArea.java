package com.alex.blog.common.enums;

/**
 *description:  七牛存储空间枚举
 *author:       alex
 *createDate:   2021/8/11 7:28
 *version:      1.0.0
 */
public enum EQiNiuArea {
    z0("z0", "华东"),
    z1("z1", "华北"),
    z2("z2", "华南"),
    na0("na0", "北美"),
    as0("as0", "东南亚");

    private String code;
    private String value;

    EQiNiuArea(String code, String value) {
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
