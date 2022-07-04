package com.alex.blog.miaosha.vo;

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

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
