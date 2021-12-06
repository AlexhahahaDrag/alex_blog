package com.alex.blog.common.enums;

/**
 *description:  评论来源枚举类
 *author:       alex
 *createDate:   2021/12/3 14:20
 *version:      1.0.0
 */
public enum ECommentSource {
    ABOUT("0", "关于我"),
    BLOG_INFO("1", "博客详情"),
    MESSAGE_BOARD("2", "留言板");

    ECommentSource(String code, String value) {
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
