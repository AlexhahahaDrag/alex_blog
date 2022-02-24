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
 * @description:  专题Item表类
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_subject_item")
@ApiModel(value = "SubjectItem对象", description = "专题Item表")
public class SubjectItem extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专题id")
    @TableField("subject_id")
    private String subjectId;

    @ApiModelProperty(value = "博客id")
    @TableField("blog_id")
    private String blogId;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;

}
