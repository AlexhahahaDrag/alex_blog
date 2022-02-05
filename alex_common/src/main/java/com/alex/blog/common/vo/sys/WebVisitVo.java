package com.alex.blog.common.vo.sys;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * Web访问记录表
 * </p>
 *
 * @author alex
 * @since 2022-01-26 17:27:16
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "WebVisit对象vo", description = "Web访问记录表vo")
public class WebVisitVo extends BaseVo<WebVisitVo> {

    @ApiModelProperty("用户id")
    private String adminId;

    @ApiModelProperty("访问ip地址")
    private String ip;

    @ApiModelProperty("用户行为")
    private String behavior;

    @ApiModelProperty("模块id（文章id，标签id，分类id）")
    private String moduleId;

    @ApiModelProperty("附加数据(比如搜索内容)")
    private String otherData;

    @ApiModelProperty("操作系统")
    private String os;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("ip来源")
    private String ipSource;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
