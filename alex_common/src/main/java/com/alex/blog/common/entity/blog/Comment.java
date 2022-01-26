package com.alex.blog.common.entity.blog;

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
 * 评论表
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Comment对象", description = "评论表")
@TableName("t_comment")
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("回复某条评论的id")
    @TableField("to_id")
    private String toId;

    @ApiModelProperty("回复某个人的id")
    @TableField("to_user_id")
    private String toUserId;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("博客id")
    @TableField("blog_id")
    private String blogId;

    @ApiModelProperty("评论来源： MESSAGE_BOARD，ABOUT，BLOG_INFO 等")
    @TableField("source")
    private String source;

    @ApiModelProperty("评论类型 1:点赞 0:评论")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty("一级评论ID")
    @TableField("first_comment_id")
    private String firstCommentId;

    @ApiModelProperty("评论来源名称")
    @TableField(exist = false)
    private String sourceName;

    @ApiModelProperty("评论的用户")
    @TableField(exist = false)
    private Admin user;

    @ApiModelProperty("本条博客对哪一条评论说")
    @TableField(exist = false)
    private Admin toUser;

    @ApiModelProperty("博客信息")
    @TableField(exist = false)
    private Blog blog;
}
