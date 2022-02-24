package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源分类表
 * </p>
 *
 * @author alex
 * @since 2022-02-15 09:59:56
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ResourceSort对象Vo", description = "资源分类表Vo")
public class ResourceSortVo extends BaseVo<ResourceSortVo> {

    @ApiModelProperty("分类图片uid")
    @TableField("file_uid")
    private String fileUid;

    @ApiModelProperty("分类名")
    @TableField("sort_name")
    private String sortName;

    @ApiModelProperty("分类介绍")
    @TableField("content")
    private String content;

    @ApiModelProperty("点击数")
    @TableField("click_count")
    private String clickCount;

    @ApiModelProperty("父ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("排序字段")
    @TableField("sort")
    private Integer sort;
}
