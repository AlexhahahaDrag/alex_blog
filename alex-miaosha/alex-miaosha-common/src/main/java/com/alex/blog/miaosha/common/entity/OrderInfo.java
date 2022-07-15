package com.alex.blog.miaosha.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description:  类
 * @author:       alex
 * @createDate: 2022-07-04 18:11:00
 * @version:      1.0.0
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_info")
@ApiModel(value = "OrderInfo对象", description = "")
public class OrderInfo {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty(value = "收获地址ID")
    @TableField("delivery_addr_id")
    private Long deliveryAddrId;

    @ApiModelProperty(value = "冗余过来的商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "商品数量")
    @TableField("goods_count")
    private Integer goodsCount;

    @ApiModelProperty(value = "商品单价")
    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "1pc，2android，3ios")
    @TableField("order_channel")
    private Integer orderChannel;

    @ApiModelProperty(value = "订单的创建时间")
    @TableField("create_date")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "支付时间")
    @TableField("pay_date")
    private LocalDateTime payDate;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;
}
