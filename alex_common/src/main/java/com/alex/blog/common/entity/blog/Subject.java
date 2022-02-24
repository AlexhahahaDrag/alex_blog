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
 * @description:  专题表类
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_subject")
@ApiModel(value = "Subject对象", description = "专题表")
public class Subject extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专题名称")
    @TableField("subject_name")
    private String subjectName;

    @ApiModelProperty(value = "简介")
    @TableField("summary")
    private String summary;

    @ApiModelProperty(value = "封面图片UID")
    @TableField("file_uid")
    private String fileUid;

    @ApiModelProperty(value = "专题点击数")
    @TableField("click_count")
    private Integer clickCount;

    @ApiModelProperty(value = "专题收藏数")
    @TableField("collect_count")
    private Integer collectCount;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;

}
