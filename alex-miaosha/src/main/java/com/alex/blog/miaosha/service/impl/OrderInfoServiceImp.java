package com.alex.blog.xo.service..impl;

import com.alex.blog.common.entity..OrderInfo;
import com.alex.blog.xo.mapper..OrderInfoMapper;
import com.alex.blog.xo.service..OrderInfoService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import org.springframework.beans.BeanUtils;
import java.util.List;
import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.vo..OrderInfoVo;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-07-04 18:03:45
 * @version:      1.0.0
 */
@Service
public class OrderInfoServiceImp extends SuperServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public IPage<OrderInfo> getPageList(OrderInfoVo orderInfoVo) {
        QueryWrapper<OrderInfo> query = getQuery();
        Page<OrderInfo> page = new Page<>();
        page.setCurrent(orderInfoVo.getCurrentPage());
        page.setSize(orderInfoVo.getPageSize());
        Page<OrderInfo> orderInfoPage = this.page(page, query);
        return orderInfoPage;
    }

    @Override
    public String addOrderInfo(OrderInfoVo orderInfoVo) {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfoVo, orderInfo);
        orderInfo.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editOrderInfo(OrderInfoVo orderInfoVo) {
        QueryWrapper<OrderInfo> query = getQuery();
        query.eq(SysConf.ID, orderInfoVo.getId());
        OrderInfo orderInfo = this.getOne(query);
        if (orderInfo == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(orderInfoVo, orderInfo);
        orderInfo.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchOrderInfo(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<OrderInfo> getQuery() {
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
