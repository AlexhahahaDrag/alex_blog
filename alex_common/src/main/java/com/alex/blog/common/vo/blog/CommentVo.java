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
 * 评论表
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Comment对象Vo", description = "评论表")
public class CommentVo extends BaseVo<CommentVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("回复某条评论的id")
    private String toId;

    @ApiModelProperty("回复某个人的id")
    private String toUserId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("博客id")
    private String blogId;

    @ApiModelProperty("评论来源： MESSAGE_BOARD，ABOUT，BLOG_INFO 等")
    private String source;

    @ApiModelProperty("评论类型 1:点赞 0:评论")
    private String type;

    @ApiModelProperty("一级评论ID")
    private String firstCommentId;
}
