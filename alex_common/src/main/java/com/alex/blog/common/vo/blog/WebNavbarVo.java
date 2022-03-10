package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  Vo
 * @author:       alex
 * @createDate:   2022-03-10 14:15:42
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "WebNavbarVo", description = "Vo")
public class WebNavbarVo extends BaseVo<WebNavbarVo>{

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
