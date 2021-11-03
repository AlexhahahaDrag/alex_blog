package com.alex.blog.common.vo.admin;

import com.alex.blog.base.vo.BaseVo;
import com.alex.blog.common.entity.admin.CategoryMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryMenuVo extends BaseVo<CategoryMenuVo> {

    @ApiModelProperty(value = "name", example = "个人中心", name = "名称")
    private String name;

    @ApiModelProperty(value = "menuLevel", example = "一级分类", name = "菜单级别")
    private Integer menuLevel;

    @ApiModelProperty(value = "menuType", example = "按钮", name = "菜单类型")
    private Integer menuType;

    @ApiModelProperty(value = "summary", example = "很好的功能", name = "简介")
    private String summary;

    @ApiModelProperty(value = "icon", example = "ha", name = "icon图标")
    private String icon;

    @ApiModelProperty(value = "pid", example = "3", name = "父级id")
    private Integer pid;

    @ApiModelProperty(value = "url", example = "ww.baidu.com", name = "地址")
    private String url;

    @ApiModelProperty(value = "sort", example = "3", name = "排序字段越大越靠前")
    private Integer sort;

    @ApiModelProperty(value = "isShow", example = "1：是，0：否", name = "是否显示")
    private Integer isShow;

    @ApiModelProperty(value = "isJumpExternalUrl", example = "1：是，0：否", name = "是否跳转外部url")
    private Integer isJumpExternalUrl;

    private CategoryMenu parentCategoryMenu;

    private List<CategoryMenu> childCategoryMenus;
}
