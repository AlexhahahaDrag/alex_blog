package com.alex.blog.xo.entity;

import lombok.Data;

/**
 *description:  当前在线管理员
 *author:       alex
 *createDate:   2021/7/11 21:45
 *version:      1.0.0
 */
@Data
public class OnlineAdmin {

    //会话编号
    private String tokenId;

    //用户token
    private String token;

    //管理员id
    private String adminId;

    //用户名
    private String username;

    //登录ip地址
    private String ipAddr;

    //登陆地址
    private String loginLocation;

    //浏览器类型
    private String browser;

    //操作系统
    private String os;

    //角色名
    private String roleName;

    //登陆时间
    private String loginTime;

    //过期时间
    private String expireTime;
}
