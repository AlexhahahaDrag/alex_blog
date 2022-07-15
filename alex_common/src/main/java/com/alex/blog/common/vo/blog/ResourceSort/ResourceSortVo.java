package com.alex.blog.common.vo.blog.ResourceSort;

import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  资源分类表类
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "ResourceSort对象", description = "资源分类表")

public class ResourceSortVo extends BaseVo<ResourceSortVo>{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类图片uid")
    private String fileUid;

    @ApiModelProperty(value = "分类名")
    private String sortName;

    @ApiModelProperty(value = "分类介绍")
    private String content;

    @ApiModelProperty(value = "点击数")
    private String clickCount;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

}
