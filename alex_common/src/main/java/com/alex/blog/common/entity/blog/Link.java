package com.alex.blog.common.entity.blog;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 友情链接表
 * </p>
 *
 * @author alex
 * @since 2022-01-27 17:32:37
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_link")
@ApiModel(value = "Link对象", description = "友情链接表")
public class Link extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("友情链接标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("友情链接介绍")
    @TableField("summary")
    private String summary;

    @ApiModelProperty("友情链接URL")
    @TableField("url")
    private String url;

    @ApiModelProperty("点击数")
    @TableField("click_count")
    private Integer clickCount;

    @ApiModelProperty("排序字段，越大越靠前")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("友链状态： 0 申请中， 1：已上线，  2：已下架")
    @TableField("link_status")
    private Boolean linkStatus;

    @ApiModelProperty("申请用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("操作管理员ID")
    @TableField("admin_id")
    private String adminId;

    @ApiModelProperty("站长邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("网站图标")
    @TableField("file_id")
    private String fileId;


}
