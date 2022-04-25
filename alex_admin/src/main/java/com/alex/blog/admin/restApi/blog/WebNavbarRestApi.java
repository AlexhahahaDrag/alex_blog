package com.alex.blog.admin.restApi.blog;

import com.alex.blog.common.vo.blog.WebNavbarVo;
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
import com.alex.blog.xo.service.blog.WebNavbarService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  restApi
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Api(value = "相关接口", tags = {"相关接口"})
@RestController
@RequestMapping("/web-navbar")
public class WebNavbarRestApi {

    @Autowired
    private WebNavbarService webNavbarService;

    @AuthorityVerify
    @ApiOperation(value = "获取列表", notes = "获取列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody WebNavbarVo webNavbarVo) {
        return ResultUtil.resultSuccessWithData(webNavbarService.getPageList(webNavbarVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增", notes = "新增", response = String.class)
    @PutMapping(value = "/add")
    @OperationLogger(value = "新增")
    public String addWebNavbar(@Validated({Insert.class}) @RequestBody WebNavbarVo webNavbarVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return webNavbarService.addWebNavbar(webNavbarVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改", notes = "修改", response = String.class)
    @PutMapping(value = "/edit")
    @OperationLogger(value = "修改")
    public String editWebNavbar(@Validated({Update.class}) @RequestBody WebNavbarVo webNavbarVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return webNavbarService.editWebNavbar(webNavbarVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除", notes = "删除", response = String.class)
    @DeleteMapping(value = "/deleteBatch")
    @OperationLogger(value = "删除")
    public String deleteBatch(@Validated({Delete.class})
                              @ApiParam(value = "ids", name = "ids", required = true)
                              @RequestParam(value = "ids") List<String> ids) {
        return webNavbarService.deleteBatchWebNavbar(ids);
    }
}
