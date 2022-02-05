package com.alex.blog.common.entity.task;

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
 * 代办事项表
 * </p>
 *
 * @author alex
 * @since 2022-01-26 09:42:11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_todo")
@ApiModel(value = "Todo对象", description = "代办事项表")
public class Todo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("管理员id")
    @TableField("admin_id")
    private String adminId;

    @ApiModelProperty("内容")
    @TableField("`text`")
    private String text;

    @ApiModelProperty("表示事项是否完成（0：未完成 1：已完成）")
    @TableField("done")
    private Integer done;
}
