package com.alex.blog.common.enums;

/**
 *description:  友链状态枚举类
 *author:       alex
 *createDate:   2022/1/28 10:04
 *version:      1.0.0
 */
public enum ELinkStatus {
    APPLY("0", "申请中"),
    PUBLISH("1", "已发布"),
    NO_PUBLISH("2", "已下架");

    ELinkStatus(String code, String value) {
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
