package com.alex.blog.common.vo.file;

import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 *description:  文件视图类
 *author:       alex
 *createDate:   2021/8/7 20:47
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "fileVo", description = "文件视图类")
public class FileVo extends BaseVo<FileVo> {

    /**
     * 如果是用户上传，则包含用户uid
     */
    @ApiModelProperty(value = "userId", name = "用户id")
    private String userId;

    /**
     * 如果是管理员上传，则包含管理员uid
     */
    @ApiModelProperty(value = "adminId", name = "管理员id")
    private String adminId;

    /**
     * 项目名
     */
    @ApiModelProperty(value = "projectName", name = "项目名")
    private String projectName;

    /**
     * 模块名
     */
    @ApiModelProperty(value = "sortName", name = "模块名")
    private String sortName;

    /**
     * 图片Url集合
     */
    @ApiModelProperty(value = "urlList", name = "图片Url集合")
    private List<String> urlList;

    /**
     * 系统配置
     */
    @ApiModelProperty(value = "systemConfig", name = "系统配置")
    private Map<String, String> systemConfig;

    /**
     * 上传图片时携带的token令牌
     */
    @ApiModelProperty(value = "token", name = "token")
    private String token;
}
