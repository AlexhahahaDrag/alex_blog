package com.alex.blog.miaosha.common.vo;

import com.alex.blog.miaosha.common.entity.Goods;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class GoodsVo extends Goods {

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
