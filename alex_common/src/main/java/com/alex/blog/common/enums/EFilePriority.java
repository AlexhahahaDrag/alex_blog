package com.alex.blog.common.enums;

/**
 *description:  文件优先级枚举类
 *author:       alex
 *createDate:   2021/8/3 6:49
 *version:      1.0.0
 */
public enum EFilePriority {

    LOCAL("0", "本地文件"),
    QI_NIU("1", "七牛云存储"),
    MINIO("2", "minIO服务器")
    ;

    private String code;

    private String value;

    EFilePriority(String code, String value) {
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
