package com.alex.blog.admin.restApi.sys;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.admin.SystemConfigVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.sys.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *description:  系统配置服务接口
 *author:       alex
 *createDate:   2022/2/14 17:38
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/systemConfig")
@Api(value = "系统配置相关接口", tags = {"系统配置相关接口"})
@RequiredArgsConstructor
public class SystemConfigRestApi {

    private final SystemConfigService systemConfigService;

    @AuthorityVerify
    @ApiOperation(value = "获取系统配置", notes = "获取系统配置", response = String.class)
    @PostMapping(value = "/getList")
    public String getList() {
        return ResultUtil.resultSuccessWithData(systemConfigService.getConfig());
    }

    @AuthorityVerify
    @ApiOperation(value = "通过key前缀清空redis缓存", notes = "通过key前缀清空redis缓存", response = String.class)
    @PostMapping(value = "/cleanRedisByKey")
    public String cleanRedisByKey(@RequestBody List<String> keys) {
        return ResultUtil.resultSuccessWithData(systemConfigService.cleanRedisByKey(keys));
    }

    @AuthorityVerify
    @ApiOperation(value = "修改系统配置", notes = "修改系统配置", response = String.class)
    @PutMapping(value = "/editSystemConfig")
    @OperationLogger(value = "修改系统配置")
    public String editSystemConfig(@Validated({Update.class}) @RequestBody SystemConfigVo systemConfigVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return systemConfigService.editSystemConfig(systemConfigVo);
    }
}
