package com.alex.blog.xo.service;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.common.config.jwt.Audience;
import com.alex.blog.common.config.jwt.JwtTokenUtil;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.common.entity.Role;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.global.SQLConf;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public String login(HttpServletRequest request, String username, String password, boolean isRemember) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.resultWithMessage(SysConf.ERROR, "账号或密码为空");
        }
        String ip = IpUtils.getIpAddr(request);
        String limitCount = RedisUtils.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
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
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        //对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword = encoder.matches(password, admin.getPassword());
        if (!isPassword) {
            //密码错误，返回提示信息
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        //设置角色信息
        List<String> roleIds = new ArrayList<>();
        roleIds.add(admin.getRoleId());
        List<Role> roles = roleService.listByIds(roleIds);
        if (roles == null && roles.size() <= 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.NO_ROLE);
        }
        StringBuffer sb = new StringBuffer();
        for (Role role : roles) {
            sb.append(role.getRoleName()).append(Constants.SYMBOL_COMMA);
        }
        String roleName = sb.replace(sb.length() - 1, sb.length(), "").toString();
        long expiration = isRemember ? isRememberMeExpiresSecond : audience.getExpiresSecond();
        // TODO: 2021/7/25 添加角色信息
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
//        admin.setRole();
        //添加在线用户到redis中，设置过期时间
        adminService.addOnLineAdmin(admin, expiration);
        return ResultUtil.result(SysConf.SUCCESS, result);
    }

    private Integer setLoginCommit(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        String count = RedisUtils.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        int surplusCount = Constants.NUM_FIVE;
        if (StringUtils.isNotEmpty(count)) {
            int curCount = Integer.parseInt(count) + 1;
            surplusCount -= curCount;
            RedisUtils.setEx(RedisConf.LOGIN_LIMIT +
                    RedisConf.SEGMENTATION + ip, String.valueOf(curCount), 10, TimeUnit.MINUTES);
        } else {
            surplusCount -= 1;
            RedisUtils.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, Constants.STR_ONE, 30, TimeUnit.MINUTES);
        }
        return surplusCount;
    }
}
