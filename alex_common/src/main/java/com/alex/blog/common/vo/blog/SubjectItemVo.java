package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
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
@ApiModel(value = "SubjectItem对象", description = "专题Item表")

public class SubjectItemVo extends BaseVo<SubjectItemVo>{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专题id")
    private String subjectId;

    @ApiModelProperty(value = "博客id")
    private String blogId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

}
