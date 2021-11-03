package com.alex.blog.common.entity.admin;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *description:  菜单表
 *author:       alex
 *createDate:   2021/10/20 7:15
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_category_menu")
@ApiModel(value = "t_category_menu", description = "菜单表")
public class CategoryMenu extends BaseEntity<CategoryMenu> implements Comparable<CategoryMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "name", example = "个人中心", name = "名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "menuLevel", example = "一级分类", name = "菜单级别")
    @TableField(value = "menu_level")
    private Integer menuLevel;

    @ApiModelProperty(value = "menuType", example = "按钮", name = "菜单类型")
    @TableField(value = "menu_type")
    private Integer menuType;

    @ApiModelProperty(value = "summary", example = "很好的功能", name = "简介")
    @TableField(value = "summary")
    private String summary;

    @ApiModelProperty(value = "icon", example = "ha", name = "icon图标")
    @TableField(value = "icon")
    private String icon;

    @ApiModelProperty(value = "pid", example = "3", name = "父级id")
    @TableField(value = "pid")
    private Integer pid;

    @ApiModelProperty(value = "url", example = "ww.baidu.com", name = "地址")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "sort", example = "3", name = "排序字段越大越靠前")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty(value = "isShow", example = "1：是，0：否", name = "是否显示")
    @TableField(value = "isShow")
    private Integer isShow;

    @ApiModelProperty(value = "isJumpExternalUrl", example = "1：是，0：否", name = "是否跳转外部url")
    @TableField(value = "isJumpExternalUrl")
    private Integer isJumpExternalUrl;

    @ApiModelProperty(value = "parentCategoryMenu", example = "", name = "父菜单")
    @TableField(exist = false)
    private CategoryMenu parentCategoryMenu;

    @ApiModelProperty(value = "childCategoryMenus", example = "", name = "子菜单")
    @TableField(exist = false)
    private List<CategoryMenu> childCategoryMenus;

    @Override
    public int compareTo(CategoryMenu o) {
        return this.sort >= o.sort ? 1 : -1;
    }
}
