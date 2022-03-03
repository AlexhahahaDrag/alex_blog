package com.alex.blog.common.entity.blog;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("t_resource_sort")
@ApiModel(value = "ResourceSort对象", description = "资源分类表")
public class ResourceSort extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类图片uid")
    @TableField("file_uid")
    private String fileUid;

    @ApiModelProperty(value = "分类名")
    @TableField("sort_name")
    private String sortName;

    @ApiModelProperty(value = "分类介绍")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "点击数")
    @TableField("click_count")
    private String clickCount;

    @ApiModelProperty(value = "父ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;

}
