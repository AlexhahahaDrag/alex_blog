package com.alex.blog.admin.restApi.;

import com.alex.blog.common.vo..GoodsVo;
import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.utils.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alex.blog.xo.service..GoodsService;
import org.springframework.stereotype.Controller;

/**
 * @description:  restApi
 * @author:       alex
 * @createDate: 2022-07-04 18:10:59
 * @version:      1.0.0
 */
@Api(value = "相关接口", tags = {"相关接口"})
@Controller
@RequestMapping("/goods")
public class GoodsRestApi {

    @Autowired
    private GoodsService goodsService;

    @AuthorityVerify
    @ApiOperation(value = "获取列表", notes = "获取列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody GoodsVo goodsVo) {
        return ResultUtil.resultSuccessWithData(goodsService.getPageList(goodsVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增", notes = "新增", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增")
    public String addGoods(@Validated({Insert.class}) @RequestBody GoodsVo goodsVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return goodsService.addGoods(goodsVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改", notes = "修改", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改")
    public String editGoods(@Validated({Update.class}) @RequestBody GoodsVo goodsVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return goodsService.editGoods(goodsVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除", notes = "删除", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return goodsService.deleteBatchGoods(ids);
    }
}
