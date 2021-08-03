package com.alex.blog.common.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value = "系统配置类", description = "系统配置类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_system_config")
public class SystemConfig extends BaseEntity<SystemConfig> {

    public static final long serialVersionID = 1L;

    @ApiModelProperty(value = "qiNiuAccessKey", name = "七牛云公钥", example = "1232")
    @TableField(value = "qi_niu_access_key", updateStrategy = FieldStrategy.IGNORED)
    private String qiNiuAccessKey;

    @ApiModelProperty(value = "qiNiuSecretKey", name = "七牛云密钥", example = "1232")
    @TableField(value = "qi_niu_secret_key", updateStrategy = FieldStrategy.IGNORED)
    private String qiNiuSecretKey;

    @ApiModelProperty(value = "qiNiuBucket", name = "七牛云上传空间", example = "1232")
    @TableField(value = "qi_niu_bucket", updateStrategy = FieldStrategy.IGNORED)
    private String qiNiuBucket;

    @ApiModelProperty(value = "qiNiuArea", name = "七牛云存储区域", example = "1232")
    @TableField(value = "qi_niu_area", updateStrategy = FieldStrategy.IGNORED)
    private String qiNiuArea;

    @ApiModelProperty(value = "minioEndPoint", name = "minIO远程连接地址", example = "1232")
    @TableField(value = "minio_end_point", updateStrategy = FieldStrategy.IGNORED)
    private String minioEndPoint;

    @ApiModelProperty(value = "minioAccessKey", name = "minIO公钥", example = "1232")
    @TableField(value = "minio_access_key", updateStrategy = FieldStrategy.IGNORED)
    private String minioAccessKey;

    @ApiModelProperty(value = "minioSecretKey", name = "minIO密钥", example = "1232")
    @TableField(value = "minio_secret_key", updateStrategy = FieldStrategy.IGNORED)
    private String minioSecretKey;

    @ApiModelProperty(value = "minioBucket", name = "minIO存储空间", example = "1232")
    @TableField(value = "minio_bucket", updateStrategy = FieldStrategy.IGNORED)
    private String minioBucket;

    @ApiModelProperty(value = "uploadQiNiu", name = "图片是否上传七牛（0：否，1：是）", example = "0")
    @TableField(value = "upload_qi_niu")
    private String uploadQiNiu;

    @ApiModelProperty(value = "uploadLocal", name = "图片是否上传本地（0：否，1：是）", example = "1")
    @TableField(value = "upload_local")
    private String uploadLocal;

    @ApiModelProperty(value = "uploadMinio", name = "图片是否上传minIO（0：否，1：是）", example = "0")
    @TableField(value = "upload_minio")
    private String uploadMinio;

    @ApiModelProperty(value = "picturePriority", name = "标题图片显示优先级（0：本地，1：七牛云，2：minIO）", example = "1")
    @TableField(value = "picture_priority")
    private String picturePriority;

    @ApiModelProperty(value = "contentPicturePriority", name = "博客详情图片显示优先级（0：本地，1：七牛云，2：minIO）", example = "1")
    @TableField(value = "content_picture_priority")
    private String contentPicturePriority;

    @ApiModelProperty(value = "localPictureBaseUrl", name = "本地图片服务器地址", example = "http://localhost:8600")
    @TableField(value = "local_picture_base_url", updateStrategy = FieldStrategy.IGNORED)
    private String localPictureBaseUrl;

    @ApiModelProperty(value = "qiNiuPictureBaseUrl", name = "七牛云图片服务器地址", example = "http://images.moguit.cn")
    @TableField(value = "qi_niu_picture_base_url", updateStrategy = FieldStrategy.IGNORED)
    private String qiNiuPictureBaseUrl;

    @ApiModelProperty(value = "minioPictureBaseUrl", name = "minIO图片服务器地址", example = "http://minio.moguit.cn")
    @TableField(value = "minio_picture_base_url", updateStrategy = FieldStrategy.IGNORED)
    private String minioPictureBaseUrl;

    @ApiModelProperty(value = "email", name = "邮箱", example = "734663446@qq.com")
    @TableField(value = "email", updateStrategy = FieldStrategy.IGNORED)
    private String email;

    @ApiModelProperty(value = "emailUsername", name = "邮件发送人", example = "1232")
    @TableField(value = "email_username", updateStrategy = FieldStrategy.IGNORED)
    private String emailUsername;

    @ApiModelProperty(value = "emailPassword", name = "邮箱密码", example = "1232")
    @TableField(value = "email_password", updateStrategy = FieldStrategy.IGNORED)
    private String emailPassword;

    @ApiModelProperty(value = "smtpAddress", name = "smtp地址", example = "1232")
    @TableField(value = "smtp_address", updateStrategy = FieldStrategy.IGNORED)
    private String smtpAddress;

    @ApiModelProperty(value = "smtpPort", name = "smtp端口", example = "1232")
    @TableField(value = "smtp_port", updateStrategy = FieldStrategy.IGNORED)
    private String smtpPort;

    @ApiModelProperty(value = "startEmailNotification", name = "是否开启邮件通知(0:否， 1:是),当有新的反馈，友链申请时进行通知，首先需要在系统管理处设置接收通知的邮箱", example = "1232")
    @TableField(value = "start_email_notification")
    private String startEmailNotification;

    @ApiModelProperty(value = "editorModel", name = "编辑器模式", example = "1232")
    @TableField(value = "editor_model")
    private String editorModel;

    @ApiModelProperty(value = "themeColor", name = "主题颜色", example = "1232")
    @TableField(value = "theme_color")
    private String themeColor;

    @ApiModelProperty(value = "dashboardNotification", name = "仪表盘通知(首次进入时弹出)", example = "1232")
    @TableField(value = "dashboardNotification", updateStrategy = FieldStrategy.IGNORED)
    private String dashboardNotification;

    @ApiModelProperty(value = "openDashboardNotification", name = "是否开启仪表盘通知【0 关闭，1 开启】", example = "1")
    @TableField(value = "open_dashboard_notification")
    private String openDashboardNotification;

    @ApiModelProperty(value = "openEmailActivate", name = "是否开启用户邮件激活功能【0 关闭，1 开启】", example = "1")
    @TableField(value = "open_email_activate")
    private String openEmailActivate;
}
