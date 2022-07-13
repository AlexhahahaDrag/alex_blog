package com.alex.blog.miaosha.xo.service;

import com.alex.blog.miaosha.common.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-07-04 18:03:45
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * @param orderId
     * @description: 根据orderId查询订单信息
     * @author:      majf
     * @createDate:  2022/7/5 9:43
     * @return:      com.alex.blog.miaosha.entity.OrderInfo
    */
    OrderInfo getOrderById(long orderId);
}
