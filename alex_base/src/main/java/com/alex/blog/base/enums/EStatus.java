package com.alex.blog.base.enums;

/**
 *description:  状态枚举类
 *author:       alex
 *createDate:   2021/1/20 20:22
 *version:      1.0.0
 */
public enum EStatus {
    DISABLED(0, "删除的"),
    ENABLE(1, "激活的"),
    FREEZE(2, "激活的"),
    STICK(3, "置顶的");

    EStatus(Integer code, String value) {
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
