package com.alex.blog.xo.sys.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author alex
 * @since 2022-01-27 10:13:44
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

      @ApiModelProperty("标签id")
      @TableField("tag_id")
    private Integer tagId;

      @ApiModelProperty("博客点击数")
      @TableField("click_count")
    private Integer clickCount;

      @ApiModelProperty("博客收藏数")
      @TableField("collect_count")
    private Integer collectCount;

      @ApiModelProperty("标题图片uid")
      @TableField("file_id")
    private Integer fileId;

      @ApiModelProperty("状态")
      @TableField("`status`")
    private Integer status;

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

      @ApiModelProperty("博客分类ID")
      @TableField("blog_sort_id")
    private Integer blogSortId;

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


}
