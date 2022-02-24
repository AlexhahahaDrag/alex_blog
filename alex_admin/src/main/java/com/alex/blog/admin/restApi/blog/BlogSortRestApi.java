package com.alex.blog.admin.restApi.blog;


import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.BlogSortVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.BlogSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客博客分类表 前端控制器
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:28
 */
@RestController
@RequestMapping("/blogSort")
@Api(value = "博客分类相关接口", tags = {"博客分类相关接口"})
public class BlogSortRestApi {

    @Autowired
    private BlogSortService blogSortService;

    @AuthorityVerify
    @ApiOperation(value = "获取博客分类列表", notes = "获取博客分类列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody BlogSortVo BlogSortVo) {
        return ResultUtil.resultSuccessWithData(blogSortService.getPageList(BlogSortVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增博客分类", notes = "新增博客分类", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增博客分类")
    public String addBlogSort(@Validated({Insert.class}) @RequestBody BlogSortVo BlogSortVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return blogSortService.addBlogSort(BlogSortVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改博客分类", notes = "修改博客分类", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改博客分类")
    public String editBlogSort(@Validated({Update.class}) @RequestBody BlogSortVo BlogSortVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return blogSortService.editBlogSort(BlogSortVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除博客分类", notes = "删除博客分类", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除博客分类")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "博客分类ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return blogSortService.deleteBatchBlogSort(ids);
    }

    @AuthorityVerify
    @ApiOperation(value = "置顶博客分类", notes = "置顶博客分类", response = String.class)
    @PutMapping(value = "/stick")
    @OperationLogger(value = "置顶博客分类")
    public String stickBlogSort(@Validated({Update.class}) @RequestBody BlogSortVo BlogSortVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return blogSortService.stickBlogSort(BlogSortVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "通过点击量排序博客分类", notes = "通过点击量排序博客分类", response = String.class)
    @GetMapping(value = "/BlogSortSortByClickCount")
    @OperationLogger(value = "通过点击量排序博客分类")
    public String BlogSortSortByClickCount() {
        return blogSortService.BlogSortSortByClickCount();
    }

    @AuthorityVerify
    @ApiOperation(value = "通过引用量排序博客分类", notes = "通过引用量排序博客分类", response = String.class)
    @GetMapping(value = "/BlogSortSortByCite")
    @OperationLogger(value = "通过引用量排序博客分类")
    public String BlogSortSortByCite() {
        return blogSortService.BlogSortSortByCite();
    }
}

