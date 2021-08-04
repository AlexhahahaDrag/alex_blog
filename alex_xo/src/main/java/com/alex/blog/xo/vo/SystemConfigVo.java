package com.alex.blog.xo.vo;

import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  系统配置类
 *author:       alex
 *createDate:   2021/8/2 6:37
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统配置视图类", description = "系统配置视图类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfigVo extends BaseVo<SystemConfigVo> {


    @ApiModelProperty(value = "qiNiuAccessKey", name = "七牛云公钥", example = "1232")
    private String qiNiuAccessKey;

    @ApiModelProperty(value = "qiNiuSecretKey", name = "七牛云密钥", example = "1232")
    private String qiNiuSecretKey;

    @ApiModelProperty(value = "qiNiuBucket", name = "七牛云上传空间", example = "1232")
    private String qiNiuBucket;

    @ApiModelProperty(value = "qiNiuArea", name = "七牛云存储区域", example = "1232")
    private String qiNiuArea;

    @ApiModelProperty(value = "minioEndPoint", name = "minIO远程连接地址", example = "1232")
    private String minioEndPoint;

    @ApiModelProperty(value = "minioAccessKey", name = "minIO公钥", example = "1232")
    private String minioAccessKey;

    @ApiModelProperty(value = "minioSecretKey", name = "minIO密钥", example = "1232")
    private String minioSecretKey;

    @ApiModelProperty(value = "minioBucket", name = "minIO存储空间", example = "1232")
    private String minioBucket;

    @ApiModelProperty(value = "uploadQiNiu", name = "图片是否上传七牛（0：否，1：是）", example = "0")
    private String uploadQiNiu;

    @ApiModelProperty(value = "uploadLocal", name = "图片是否上传本地（0：否，1：是）", example = "1")
    private String uploadLocal;

    @ApiModelProperty(value = "uploadMinio", name = "图片是否上传minIO（0：否，1：是）", example = "0")
    private String uploadMinio;

    @ApiModelProperty(value = "picturePriority", name = "标题图片显示优先级（0：本地，1：七牛云，2：minIO）", example = "1")
    private String picturePriority;

    @ApiModelProperty(value = "contentPicturePriority", name = "博客详情图片显示优先级（0：本地，1：七牛云，2：minIO）", example = "1")
    private String contentPicturePriority;

    @ApiModelProperty(value = "localPictureBaseUrl", name = "本地图片服务器地址", example = "http://localhost:8600")
    private String localPictureBaseUrl;

    @ApiModelProperty(value = "qiNiuPictureBaseUrl", name = "七牛云图片服务器地址", example = "http://images.moguit.cn")
    private String qiNiuPictureBaseUrl;

    @ApiModelProperty(value = "minioPictureBaseUrl", name = "minIO图片服务器地址", example = "http://minio.moguit.cn")
    private String minioPictureBaseUrl;

    @ApiModelProperty(value = "email", name = "邮箱", example = "734663446@qq.com")
    private String email;

    @ApiModelProperty(value = "emailUsername", name = "邮件发送人", example = "1232")
    private String emailUsername;

    @ApiModelProperty(value = "emailPassword", name = "邮箱密码", example = "1232")
    private String emailPassword;

    @ApiModelProperty(value = "smtpAddress", name = "smtp地址", example = "1232")
    private String smtpAddress;

    @ApiModelProperty(value = "smtpPort", name = "smtp端口", example = "1232")
    private String smtpPort;

    @ApiModelProperty(value = "startEmailNotification", name = "是否开启邮件通知(0:否， 1:是),当有新的反馈，友链申请时进行通知，首先需要在系统管理处设置接收通知的邮箱", example = "1232")
    private String startEmailNotification;

    @ApiModelProperty(value = "editorModel", name = "编辑器模式", example = "1232")
    private String editorModel;

    @ApiModelProperty(value = "themeColor", name = "主题颜色", example = "1232")
    private String themeColor;

    @ApiModelProperty(value = "dashboardNotification", name = "仪表盘通知(首次进入时弹出)", example = "1232")
    private String dashboardNotification;

    @ApiModelProperty(value = "openDashboardNotification", name = "是否开启仪表盘通知【0 关闭，1 开启】", example = "1")
    private String openDashboardNotification;

    @ApiModelProperty(value = "openEmailActivate", name = "是否开启用户邮件激活功能【0 关闭，1 开启】", example = "1")
    private String openEmailActivate;
}
