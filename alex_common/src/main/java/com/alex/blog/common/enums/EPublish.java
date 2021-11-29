package com.alex.blog.common.enums;

/**
 *description:  发布状态枚举类
 *author:       alex
 *createDate:   2021/11/26 14:56
 *version:      1.0.0
 */
public enum EPublish {
    PUBLISH("1", "发布"),
    NO_PUBLISH("0", "未发布");

    EPublish(String code, String value) {
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
