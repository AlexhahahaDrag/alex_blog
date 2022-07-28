package com.alex.blog.admin.restApi.blog;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.BlogVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.BlogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *description:  博客接口 restApi
 *author:       alex
 *createDate:   2021/11/25 15:42
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/blog")
@Api(value = "博客相关接口", tags = {"博客相关接口"})
@Slf4j
@RequiredArgsConstructor
public class BlogRestApi {

    private final BlogService blogService;

    @AuthorityVerify
    @ApiOperation(value = "获取博客列表", notes = "获取博客列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody BlogVo blogVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return ResultUtil.resultSuccessWithData(blogService.getPageList(blogVo));
    }

    @AuthorityVerify
    @AvoidRepeatableCommit
    @OperationLogger(value = "新增博客")
    @ApiOperation(value = "新增博客", notes = "新增博客", response = String.class)
    @PostMapping(value = "/add")
    public String addBlog(@Validated({Insert.class}) @RequestBody BlogVo blogVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return blogService.addBlog(blogVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "本地博客上传", notes = "本地博客上传", response = String.class)
    @OperationLogger(value = "本地博客上传")
    @PostMapping(value = "/uploadLocalBlog")
    public String uploadPicture(@RequestBody List<MultipartFile> fileList) throws IOException {
        return blogService.uploadLocalBlog(fileList);
    }

    @AuthorityVerify
    @OperationLogger(value = "修改博客")
    @ApiOperation(value = "编辑博客", notes = "编辑博客", response = String.class)
    @PostMapping(value = "/edit")
    public String editBlog(@Validated({Update.class}) @RequestBody BlogVo blogVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return blogService.editBlog(blogVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "推荐博客排序调整")
    @ApiOperation(value = "推荐博客排序调整", notes = "推荐博客排序调整", response = String.class)
    @PostMapping(value = "/editBatch")
    public String editBlogBatch(@RequestBody List<BlogVo> blogVoList) {
        return blogService.editBatch(blogVoList);
    }

    @AuthorityVerify
    @OperationLogger(value = "删除博客")
    @ApiOperation(value = "删除博客", notes = "删除博客", response = String.class)
    @DeleteMapping(value = "/delete")
    public String deleteBlog(@ApiParam(value = "id", required =true) @RequestParam(value = "id") Long id) {
        return blogService.deleteBlog(id);
    }

    @AuthorityVerify
    @OperationLogger(value = "批量删除博客")
    @ApiOperation(value = "批量删除博客", notes = "批量删除博客", response = String.class)
    @PostMapping(value = "/deleteBatch")
    public String deleteBlogBatch(@RequestBody List<Long> blogIds) {
        return blogService.deleteBatchBlog(blogIds);
    }

    @ApiOperation(value = "查询博客", notes = "查询博客", response = String.class)
    @GetMapping(value = "/getBlogById")
    public Blog getBlogById(@ApiParam(value = "id", required = true) @RequestParam(value = "id") Long id) {
        return blogService.getBlogById(id);
    }

    @ApiOperation(value = "根据关键字查询博客信息", notes = "根据关键字查询博客信息", response = String.class)
    @GetMapping(value = "/getBlogPage")
    public IPage<Blog> getBlogPage(@ApiParam(value = "currentPage", required = true) @RequestParam(value = "currentPage") Long currentPage,
                                   @ApiParam(value = "pageSize", required = true) @RequestParam(value = "pageSize") Long pageSize) {
        return blogService.searchBlogByType(currentPage, pageSize, null, null);
    }
}
