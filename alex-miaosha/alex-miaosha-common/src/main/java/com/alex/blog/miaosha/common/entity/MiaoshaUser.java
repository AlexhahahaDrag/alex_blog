package com.alex.blog.miaosha.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *description:  秒杀用户
 *author:       majf
 *createDate:   2022/7/8 16:39
 *version:      1.0.0
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("miaosha_user")
@ApiModel(value = "MiaoshaUser对象", description = "")
public class MiaoshaUser {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "头信息")
    @TableField("head")
    private String head;

    @ApiModelProperty(value = "注册时间")
    @TableField("register_date")
    private Date registerDate;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_date")
    private Date lastLoginDate;

    @ApiModelProperty(value = "登录数")
    @TableField("login_count")
    private Integer loginCount;
}
