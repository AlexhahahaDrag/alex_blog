package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客分类表
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_blog_sort")
@ApiModel(value = "BlogSort对象", description = "博客分类表")
public class BlogSortVo extends BaseVo<BlogSortVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类内容")
    @TableField("sort_name")
    private String sortName;

    @ApiModelProperty("分类简介")
    @TableField("content")
    private String content;

    @ApiModelProperty("排序字段，越大越靠前")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("点击数")
    @TableField("click_count")
    private Integer clickCount;


}
