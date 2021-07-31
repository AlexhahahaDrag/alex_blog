package com.alex.blog.common.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  角色类
 *author:       alex
 *createDate:   2021/1/20 20:14
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName("t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity<Role> {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "roleName", name = "角色", example = "管理员")
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "summary", name = "介绍", example = "你好啊")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String summary;

    /**
     * 该角色所能管辖的区域
     */
    @ApiModelProperty(value = "categoryMenuIds", name = "权限菜单列表", example = "登录")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String categoryMenuIds;
}
