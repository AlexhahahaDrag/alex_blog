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
//    /**
//     * @param: orderInfoVo
//     * @description: 获取列表
//     * @author:      alex
//     * @createDate:  2022-07-04 18:03:45
//     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity..OrderInfo>
//     */
//    IPage<OrderInfo> getPageList(OrderInfoVo orderInfoVo);
//
//    /**
//     * @param: orderInfoVo
//     * @description: 新增
//     * @author:      alex
//     * @createDate:  2022-07-04 18:03:45
//     * @return:      java.lang.String
//     */
//    String addOrderInfo(OrderInfoVo orderInfoVo);
//
//    /**
//     * @param: orderInfoVo
//     * @description: 编辑
//     * @author:      alex
//     * @createDate:  2022-07-04 18:03:45
//     * @return:      java.lang.String
//     */
//    String editOrderInfo(OrderInfoVo orderInfoVo);
//
//    /**
//     * @param ids
//     * @description: 批量删除
//     * @author:      alex
//     * @createDate:  2022-07-04 18:03:45
//     * @return:      java.lang.String
//     */
//    String deleteBatchOrderInfo(List<String> ids);
}
