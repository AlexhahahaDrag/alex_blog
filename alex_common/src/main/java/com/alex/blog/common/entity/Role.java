package com.alex.blog.common.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *description:  角色类
 *author:       alex
 *createDate:   2021/1/20 20:14
 *version:      1.0.0
 */
@TableName("t_role")
public class Role extends BaseEntity<Role> {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 介绍
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String summary;

    /**
     * 该角色所能管辖的区域
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String categoryMenuUids;
}
