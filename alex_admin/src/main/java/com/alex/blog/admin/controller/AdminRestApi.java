package com.alex.blog.admin.controller;

import com.alex.blog.admin.annotion.AuthorityVerify;
import com.alex.blog.admin.annotion.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.OperationLogger;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.admin.AdminVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *description:  管理员rest api
 *author:       alex
 *createDate:   2021/9/6 6:56
 *version:      1.0.0
 */
@RestController
@RequestMapping("/admin")
@Api(value = "管理员相关接口", tags = {"管理员相关接口"})
@Slf4j
public class AdminRestApi {

    @Autowired
    private AdminService adminService;

    @AvoidRepeatableCommit
    @AuthorityVerify
    @OperationLogger(value = "新增管理员")
    @ApiOperation(value = "新增管理员", notes = "新增管理员")
    @PutMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody AdminVo adminVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return adminService.addAdmin(adminVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "更新管理员")
    @ApiOperation(value = "更新管理员", notes = "更新管理员")
    @PostMapping("/edit")
    public String edit(@Validated({Update.class}) @RequestBody AdminVo adminVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return adminService.editAdmin(adminVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "批量删除管理员")
    @ApiOperation(value = "批量删除管理员", notes = "批量删除管理员")
    @DeleteMapping("/delete")
    public String delete(@ApiParam(name ="adminIds", value = "管理员id") @RequestParam(value = "adminIds", required = false) List<String> adminIds) {
        //参数校验
        return adminService.deleteBatchAdmin(adminIds);
    }

    @AuthorityVerify
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/changPwd")
    public String changePwd(@ApiParam(name = "oldPwd", value = "旧密码", required = true)@RequestParam(name = "oldPwd") String oldPwd,
                            @ApiParam(name = "newPwd", value = "新密码", required = true)@RequestParam(name = "newPwd") String newPwd) {
        return adminService.changePwd(oldPwd, newPwd);
    }

    @AuthorityVerify
    @ApiOperation(value = "获取我的信息", notes = "获取我的信息")
    @GetMapping("/getMe")
    public String getMe() {
        return ResultUtil.result(SysConf.SUCCESS, adminService.getMe()) ;
    }

    @AuthorityVerify
    @ApiOperation(value = "编辑我的信息", notes = "编辑我的信息")
    @PostMapping("/editMe")
    public String editMe(@Validated({Update.class}) @RequestBody AdminVo adminVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return adminService.editMe(adminVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "强制登出")
    @ApiOperation(value = "强制登出", notes = "强制登出")
    @PostMapping("/forceLogout")
    public String forceLogout(@ApiParam(name = "tokenIdList", value = "管理员token列表") @RequestParam(value = "tokenIdList", required =  false) List<String> tokenIdList) {
        return adminService.forceLogout(tokenIdList);
    }

    @AuthorityVerify
    @ApiOperation(value = "获取在线管理员列表", notes = "获取在线管理员列表")
    @PostMapping("/getOnlineAdminList")
    public String getOnlineAdminList(@Validated({GetList.class}) @RequestBody AdminVo adminVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return adminService.getOnlineAdminList(adminVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @PostMapping("/getAdminList")
    public String getAdminList(@Validated({GetList.class}) @RequestBody AdminVo adminVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return adminService.getList(adminVo);
    }
}
