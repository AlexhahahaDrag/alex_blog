package com.alex.blog.admin.restApi.blog;


import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.TagVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:48
 */
@Api(value = "博客标签相关接口", tags = {"博客标签相关接口"})
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagRestApi {

    private final TagService tagService;

    @AuthorityVerify
    @ApiOperation(value = "获取标签列表", notes = "获取标签列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody TagVo tagVo) {
        return ResultUtil.resultSuccessWithData(tagService.getPageList(tagVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增标签", notes = "新增标签", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增标签")
    public String addTag(@Validated({Insert.class}) @RequestBody TagVo tagVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return tagService.addTag(tagVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改标签", notes = "修改标签", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改标签")
    public String editTag(@Validated({Update.class}) @RequestBody TagVo tagVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return tagService.editTag(tagVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除标签", notes = "删除标签", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除标签")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return tagService.deleteBatchTag(ids);
    }

    @AuthorityVerify
    @ApiOperation(value = "置顶标签", notes = "置顶标签", response = String.class)
    @PutMapping(value = "/stick")
    @OperationLogger(value = "置顶标签")
    public String stickTag(@Validated({Update.class}) @RequestBody TagVo tagVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return tagService.stickTag(tagVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "通过点击量排序标签", notes = "通过点击量排序标签", response = String.class)
    @GetMapping(value = "/tagSortByClickCount")
    @OperationLogger(value = "通过点击量排序标签")
    public String tagSortByClickCount() {
        return tagService.tagSortByClickCount();
    }

    @AuthorityVerify
    @ApiOperation(value = "通过引用量排序标签", notes = "通过引用量排序标签", response = String.class)
    @GetMapping(value = "/tagSortByCite")
    @OperationLogger(value = "通过引用量排序标签")
    public String tagSortByCite() {
        return tagService.tagSortByCite();
    }
}

