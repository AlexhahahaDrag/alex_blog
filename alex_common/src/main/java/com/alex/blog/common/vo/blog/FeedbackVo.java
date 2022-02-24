package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "Feedback对象vo", description = "反馈表vo")
public class FeedbackVo extends BaseVo<FeedbackVo> {

    @ApiModelProperty("用户uid")
    @TableField("user_uid")
    private String userUid;

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
}
