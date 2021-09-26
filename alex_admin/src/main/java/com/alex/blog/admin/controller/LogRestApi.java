package com.alex.blog.admin.controller;

import com.alex.blog.admin.annotion.AuthorityVerify;
import com.alex.blog.admin.global.SysConf;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.log.SysLogVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *description:  日志记录rest api
 *author:       alex
 *createDate:   2021/9/26 19:54
 *version:      1.0.0
 */
@RestController
@Api(value = "操作日志相关接口", tags = {"操作日志相关操作"})
@RequestMapping(value = "/log")
@Slf4j
public class LogRestApi {

    @Autowired
    private SysLogService sysLogService;

    @AuthorityVerify
    @ApiOperation(value = "获取操作日志列表", notes = "获取操作日志列表", response = String.class)
    @PostMapping(value = "/getLogList")
    public String getLogList(@Validated({GetList.class}) @RequestBody SysLogVo sysLogVo, BindingResult resut) {
        ThrowableUtils.checkParamArgument(resut);
        return ResultUtil.result(SysConf.SUCCESS, sysLogService.getPageList(sysLogVo));
    }


}
