package com.alex.blog.admin.controller;

import com.alex.blog.admin.global.SysConf;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.service.AdminService;
import com.alex.blog.xo.service.LoginService;
import com.alex.blog.xo.service.WebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  loginController
 *author:       alex
 *createDate:   2021/7/2 7:06
 *version:      1.0.0
 */
@RestController
//@RefreshScope
@RequestMapping("/auth")
@Api(value = "登陆相关接口", tags = {"登陆相关接口"})
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WebConfigService webConfigService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", tags = "登录")
    public String login(HttpServletRequest request, @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "isRememberMe", required = false) boolean isRememberMe) {
        return loginService.login(request, username, password, isRememberMe);
    }

    @ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
    @GetMapping(value = "/info")
    public String info(@ApiParam(name = "token", value = "token令牌")
                       @RequestParam(name = "token", required = false) String token) {
        return adminService.info(token);
    }

    @ApiOperation(value = "获取当前用户菜单", notes = "获取当前用户菜单")
    @GetMapping(value = "getMenu")
    public String getMenu() {
        return adminService.getMenu();

    }

    @ApiOperation(value = "获取网站名称", tags = "获取网站名称")
    @GetMapping("/getWebSitName")
    public String getWebSiteName() {
        return ResultUtil.result(SysConf.SUCCESS, webConfigService.getWebSiteName());
    }

    @ApiOperation(value = "登出", notes = "登出", tags = "登出")
    @GetMapping(value = "logout")
    public String logout() {
        String token = RequestHolder.getAdminToken();
        if(StringUtils.isEmpty(token)) {
            // TODO: 2021/9/22 添加错误提示信息
            return ResultUtil.result(SysConf.ERROR, "token不能为空!");
        } else {
            //获取在线用户信息
            String adminJson = redisUtils.get(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + token);
            OnlineAdmin onlineAdmin = JsonUtils.jsonToPojo(adminJson, OnlineAdmin.class);
            //移除redis中的tokenId
            redisUtils.delete(RedisConf.LOGIN_ID_KEY +RedisConf.SEGMENTATION + onlineAdmin.getTokenId());
        }
        //移除redis中用户信息
        redisUtils.delete(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + token);
        SecurityContextHolder.clearContext();
        // TODO: 2021/9/22 修改提示信息
        return ResultUtil.result(SysConf.SUCCESS, "登出成功!");
    }
}
