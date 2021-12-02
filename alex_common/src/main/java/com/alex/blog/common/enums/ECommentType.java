package com.alex.blog.common.enums;

/**
 *description:  评论类型枚举类
 *author:       alex
 *createDate:   2021/12/2 17:51
 *version:      1.0.0
 */
public enum ECommentType {
    COMMENT("0", "评论"),
    PRAISE("1", "点赞");

    ECommentType(String code, String value) {
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
