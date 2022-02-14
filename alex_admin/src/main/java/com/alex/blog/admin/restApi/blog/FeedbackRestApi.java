package com.alex.blog.admin.restApi.blog;


import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.blog.FeedbackVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.blog.FeedbackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author alex
 * @since 2022-02-14 16:38:58
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackRestApi {

    @Autowired
    private FeedbackService feedbackService;

    @AuthorityVerify
    @ApiOperation(value = "获取友情链接列表", notes = "获取友情链接列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody FeedbackVo FeedbackVo) {
        return ResultUtil.resultSuccessWithData(feedbackService.getPageList(FeedbackVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增友情链接", notes = "新增友情链接", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增友情链接")
    public String addFeedback(@Validated({Insert.class}) @RequestBody FeedbackVo FeedbackVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return feedbackService.addFeedback(FeedbackVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改友情链接", notes = "修改友情链接", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改友情链接")
    public String editFeedback(@Validated({Update.class}) @RequestBody FeedbackVo FeedbackVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return feedbackService.editFeedback(FeedbackVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除友情链接", notes = "删除友情链接", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除友情链接")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "友情链接ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return feedbackService.deleteBatchFeedback(ids);
    }

    @AuthorityVerify
    @ApiOperation(value = "置顶友情链接", notes = "置顶友情链接", response = String.class)
    @PutMapping(value = "/stick")
    @OperationLogger(value = "置顶友情链接")
    public String stickFeedback(@Validated({Update.class}) @RequestBody FeedbackVo FeedbackVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return feedbackService.stickFeedback(FeedbackVo);
    }
}
