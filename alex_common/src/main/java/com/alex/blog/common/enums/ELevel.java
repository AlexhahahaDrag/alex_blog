package com.alex.blog.common.enums;

import com.alex.blog.common.global.SysConf;

/**
 *description:  级别分类枚举类
 *author:       alex
 *createDate:   2021/12/6 17:12
 *version:      1.0.0
 */
public enum ELevel {
    NORMAL(0, SysConf.BLOG_NEW_COUNT),//"正常的"
    FIRST(1, SysConf.BLOG_FIRST_COUNT),//"一级推荐"
    SECOND(2, SysConf.BLOG_SECOND_COUNT),//"二级推荐"
    THIRD(3, SysConf.BLOG_THIRD_COUNT),//"三级推荐"
    FOURTH(4, SysConf.BLOG_FOURTH_COUNT);//"四级推荐"

    private Integer code;

    private String value;

    ELevel(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

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
