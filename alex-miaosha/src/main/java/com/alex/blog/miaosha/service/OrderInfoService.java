package com.alex.blog.xo.service.;

import com.alex.blog.common.entity..OrderInfo;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo..OrderInfoVo;
import java.util.List;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-07-04 18:03:45
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface OrderInfoService extends SuperService<OrderInfo> {

    /**
     * @param: orderInfoVo
     * @description: 获取列表
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity..OrderInfo>
     */
    IPage<OrderInfo> getPageList(OrderInfoVo orderInfoVo);

    /**
     * @param: orderInfoVo
     * @description: 新增
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String addOrderInfo(OrderInfoVo orderInfoVo);

    /**
     * @param: orderInfoVo
     * @description: 编辑
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String editOrderInfo(OrderInfoVo orderInfoVo);

    /**
     * @param ids
     * @description: 批量删除
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String deleteBatchOrderInfo(List<String> ids);
}
