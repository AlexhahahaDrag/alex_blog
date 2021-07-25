package com.alex.blog.admin.controller;

import com.alex.blog.common.entity.LoginParams;
import com.alex.blog.xo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  loginController
 *author:       alex
 *createDate:   2021/7/2 7:06
 *version:      1.0.0
 */
@RestController
@RefreshScope
@RequestMapping("/auth")
@Api(value = "登陆相关接口", tags = {"登陆相关接口"})
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", tags = "登录")
    public String login(HttpServletRequest request,  @RequestBody LoginParams loginParams) {
        return loginService.login(request, loginParams.getUsername(), loginParams.getPassword(), loginParams.getIsRemember());
    }
}
