package com.alex.blog.common.vo.blog;

import com.alex.blog.base.vo.BaseVo;
import com.alex.blog.common.entity.blog.BlogSort;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@ApiModel(value = "Blog对象", description = "博客表")
public class BlogVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客简介")
    private String summary;

    @ApiModelProperty("博客内容")
    private String content;

    //多标签用逗号分隔
    @ApiModelProperty("标签id")
    private String tagId;

    @ApiModelProperty("博客点击数")
    private Integer clickCount;

    @ApiModelProperty("博客收藏数")
    private Integer collectCount;

    @ApiModelProperty("博客点赞数")
    private Integer praiseCount;

    @ApiModelProperty("标题图片id")
    private String fileId;

    @ApiModelProperty("管理员id")
    private String adminId;

    @ApiModelProperty("是否原创（0:不是 1：是）")
    private String isOriginal;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("文章出处")
    private String articlesPart;

    //博客分类id，多id时用逗号分隔
    @ApiModelProperty("博客分类ID")
    private String blogSortId;

    @ApiModelProperty("推荐等级(0:正常)")
    private Integer level;

    @ApiModelProperty("是否发布：0：否，1：是")
    private String isPublish;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("是否开启评论(0:否 1:是)")
    private Boolean openComment;

    @ApiModelProperty("类型【0 博客， 1：推广】")
    private Boolean type;

    @ApiModelProperty("外链【如果是推广，那么将跳转到外链】")
    private String outsideLink;

    @ApiModelProperty("唯一oid")
//    @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    @ApiModelProperty("标签列表")
    private List<Tag> tagList;

    @ApiModelProperty("博客分类列表")
    private List<BlogSort> blogSortList;

    @ApiModelProperty("博客分类名")
    private String blogSortName;

    @ApiModelProperty("博客标题图")
    private String photoUrl;
}
