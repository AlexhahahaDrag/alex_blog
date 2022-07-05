package com.alex.blog.miaosha.common.vo;

import com.alex.blog.miaosha.common.entity.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *description:  商品信息明细视图
 *author:       majf
 *createDate:   2022/7/4 17:44
 *version:      1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private GoodsVo goods;

    private OrderInfo order;
}
