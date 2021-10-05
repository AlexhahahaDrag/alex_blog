package com.alex.blog.admin.controller;

import com.alex.blog.admin.global.SysConf;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.admin.RoleVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public String getList(@Validated({GetList.class}) @RequestBody RoleVo roleVo, BindingResult result) {
        //校验参数
        ThrowableUtils.checkParamArgument(result);
        log.info("获取角色信息列表");
        return ResultUtil.result(SysConf.SUCCESS, roleService.getPageList(roleVo));
    }
}
