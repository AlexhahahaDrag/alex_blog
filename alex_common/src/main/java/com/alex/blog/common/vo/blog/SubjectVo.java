package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  专题表类
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Subject对象", description = "专题表")

public class SubjectVo extends BaseVo<SubjectVo>{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专题名称")
    private String subjectName;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "封面图片UID")
    private String fileUid;

    @ApiModelProperty(value = "专题点击数")
    private Integer clickCount;

    @ApiModelProperty(value = "专题收藏数")
    private Integer collectCount;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

}
