package com.alex.blog.common.vo.admin;

import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *description:  角色视图
 *author:       alex
 *createDate:   2021/7/31 14:00
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleVo", description = "RoleVo类")
public class RoleVo extends BaseVo<RoleVo> {

    @ApiModelProperty(value = "roleName", example = "管理员")
    @NotBlank(groups = {Insert.class, Update.class})
    private String roleName;

    @ApiModelProperty(value = "summary", example = "你好啊")
    private String summary;

    @ApiModelProperty(value = "categoryMenuIds", example = "")
    private String categoryMenuIds;
}
