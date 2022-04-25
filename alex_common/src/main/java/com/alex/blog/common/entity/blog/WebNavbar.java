package com.alex.blog.common.entity.blog;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  类
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_web_navbar")
@ApiModel(value = "WebNavbar对象", description = "")
public class WebNavbar extends BaseEntity<WebNavbar>{

    @TableField("`name`")
    private String name;

    @TableField("navbar_level")
    private Boolean navbarLevel;

    @TableField("summary")
    private String summary;

    @TableField("parent_uid")
    private String parentUid;

    @TableField("url")
    private String url;

    @TableField("icon")
    private String icon;

    @TableField("is_show")
    private Boolean isShow;

    @TableField("is_jump_external_url")
    private Boolean isJumpExternalUrl;

    @TableField("sort")
    private Integer sort;

}
