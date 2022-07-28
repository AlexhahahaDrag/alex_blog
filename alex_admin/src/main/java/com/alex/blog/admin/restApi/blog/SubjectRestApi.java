package com.alex.blog.admin.restApi.blog;

import com.alex.blog.common.vo.blog.SubjectVo;
import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.utils.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alex.blog.xo.service.blog.SubjectService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  专题表restApi
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Api(value = "专题表相关接口", tags = {"专题表相关接口"})
@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectRestApi {

    private final SubjectService subjectService;

    @AuthorityVerify
    @ApiOperation(value = "获取专题表列表", notes = "获取专题表列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody SubjectVo subjectVo) {
        return ResultUtil.resultSuccessWithData(subjectService.getPageList(subjectVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增专题表", notes = "新增专题表", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增专题表")
    public String addSubject(@Validated({Insert.class}) @RequestBody SubjectVo subjectVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return subjectService.addSubject(subjectVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改专题表", notes = "修改专题表", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改专题表")
    public String editSubject(@Validated({Update.class}) @RequestBody SubjectVo subjectVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return subjectService.editSubject(subjectVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除专题表", notes = "删除专题表", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除专题表")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "专题表ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return subjectService.deleteBatchSubject(ids);
    }
}
