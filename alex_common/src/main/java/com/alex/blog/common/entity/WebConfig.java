package com.alex.blog.common.entity;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *description:  网站配置类
 *author:       alex
 *createDate:   2021/8/1 19:45
 *version:      1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_web_config")
@ApiModel(value = "webConfig", description = "webConfig配置表")
public class WebConfig extends BaseEntity<WebConfig> {

    private static final long serialVersionID = 1L;

    @ApiModelProperty(value = "logo", name = "网站logo", example = "11")
    @TableField(value = "logo", updateStrategy = FieldStrategy.IGNORED)
    private String logo;

    @ApiModelProperty(value = "name", name = "网站名称", example = "alex_blog")
    @TableField(value = "name", updateStrategy = FieldStrategy.IGNORED)
    private String name;

    @ApiModelProperty(value = "title", name = "标题", example = "11")
    @TableField(value = "title", updateStrategy = FieldStrategy.IGNORED)
    private String title;

    @ApiModelProperty(value = "summary", name = "描述", example = "很好的网站")
    @TableField(value = "summary", updateStrategy = FieldStrategy.IGNORED)
    private String summary;

    @ApiModelProperty(value = "keyword", name = "关键字", example = "fight")
    @TableField(value = "keyword", updateStrategy = FieldStrategy.IGNORED)
    private String keyword;

    @ApiModelProperty(value = "author", name = "作者", example = "alex")
    @TableField(value = "author", updateStrategy = FieldStrategy.IGNORED)
    private String author;

    @ApiModelProperty(value = "recordNum", name = "备案号", example = "11")
    @TableField(value = "record_num", updateStrategy = FieldStrategy.IGNORED)
    private String recordNum;

    @ApiModelProperty(value = "aliPay", name = "阿里支付宝码", example = "11")
    @TableField(value = "ali_pay", updateStrategy = FieldStrategy.IGNORED)
    private String aliPay;

    @ApiModelProperty(value = "weChatPay", name = "微信支付码", example = "11")
    @TableField(value = "we_chat_pay", updateStrategy = FieldStrategy.IGNORED)
    private String weChatPay;

    @ApiModelProperty(value = "openComment", name = "是否开启网页端评论（0：否，1：是）", example = "0")
    @TableField(value = "open_comment")
    private String openComment;

    @ApiModelProperty(value = "openPhoneComment", name = "是否开启移动端评论（0：否，1：是）", example = "0")
    @TableField(value = "open_phone_comment")
    private String openPhoneComment;

    @ApiModelProperty(value = "openAdmiration", name = "是否开启网页端赞赏（0：否，1：是）", example = "0")
    @TableField(value = "open_admiration")
    private String openAdmiration;

    @ApiModelProperty(value = "openPhoneAdmiration", name = "是否开启移动端赞赏（0：否，1：是）", example = "0")
    @TableField(value = "open_phone_admiration")
    private String openPhoneAdmiration;

    @ApiModelProperty(value = "github", name = "github", example = "alex_hahaha")
    @TableField(value = "github", updateStrategy = FieldStrategy.IGNORED)
    private String github;

    @ApiModelProperty(value = "gitee", name = "gitee", example = "alex_hahaha")
    @TableField(value = "gitee", updateStrategy = FieldStrategy.IGNORED)
    private String gitee;

    @ApiModelProperty(value = "qqNumber", name = "qq号", example = "734663446")
    @TableField(value = "qqNumber", updateStrategy = FieldStrategy.IGNORED)
    private String qqNumber;

    @ApiModelProperty(value = "qqGroup", name = "qq群组", example = "283454")
    @TableField(value = "qqGroup", updateStrategy = FieldStrategy.IGNORED)
    private String qqGroup;

    @ApiModelProperty(value = "weChat", name = "微信", example = "alex_hahaha")
    @TableField(value = "weChat", updateStrategy = FieldStrategy.IGNORED)
    private String weChat;

    @ApiModelProperty(value = "email", name = "邮箱", example = "734663446@qq.com")
    @TableField(value = "email", updateStrategy = FieldStrategy.IGNORED)
    private String email;

    @ApiModelProperty(value = "showList", name = "展示列表", example = "7")
    @TableField(value = "show_list")
    private String showList;

    @ApiModelProperty(value = "loginTypeList", name = "登陆类型列表", example = "qq")
    @TableField(value = "login_type_list")
    private String loginTypeList;

    @ApiModelProperty(value = "photoList", name = "标题列表", example = "1")
    @TableField(exist = false)
    private List<String> photoList;

    @ApiModelProperty(value = "logoPhoto", name = "logo图片", example = "1")
    @TableField(exist = false)
    private String logoPhoto;

    @ApiModelProperty(value = "aliPayPhoto", name = "支付宝二维码", example = "1")
    @TableField(exist = false)
    private String aliPayPhoto;

    @ApiModelProperty(value = "weChatPhoto", name = "微信二维码", example = "1")
    @TableField(exist = false)
    private String weChatPhoto;
}
