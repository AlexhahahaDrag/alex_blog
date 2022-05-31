package com.alex.blog.xo.service;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.config.jwt.Audience;
import com.alex.blog.common.config.jwt.JwtTokenUtil;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.entity.admin.Role;
import com.alex.blog.common.feign.PictureFeignClient;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.admin.RoleService;
import com.alex.blog.xo.service.sys.WebConfigService;
import com.alex.blog.xo.utils.WebUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private RedisUtils RedisUtils;

    @Autowired
    private AdminService adminService;

    @Value(value = "${isRememberMeExpiresSecond}")
    private int isRememberMeExpiresSecond;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Autowired
    private Audience audience;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WebConfigService webConfigService;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Autowired
    private WebUtils webUtils;

    public String login(HttpServletRequest request, String username, String password, boolean isRemember) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.resultWithMessage(SysConf.ERROR, "账号或密码为空");
        }
        String ip = IpUtils.getIpAddr(request);
        String limitCount = RedisUtils.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip + RedisConf.SEGMENTATION + username);
        if(StringUtils.isNotEmpty(limitCount) && Integer.parseInt(limitCount) >= Constants.NUM_FIVE) {
            return ResultUtil.result(SysConf.ERROR, "密码输错次数过多，已被锁定30分钟");
        }
        boolean isEmail = CheckUtils.checkEmail(username);
        boolean isMobile = CheckUtils.checkPhone(username);
        QueryWrapper<Admin> query = new QueryWrapper<>();
        if (isEmail) {
            query.eq(SysConf.EMAIL, username);
        } else if (isMobile) {
            query.eq(SysConf.MOBILE, username);
        } else {
            query.eq(SysConf.USERNAME, username);
        }
        query.last(SysConf.LIMIT_ONE);
        query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
        Admin admin = adminService.getOne(query);
        if (admin == null) {
            //设置错误登录次数
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request, username)));
        }
        //对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword = encoder.matches(password, admin.getPassword());
        if (!isPassword) {
            //密码错误，返回提示信息
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request, username)));
        }
        //设置角色信息
        List<String> roleIds = new ArrayList<>();
        roleIds.add(admin.getRoleId());
        List<Role> roles = roleService.listByIds(roleIds);
        if (roles == null || roles.size() <= 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.NO_ROLE);
        }
        StringBuilder sb = new StringBuilder();
        for (Role role : roles) {
            sb.append(role.getRoleName()).append(Constants.SYMBOL_COMMA);
        }
        String roleName = sb.replace(sb.length() - 1, sb.length(), "").toString();
        long expiration = isRemember ? isRememberMeExpiresSecond : audience.getExpiresSecond();
        String jwtToken = jwtTokenUtil.createJwt(admin.getUsername(), admin.getId(), roleName, audience.getClientId(), audience.getName()
                , expiration, audience.getBase64Secret());
        String token = tokenHead + jwtToken;
        HashMap<String, Object> result = new HashMap<>(Constants.NUM_ONE);
        result.put(SysConf.TOKEN, token);
        //进行登陆相关操作
        int count = admin.getLoginCount() + 1;
        admin.setLoginCount(count);
        admin.setLastLoginIp(ip);
        admin.setLastLoginTime(LocalDateTime.now());
        admin.updateById();
        //设置token到validCode.用于记录用户信息
        admin.setValidCode(token);
        //设置tokenId，主要用于换取token令牌，防止token直接暴露到在线用户管理中
        admin.setTokenId(StringUtils.getUUID());
        admin.setRole(roles.get(0));
        //添加在线用户到redis中，设置过期时间
        adminService.addOnLineAdmin(admin, expiration);
        result.put(SysConf.ADMIN, admin);
        return ResultUtil.result(SysConf.SUCCESS, result);
    }

    @ApiOperation(value = "用户信息", notes = "用户信息", response = String.class)
    @GetMapping(value = "/info")
    public String info(HttpServletRequest request, @ApiParam(name = "token", value = "token令牌") @RequestParam(value = "token", required = false) String token) {
        Map<String, Object> map = new HashMap<>(Constants.NUM_THREE);
        String adminId = (String) request.getAttribute(SysConf.ADMIN_ID);
        boolean isExpiration = jwtTokenUtil.isExpiration(token, audience.getBase64Secret());
        if (adminId == null || isExpiration) {
            return ResultUtil.result(SysConf.ERROR, "token用户过期");
        }
        Admin admin = adminService.getById(adminId);
        map.put(SysConf.TOKEN, token);
        if (StringUtils.isNotEmpty(admin.getAvatar())) {
            String picture = pictureFeignClient.getPicture(admin.getAvatar(), SysConf.FILE_SEGMENTATION);
            List<String> pictureList = webUtils.getPicture(picture);
            map.put(SysConf.AVATAR, pictureList == null || pictureList.isEmpty() ?
                    "https://gitee.com/moxi159753/wx_picture/raw/master/picture/favicon.png" : pictureList.get(0));
        }
        List<Role> roles = roleService.listByIds(Lists.newArrayList(admin.getRoleId()));
        map.put(SysConf.ROLES, roles);
        return ResultUtil.result(SysConf.SUCCESS, map);
    }

    @ApiOperation(value = "获取网站名称", notes = "获取网站名称", response = String.class)
    @GetMapping(value = "/getWebSiteName")
    public String getWebSiteName() {
        return ResultUtil.result(SysConf.SUCCESS, webConfigService.getWebSiteName());
    }

    @ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
    @PostMapping(value = "logout")
    public String logout() {
        String adminToken = RequestHolder.getAdminToken();
        if (StringUtils.isEmpty(adminToken)) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.OPERATION_FAIL);
        } else {
            //获取在线用户信息
            String adminJson = redisUtils.get(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + adminToken);
            if (StringUtils.isNotEmpty(adminJson)) {
                OnlineAdmin onlineAdmin = JsonUtils.jsonToPojo(adminJson, OnlineAdmin.class);
                //移除redis中tokenId
                redisUtils.delete(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + onlineAdmin.getTokenId());
            }
            //移除redis中的用户
            redisUtils.delete(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + adminToken);
            SecurityContextHolder.clearContext();
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.OPERATION_SUCCESS);
        }
    }

    /**
     * @param request
     * @param username 登录名称
     * @description:  设置登录限制，返回剩余次数
     * @author:       alex
     * @return:       java.lang.Integer
    */
    private Integer setLoginCommit(HttpServletRequest request, String username) {
        String ip = IpUtils.getIpAddr(request);
        String loginCountKey = RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip + RedisConf.SEGMENTATION + username;
        String count = RedisUtils.get(loginCountKey);
        int surplusCount = Constants.NUM_FIVE;
        if (StringUtils.isNotEmpty(count)) {
            int curCount = Integer.parseInt(count) + 1;
            surplusCount -= curCount;
            RedisUtils.setEx(loginCountKey, String.valueOf(curCount), 10, TimeUnit.MINUTES);
        } else {
            surplusCount -= 1;
            RedisUtils.setEx(loginCountKey, Constants.STR_ONE, 30, TimeUnit.MINUTES);
        }
        return surplusCount;
    }
}
