package com.alex.blog.admin.restApi.blog;


import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.CommentVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.CommentService;
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
 * 评论表 前端控制器
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
@RestController
@RequestMapping("/comment")
@Api(value = "用户评论相关接口", tags = {"用户评论相关接口"})
@RequiredArgsConstructor
public class CommentRestApi {

    private final CommentService commentService;

    @AuthorityVerify
    @ApiOperation(value = "获取评论列表", notes = "获取评论列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody CommentVo CommentVo) {
        return ResultUtil.resultSuccessWithData(commentService.getPageList(CommentVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增评论", notes = "新增评论", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增评论")
    public String addComment(@Validated({Insert.class}) @RequestBody CommentVo CommentVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return commentService.addComment(CommentVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改评论", notes = "修改评论", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改评论")
    public String editComment(@Validated({Update.class}) @RequestBody CommentVo CommentVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return commentService.editComment(CommentVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除评论", notes = "删除评论", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除评论")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "评论ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return commentService.deleteBatchComment(ids);
    }
}

