package com.alex.blog.common.vo.sysParams;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author alex
 * @since 2021-12-06 16:10:18
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "SysParams对象vo", description = "参数配置表vo")
public class SysParamsVo extends BaseVo<SysParamsVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("配置类型 是否系统内置(1:，是 0:否)")
    private String paramsType;

    @ApiModelProperty("参数名称")
    private String paramsName;

    @ApiModelProperty("参数键名")
    private String paramsKey;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("参数键值")
    private String paramsValue;

    @ApiModelProperty("排序字段")
    private Integer sort;
}
