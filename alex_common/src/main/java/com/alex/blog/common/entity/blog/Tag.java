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
 * <p>
 * 标签表
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:48
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_tag")
@ApiModel(value = "Tag对象", description = "标签表")
public class Tag extends BaseEntity<Tag> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("点击次数")
    @TableField("click_count")
    private Integer clickCount;

    @ApiModelProperty("排序字段，越大越靠前")
    @TableField("sort")
    private Integer sort;
}
