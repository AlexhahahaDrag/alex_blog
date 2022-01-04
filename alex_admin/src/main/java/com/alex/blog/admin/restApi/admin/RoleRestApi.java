package com.alex.blog.admin.restApi.admin;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.admin.global.SysConf;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.admin.RoleVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *description:  角色rest api
 *author:       alex
 *createDate:   2021/10/5 19:03
 *version:      1.0.0
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色相关接口", tags = {"角色相关接口"})
@Slf4j
public class RoleRestApi {

    @Autowired
    private RoleService roleService;

    @AuthorityVerify
    @ApiOperation(value = "获取角色信息列表", notes = "获取角色信息列表")
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody RoleVo roleVo, BindingResult result) {
        //校验参数
        ThrowableUtils.checkParamArgument(result);
        log.info("获取角色信息列表");
        return ResultUtil.result(SysConf.SUCCESS, roleService.getPageList(roleVo));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    @OperationLogger(value = "新增角色信息")
    @PutMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody RoleVo roleVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return roleService.addRole(roleVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "更新角色信息", notes = "更新角色信息")
    @OperationLogger(value = "更新角色信息")
    @PostMapping(value = "/edit")
    public String edit(@Validated({Update.class}) @RequestBody RoleVo roleVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return roleService.editRole(roleVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @OperationLogger(value = "删除角色信息")
    @DeleteMapping(value = "/delete")
    public String delete(@RequestParam(value = "id") Integer id) {
        return roleService.deleteRole(id);
    }
}
