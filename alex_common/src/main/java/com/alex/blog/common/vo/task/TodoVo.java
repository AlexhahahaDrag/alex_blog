package com.alex.blog.common.vo.task;

import com.alex.blog.base.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代办事项表
 * </p>
 *
 * @author alex
 * @since 2022-01-26 09:51:38
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Todo对象vo", description = "代办事项表vo")
public class TodoVo extends BaseVo<TodoVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员id")
    private String adminId;

    @ApiModelProperty("内容")
    private String text;

    @ApiModelProperty("表示事项是否完成（0：未完成 1：已完成）")
    private Integer done;
}
