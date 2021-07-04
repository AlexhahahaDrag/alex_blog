package com.alex.blog.admin.controller;

import com.alex.blog.admin.global.SysConf;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.common.entity.LoginParams;
import com.alex.blog.utils.utils.RedisUtil;
import com.alex.blog.utils.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
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
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录", tags = "登录")
    public String login(HttpServletRequest request,  @RequestBody LoginParams loginParams) {
        System.out.println(loginParams);
        if (StringUtils.isEmpty(loginParams.getUsername()) || StringUtils.isEmpty(loginParams.getPassword())) {
            return ResultUtil.resultWithMessage(SysConf.ERROR, "账号或密码为空");
        }
        // TODO: 2021/7/2 根据请求获取ip信息
//        String ip = IpUtils.getIpAddr(reuest);
        String ip = "192.168.1.1";
        String limitCount = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if(StringUtils.is)
        return ResultUtil.resultWithMessage(SysConf.SUCCESS, "登录成功");
    }
}
