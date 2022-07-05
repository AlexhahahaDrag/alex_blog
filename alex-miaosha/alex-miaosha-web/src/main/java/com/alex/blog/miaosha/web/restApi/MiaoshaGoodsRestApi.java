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
@RequestMapping("/miaosha-goods")
public class MiaoshaGoodsRestApi {

//    @Autowired
//    private MiaoshaGoodsService miaoshaGoodsService;
//
//    @AuthorityVerify
//    @ApiOperation(value = "获取列表", notes = "获取列表", response = String.class)
//    @PostMapping(value = "/getList")
//    public String getList(@Validated({GetList.class}) @RequestBody MiaoshaGoodsVo miaoshaGoodsVo) {
//        return ResultUtil.resultSuccessWithData(miaoshaGoodsService.getPageList(miaoshaGoodsVo));
//    }
//
//    @AvoidRepeatableCommit
//    @AuthorityVerify
//    @ApiOperation(value = "新增", notes = "新增", response = String.class)
//    @PutMapping(value = "/add")
//    @OperationLogger(value = "新增")
//    public String addMiaoshaGoods(@Validated({Insert.class}) @RequestBody MiaoshaGoodsVo miaoshaGoodsVo, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return miaoshaGoodsService.addMiaoshaGoods(miaoshaGoodsVo);
//    }
//
//    @AuthorityVerify
//    @ApiOperation(value = "修改", notes = "修改", response = String.class)
//    @PutMapping(value = "/edit")
//    @OperationLogger(value = "修改")
//    public String editMiaoshaGoods(@Validated({Update.class}) @RequestBody MiaoshaGoodsVo miaoshaGoodsVo, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return miaoshaGoodsService.editMiaoshaGoods(miaoshaGoodsVo);
//    }
//
//    @AuthorityVerify
//    @ApiOperation(value = "删除", notes = "删除", response = String.class)
//    @DeleteMapping(value = "/deleteBatch")
//    @OperationLogger(value = "删除")
//    public String deleteBatch(@Validated({Delete.class})
//                              @ApiParam(value = "ids", name = "ids", required = true)
//                              @RequestParam(value = "ids") List<String> ids) {
//        return miaoshaGoodsService.deleteBatchMiaoshaGoods(ids);
//    }
}
