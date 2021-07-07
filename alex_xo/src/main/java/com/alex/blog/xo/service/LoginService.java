package com.alex.blog.xo.service;

import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.common.entity.Admin;

public class LoginService {

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
        return ResultUtil.resultWithMessage(SysConf.ERROR, "账号或密码为空");
    }
    // TODO: 2021/7/2 根据请求获取ip信息
//        String ip = IpUtils.getIpAddr(reuest);
    String ip = "192.168.1.1";
    String limitCount = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if(StringUtils.isEmpty(limitCount)) {
        Integer count = Integer.valueOf(limitCount);
        if (count >= Constants.NUM_FIVE){
            return ResultUtil.result(SysConf.ERROR, "密码输错次数过多，已被锁定30分钟");
        }
    }
    Boolean isEmail = CheckUtil.checkEmail(username);
    Boolean isMobile = CheckUtil.checkMobile(username);
    QueryWrapper<Object> query = new QueryWrapper<>();
        if (isEmail) {
        query.eq(SysConf.EMAIL, username);
    } else if (isMobile) {
        query.eq(SysConf.MOBILE, username);
    } else {
        query.eq(SysConf.USERNAME, username);
    }
    Admin admin = a
            return ResultUtil.resultWithMessage(SysConf.SUCCESS, "登录成功");
}
