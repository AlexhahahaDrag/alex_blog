package com.alex.blog.xo.blog.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.alex.blog.common.entity.admin.Admin;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 反馈表
 * </p>
 *
 * @author alex
 * @since 2022-02-14 16:38:58
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_feedback")
@ApiModel(value = "Feedback对象", description = "反馈表")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("反馈的内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("反馈状态： 0：已开启  1：进行中  2：已完成  3：已拒绝")
    @TableField("feedback_status")
    private Boolean feedbackStatus;

    @ApiModelProperty("回复")
    @TableField("reply")
    private String reply;

    @ApiModelProperty("管理员id")
    @TableField("admin_id")
    private String adminId;

    @ApiModelProperty("用户信息")
    @TableField(exist = false)
    private Admin user;
}
