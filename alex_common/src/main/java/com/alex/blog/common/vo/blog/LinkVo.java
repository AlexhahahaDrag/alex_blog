package com.alex.blog.common.vo.blog;

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
@ApiModel(value = "Link对象Vo", description = "友情链接表Vo")
public class LinkVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("友情链接标题")
    private String title;

    @ApiModelProperty("友情链接介绍")
    private String summary;

    @ApiModelProperty("友情链接URL")
    private String url;

    @ApiModelProperty("点击数")
    private Integer clickCount;

    @ApiModelProperty("排序字段，越大越靠前")
    private Integer sort;

    @ApiModelProperty("友链状态： 0 申请中， 1：已上线，  2：已下架")
    private Boolean linkStatus;

    @ApiModelProperty("申请用户ID")
    private String userId;

    @ApiModelProperty("操作管理员ID")
    private String adminId;

    @ApiModelProperty("站长邮箱")
    private String email;

    @ApiModelProperty("网站图标")
    private String fileId;
}
