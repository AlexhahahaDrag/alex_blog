package com.alex.blog.admin.restApi.blog;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.ResourceSortVo;
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
import com.alex.blog.xo.service.blog.ResourceSortService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  资源分类表restApi
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Api(value = "资源分类表相关接口", tags = {"资源分类表相关接口"})
@RestController
@RequestMapping("/resource-sort")
public class ResourceSortRestApi {

    @Autowired
    private ResourceSortService resourceSortService;

    @AuthorityVerify
    @ApiOperation(value = "获取资源分类表列表", notes = "获取资源分类表列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody ResourceSortVo resourceSortVo) {
        return ResultUtil.resultSuccessWithData(resourceSortService.getPageList(resourceSortVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增资源分类表", notes = "新增资源分类表", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增资源分类表")
    public String addResourceSort(@Validated({Insert.class}) @RequestBody ResourceSortVo resourceSortVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return resourceSortService.addResourceSort(resourceSortVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改资源分类表", notes = "修改资源分类表", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改资源分类表")
    public String editResourceSort(@Validated({Update.class}) @RequestBody ResourceSortVo resourceSortVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return resourceSortService.editResourceSort(resourceSortVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除资源分类表", notes = "删除资源分类表", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除资源分类表")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "资源分类表ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return resourceSortService.deleteBatchResourceSort(ids);
    }
}
