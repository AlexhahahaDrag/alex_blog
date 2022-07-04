package com.alex.blog.miaosha.restApi;

import com.alex.blog.miaosha.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *description:  订单控制类
 *author:       majf
 *createDate:   2022/7/4 17:30
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/order")
public class OrderRestApi {

    @ApiOperation(value = "获取秒杀商品信息", notes = "获取秒杀商品信息", response = Result.class)
    @GetMapping(value = "/detail")
    public Result<> detail(@RequestParam(value = "orderId") long orderId) {

    }
}
