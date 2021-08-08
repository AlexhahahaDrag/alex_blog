package com.alex.blog.common.entity.file;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  文件实体类
 *author:       alex
 *createDate:   2021/8/7 20:27
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_file")
@ApiModel(value = "file", description = "文件类")
public class File extends BaseEntity<File> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "fileOldName", name = "文件老名字")
    private String fileOldName;

    @ApiModelProperty(value = "fileSize", name = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "fileSortId", name = "文件排序id")
    private String fileSortId;

    /**
     * 图片扩展名
     */
    @ApiModelProperty(value = "picExpandedName", name = "图片扩展名")
    private String picExpandedName;

    /**
     * 图片名称
     */
    @ApiModelProperty(value = "picName", name = "图片名称")
    private String picName;

    /**
     * 图片url地址
     */
    @ApiModelProperty(value = "picUrl", name = "图片url地址")
    private String picUrl;

    /**
     * 管理员id
     */
    @ApiModelProperty(value = "adminId", name = "管理员id")
    private String adminId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "userId", name = "用户id")
    private String userId;

    /**
     * 七牛云Url
     */
    @ApiModelProperty(value = "qiNiuUrl", name = "七牛云Url")
    private String qiNiuUrl;

    /**
     * Minio文件URL
     */
    @ApiModelProperty(value = "minioUrl", name = "Minio文件URL")
    private String minioUrl;
}
