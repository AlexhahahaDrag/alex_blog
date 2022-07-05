package com.alex.blog.miaosha.xo.service.impl;

import com.alex.blog.miaosha.common.entity.OrderInfo;
import com.alex.blog.miaosha.xo.mapper.OrderInfoMapper;
import com.alex.blog.miaosha.xo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-07-04 18:03:45
 * @version:      1.0.0
 */
@Service
public class OrderInfoServiceImp extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public OrderInfo getOrderById(long orderId) {
        return this.getById(orderId);
    }
}
