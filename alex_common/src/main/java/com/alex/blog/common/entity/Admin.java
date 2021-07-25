package com.alex.blog.common.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 *description:  用户类
 *author:       alex
 *createDate:   2021/1/20 20:14
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "t_admin", description = "管理员表")
@TableName(value = "t_admin")
public class Admin extends BaseEntity {

    @ApiModelProperty(value = "userName", name="用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "password", name="密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "gender", name="性别(0:男1:女)")
    @TableField(value = "gender")
    private Integer gender;

    @ApiModelProperty(value = "avatar", name="个人头像")
    @TableField(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "email", name="邮箱")
    @TableField(value = "email")
    private String email;

    @ApiModelProperty(value = "birthday", name="出生年月日")
    @TableField(value = "birthday")
    @DateTimeFormat(pattern = "yyyymmdd HH:mi:ss")
    @JsonFormat(pattern = "yyyymmdd HH:mi:ss")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "mobile", name="手机")
    @TableField(value = "mobile")
    private String mobile;


    @ApiModelProperty(value = "summary", name="自我简介(最多150字)")
    @TableField(value = "summary")
    private String summary;

    @ApiModelProperty(value = "loginCount", name="登录次数")
    @TableField(value = "login_count")
    private Integer loginCount;

    @ApiModelProperty(value = "lastLoginTime", name="最后登录时间")
    @TableField(value = "last_login_time")
    @DateTimeFormat(pattern = "yyyymmdd HH:mi:ss")
    @JsonFormat(pattern = "yyyymmdd HH:mi:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "lastLoginIp", name="最后登录IP")
    @TableField(value = "last_login_ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "nickName", name="昵称")
    @TableField(value = "nick_name")
    private String nickName;

    @ApiModelProperty(value = "qqNumber", name="QQ号")
    @TableField(value = "qq_number")
    private String qqNumber;

    @ApiModelProperty(value = "weChat", name="微信号")
    @TableField(value = "we_chat")
    private String weChat;

    @ApiModelProperty(value = "occupation", name="职业")
    @TableField(value = "occupation")
    private String occupation;

    @ApiModelProperty(value = "github", name="github地址")
    @TableField(value = "github")
    private String github;

    @ApiModelProperty(value = "gitee", name="gitee地址")
    @TableField(value = "gitee")
    private String gitee;

    @ApiModelProperty(value = "roleId", name="拥有的角色uid")
    @TableField(value = "role_id")
    private String roleId;

    @ApiModelProperty(value = "personResume", name="履历")
    @TableField(value = "person_resume")
    private String personResume;


    // 以下字段不存入数据库

    /**
     * 用户头像
     */
    @TableField(exist = false)
    private List<String> photoList;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private Role role;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "validCode", name="验证码")
    @TableField(exist = false)
    private String validCode;

    /**
     * 已用网盘容量
     */
    @TableField(exist = false)
    private Long storageSize;

    /**
     * 最大网盘容量
     */
    @TableField(exist = false)
    private Long maxStorageSize;

    /**
     * 令牌ID【主要用于换取token令牌，防止token直接暴露到在线用户管理中】
     */
    @TableField(exist = false)
    private String tokenId;
}
