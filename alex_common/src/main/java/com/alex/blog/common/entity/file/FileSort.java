package com.alex.blog.common.entity.file;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  文件分类
 *author:       alex
 *createDate:   2021/8/9 6:57
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FileSort", description = "文件分类类")
@TableName(value = "t_file_sort")
public class FileSort extends BaseEntity<FileSort> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "projectName", name = "项目名称")
    @TableField(value = "project_name")
    private String projectName;

    @ApiModelProperty(value = "sortName", name = "分类名称")
    @TableField(value = "sort_name")
    private String sortName;

    @ApiModelProperty(value = "url", name = "存储路径")
    @TableField(value = "url")
    private String url;
}
