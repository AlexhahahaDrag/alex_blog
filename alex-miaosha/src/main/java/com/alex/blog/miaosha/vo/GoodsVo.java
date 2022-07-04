package com.alex.blog.common.vo.;

import com.alex.blog.base.vo.BaseVo;
import com.baomidou.mybatisplus.annotation.TableField;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  Vo
 * @author:       alex
 * @createDate:   2022-07-04 18:10:59
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "GoodsVo", description = "Vo")
public class GoodsVo extends BaseVo<GoodsVo>{

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "商品标题")
    @TableField("goods_title")
    private String goodsTitle;

    @ApiModelProperty(value = "商品的图片")
    @TableField("goods_img")
    private String goodsImg;

    @ApiModelProperty(value = "商品的详情介绍")
    @TableField("goods_detail")
    private String goodsDetail;

    @ApiModelProperty(value = "商品单价")
    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品库存，-1表示没有限制")
    @TableField("goods_stock")
    private Integer goodsStock;

}
