package com.alex.blog.miaosha.web.restApi;

import com.alex.blog.miaosha.common.common.Result;
import com.alex.blog.miaosha.common.entity.OrderInfo;
import com.alex.blog.miaosha.common.enums.ResultEnum;
import com.alex.blog.miaosha.common.vo.GoodsVo;
import com.alex.blog.miaosha.common.vo.OrderDetailVo;
import com.alex.blog.miaosha.xo.service.GoodsService;
import com.alex.blog.miaosha.xo.service.OrderInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *description:  订单控制类
 *author:       majf
 *createDate:   2022/7/4 17:30
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/order")
public class OrderRestApi {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "获取秒杀商品信息", notes = "获取秒杀商品信息", response = Result.class)
    @GetMapping(value = "/detail")
    public Result<OrderDetailVo> detail(@RequestParam(value = "orderId") long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderById(orderId);
        if (orderInfo == null) {
            return Result.error(ResultEnum.ORDER_NOT_EXISTS.getCode(), ResultEnum.ORDER_NOT_EXISTS.getValue());
        }
        GoodsVo goodsInfo = goodsService.getGoodsVoByGoodsId(orderInfo.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(orderInfo);
        orderDetailVo.setGoods(goodsInfo);
        return Result.success(orderDetailVo);
    }
}
