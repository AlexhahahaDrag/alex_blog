package com.alex.blog.common.entity.;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @description:  类
 * @author:       alex
 * @createDate: 2022-07-04 18:11:00
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("miaosha_goods")
@ApiModel(value = "MiaoshaGoods对象", description = "")
public class MiaoshaGoods extends BaseEntity<MiaoshaGoods>{

    @ApiModelProperty(value = "商品Id")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty(value = "秒杀价")
    @TableField("miaosha_price")
    private BigDecimal miaoshaPrice;

    @ApiModelProperty(value = "库存数量")
    @TableField("stock_count")
    private Integer stockCount;

    @ApiModelProperty(value = "秒杀开始时间")
    @TableField("start_date")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "秒杀结束时间")
    @TableField("end_date")
    private LocalDateTime endDate;

}
