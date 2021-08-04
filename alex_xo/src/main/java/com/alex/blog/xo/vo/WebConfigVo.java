package com.alex.blog.xo.vo;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "webConfigVo", description = "网站配置视图类")
public class WebConfigVo extends BaseVo<WebConfigVo> {

    @ApiModelProperty(value = "logo", name = "网站logo", example = "11")
    private String logo;

    @ApiModelProperty(value = "name", name = "网站名称", example = "alex_blog")
    private String name;

    @ApiModelProperty(value = "title", name = "标题", example = "11")
    private String title;

    @ApiModelProperty(value = "summary", name = "描述", example = "很好的网站")
    private String summary;

    @ApiModelProperty(value = "keyword", name = "关键字", example = "fight")
    private String keyword;

    @ApiModelProperty(value = "author", name = "作者", example = "alex")
    private String author;

    @ApiModelProperty(value = "recordNum", name = "备案号", example = "11")
    private String recordNum;

    @ApiModelProperty(value = "aliPay", name = "阿里支付宝码", example = "11")
    private String aliPay;

    @ApiModelProperty(value = "weChatPay", name = "微信支付码", example = "11")
    private String weChatPay;

    @ApiModelProperty(value = "linkApplyTemplate", name = "友链申请模板")
    private String linkApplyTemplate;

    @ApiModelProperty(value = "openComment", name = "是否开启网页端评论（0：否，1：是）", example = "0")
    private String openComment;

    @ApiModelProperty(value = "openPhoneComment", name = "是否开启移动端评论（0：否，1：是）", example = "0")
    private String openPhoneComment;

    @ApiModelProperty(value = "openAdmiration", name = "是否开启网页端赞赏（0：否，1：是）", example = "0")
    private String openAdmiration;

    @ApiModelProperty(value = "openPhoneAdmiration", name = "是否开启移动端赞赏（0：否，1：是）", example = "0")
    private String openPhoneAdmiration;

    @ApiModelProperty(value = "github", name = "github", example = "alex_hahaha")
    private String github;

    @ApiModelProperty(value = "gitee", name = "gitee", example = "alex_hahaha")
    private String gitee;

    @ApiModelProperty(value = "qqNumber", name = "qq号", example = "734663446")
    private String qqNumber;

    @ApiModelProperty(value = "qqGroup", name = "qq群组", example = "283454")
    private String qqGroup;

    @ApiModelProperty(value = "weChat", name = "微信", example = "alex_hahaha")
    private String weChat;

    @ApiModelProperty(value = "email", name = "邮箱", example = "734663446@qq.com")
    private String email;

    @ApiModelProperty(value = "showList", name = "展示列表", example = "7")
    private String showList;

    @ApiModelProperty(value = "loginTypeList", name = "登陆类型列表", example = "qq")
    private String loginTypeList;

    @ApiModelProperty(value = "photoList", name = "标题列表", example = "1")
    private List<String> photoList;

    @ApiModelProperty(value = "logoPhoto", name = "logo图片", example = "1")
    private String logoPhoto;

    @ApiModelProperty(value = "aliPayPhoto", name = "支付宝二维码", example = "1")
    private String aliPayPhoto;

    @ApiModelProperty(value = "weChatPhoto", name = "微信二维码", example = "1")
    private String weChatPhoto;
}
