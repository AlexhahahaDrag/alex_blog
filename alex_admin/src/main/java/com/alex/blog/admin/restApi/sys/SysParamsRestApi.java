package com.alex.blog.admin.restApi.sys;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.sys.SysParamsVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.sys.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "参数配置相关接口", tags = {"参数配置相关接口"})
@RequestMapping(value = "/sysParam")
@RequiredArgsConstructor
public class SysParamsRestApi {
    
    private final SysParamsService sysParamsService;

    @AuthorityVerify
    @ApiOperation(value = "获取参数配置列表", notes = "获取参数配置列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody SysParamsVo sysParamsVo) {
        return ResultUtil.resultSuccessWithData(sysParamsService.getPageList(sysParamsVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增参数配置", notes = "新增参数配置", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增参数配置")
    public String addSysParams(@Validated({Insert.class}) @RequestBody SysParamsVo sysParamsVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return sysParamsService.addSysParams(sysParamsVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改参数配置", notes = "修改参数配置", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改参数配置")
    public String editSysParams(@Validated({Update.class}) @RequestBody SysParamsVo sysParamsVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return sysParamsService.editSysParams(sysParamsVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除参数配置", notes = "删除参数配置", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除参数配置")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "参数配置ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return sysParamsService.deleteBatchSysParams(ids);
    }
}