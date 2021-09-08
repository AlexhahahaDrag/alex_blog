package com.alex.blog.admin.controller;

import com.alex.blog.admin.annotion.OperationLogger;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.common.vo.admin.AdminVo;
import com.alex.blog.xo.service.AdminService;
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

//    @AuthorityVerify
    @OperationLogger(value = "新增管理员")
    @ApiOperation(value = "新增管理员", notes = "新增管理员")
    @PostMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody AdminVo adminVo, BindingResult result) {
//        public String add(@RequestBody AdminVo adminVo) {
        //参数校验
//        ThrowableUtils.checkParamArgument(result);
        return adminService.addAdmin(adminVo);
    }

}
