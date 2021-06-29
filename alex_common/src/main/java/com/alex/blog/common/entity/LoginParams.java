package com.alex.blog.common.entity;

import lombok.Data;

/**
 *description:  登录参数
 *author:       alex
 *createDate:   2021/6/6 14:31
 *version:      1.0.0
 */
@Data
public class LoginParams {

    private String username;

    private String password;

    private Boolean isRemember;
}
