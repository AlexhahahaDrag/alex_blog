package com.alex.blog.miaosha.web.restApi;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:  restApi
 * @author:       alex
 * @createDate: 2022-07-04 18:11:00
 * @version:      1.0.0
 */
@Api(value = "相关接口", tags = {"相关接口"})
@Controller
@RequestMapping("/order-info")
public class OrderInfoRestApi {

//    @Autowired
//    private com.alex.blog.miaoshaservice.OrderInfoService orderInfoService;
//
//    @AuthorityVerify
//    @ApiOperation(value = "获取列表", notes = "获取列表", response = String.class)
//    @PostMapping(value = "/getList")
//    public String getList(@Validated({GetList.class}) @RequestBody OrderInfoVo orderInfoVo) {
//        return ResultUtil.resultSuccessWithData(orderInfoService.getPageList(orderInfoVo));
//    }
//
//    @AvoidRepeatableCommit
//    @AuthorityVerify
//    @ApiOperation(value = "新增", notes = "新增", response = String.class)
//    @PutMapping(value = "/add")
//    @OperationLogger(value = "新增")
//    public String addOrderInfo(@Validated({Insert.class}) @RequestBody OrderInfoVo orderInfoVo, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return orderInfoService.addOrderInfo(orderInfoVo);
//    }
//
//    @AuthorityVerify
//    @ApiOperation(value = "修改", notes = "修改", response = String.class)
//    @PutMapping(value = "/edit")
//    @OperationLogger(value = "修改")
//    public String editOrderInfo(@Validated({Update.class}) @RequestBody OrderInfoVo orderInfoVo, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return orderInfoService.editOrderInfo(orderInfoVo);
//    }
//
//    @AuthorityVerify
//    @ApiOperation(value = "删除", notes = "删除", response = String.class)
//    @DeleteMapping(value = "/deleteBatch")
//    @OperationLogger(value = "删除")
//    public String deleteBatch(@Validated({Delete.class})
//                              @ApiParam(value = "ids", name = "ids", required = true)
//                              @RequestParam(value = "ids") List<String> ids) {
//        return orderInfoService.deleteBatchOrderInfo(ids);
//    }
}
