package com.alex.blog.admin.restApi.blog;

import com.alex.blog.common.vo.blog.SubjectItem.SubjectItemVo;
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
import com.alex.blog.xo.service.blog.SubjectItemService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  专题Item表restApi
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Api(value = "专题Item表相关接口", tags = {"专题Item表相关接口"})
@RestController
@RequestMapping("/subject-item")
public class SubjectItemRestApi {

    @Autowired
    private SubjectItemService subjectItemService;

    @AuthorityVerify
    @ApiOperation(value = "获取反馈列表", notes = "获取反馈列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody SubjectItemVo subjectItemVo) {
        return ResultUtil.resultSuccessWithData(subjectItemService.getPageList(subjectItemVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增反馈", notes = "新增反馈", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增反馈")
    public String addSubjectItem(@Validated({Insert.class}) @RequestBody SubjectItemVo subjectItemVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return subjectItemService.addSubjectItem(subjectItemVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改反馈", notes = "修改反馈", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改反馈")
    public String editSubjectItem(@Validated({Update.class}) @RequestBody SubjectItemVo subjectItemVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return subjectItemService.editSubjectItem(subjectItemVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除反馈", notes = "删除反馈", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除反馈")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "反馈ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return subjectItemService.deleteBatchSubjectItem(ids);
    }
}
