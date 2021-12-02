package com.alex.blog.common.entity.blog;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author alex
 * @since 2021-11-25 16:17:08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_blog")
@ApiModel(value = "Blog对象", description = "博客表")
public class Blog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("博客简介")
    @TableField("summary")
    private String summary;

    @ApiModelProperty("博客内容")
    @TableField("content")
    private String content;

    //多标签用逗号分隔
    @ApiModelProperty("标签id")
    @TableField("tag_id")
    private String tagId;

    @ApiModelProperty("博客点击数")
    @TableField("click_count")
    private Integer clickCount;

    @ApiModelProperty("博客收藏数")
    @TableField("collect_count")
    private Integer collectCount;

    @ApiModelProperty("博客点赞数")
    @TableField("praise_count")
    private Integer praiseCount;

    @ApiModelProperty("标题图片id")
    @TableField("file_id")
    private String fileId;

    @ApiModelProperty("管理员id")
    @TableField("admin_id")
    private Integer adminId;

    @ApiModelProperty("是否原创（0:不是 1：是）")
    @TableField("is_original")
    private String isOriginal;

    @ApiModelProperty("作者")
    @TableField("author")
    private String author;

    @ApiModelProperty("文章出处")
    @TableField("articles_part")
    private String articlesPart;

    //博客分类id，多id时用逗号分隔
    @ApiModelProperty("博客分类ID")
    @TableField("blog_sort_id")
    private String blogSortId;

    @ApiModelProperty("推荐等级(0:正常)")
    @TableField("`level`")
    private Boolean level;

    @ApiModelProperty("是否发布：0：否，1：是")
    @TableField("is_publish")
    private String isPublish;

    @ApiModelProperty("排序字段")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("是否开启评论(0:否 1:是)")
    @TableField("open_comment")
    private Boolean openComment;

    @ApiModelProperty("类型【0 博客， 1：推广】")
    @TableField("`type`")
    private Boolean type;

    @ApiModelProperty("外链【如果是推广，那么将跳转到外链】")
    @TableField("outside_link")
    private String outsideLink;

    @ApiModelProperty("唯一oid")
    @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    @TableField(exist = false)
    @ApiModelProperty("标签列表")
    private List<Tag> tagList;

    @ApiModelProperty("博客分类列表")
    @TableField(exist = false)
    private List<BlogSort> blogSortList;

    @ApiModelProperty("博客分类名")
    @TableField(exist = false)
    private String blogSortName;

    @ApiModelProperty("博客标题图")
    @TableField(exist = false)
    private String photoUrl;
}
