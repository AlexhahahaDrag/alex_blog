package com.alex.blog.xo.service.impl;

import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.service.AdminService;
import com.alex.blog.xo.service.mapper.AdminMapper;
import com.alex.blog.xo.vo.AdminVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *description:  管理员service实现类
 *author:       alex
 *createDate:   2021/7/11 20:13
 *version:      1.0.0
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AdminService adminService;

    @Override
    public Admin getAdminByUid(String uid) {
        return adminMapper.getAdminByUid(uid);
    }

    @Override
    public String getOnLineAdminList(AdminVo adminVo) {
        //获取redis中匹配的所有key
        Set<String> keys = redisUtil.keys(RedisConf.LOGIN_TOKEN_KEY + "*");
        List<String> onlineAdminJsonList = redisUtil.muliGet(keys);
        int pageSize = adminVo.getPageSize().intValue();
        int currentPage = adminVo.getCurrentPage().intValue();
        int total = onlineAdminJsonList.size();
        int start = Math.max((currentPage - 1) * pageSize, 0);
        int end = Math.min(currentPage * pageSize, total);
        List<String> onlineAdminSubList = onlineAdminJsonList.subList(start, end);
        List<OnlineAdmin> onlineAdminList = onlineAdminSubList.stream().map(item -> {
            OnlineAdmin onlineAdmin = JsonUtils.jsonToPojo(item, OnlineAdmin.class);
            onlineAdmin.setToken("");
            return onlineAdmin;
        }).collect(Collectors.toList());
        Page<OnlineAdmin> page = new Page<>();
        page.setCurrent(currentPage);
        page.setTotal(total);
        page.setSize(pageSize);
        page.setRecords(onlineAdminList);
        return ResultUtil.resultWithData(SysConf.SUCCESS, page);
    }

    @Override
    public Admin getAdminByUser(String username) {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq(SysConf.USERNAME, username);
        query.last(SysConf.LIMIT_ONE);
        Admin admin = adminService.getOne(query);
        //清空密码，防止密码泄露
        admin.setPassword(null);
        // TODO: 2021/7/14 获取图片信息
        if (StringUtils.isNotEmpty(admin.getAvatar())) {

        }
        Admin res = new Admin();
        res.setOccupation(admin.getOccupation());
        res.setNickName(admin.getNickName());
        res.setPersonResume(admin.getPersonResume());
        res.setAvatar(admin.getAvatar());
        res.setSummary(admin.getSummary());
        res.setPhotoList(admin.getPhotoList());
        return res;
    }

    /**
     * @description:  通过request对象获取当前登录管理员信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.Admin
    */
    @Override
    public Admin getMe() {
        String adminId = RequestHolder.getAdminId();
        if (StringUtils.isEmpty(adminId)) {
            return new Admin();
        }
        Admin admin = adminService.getById(adminId);
        admin.setPassword("");
        // TODO: 2021/7/15获取图片
        if (StringUtils.isNotEmpty(admin.getAvatar())) {

        }
        return admin;
    }

    @Override
    public void addOnLineAdmin(Admin admin, Long expirationSecond) {
        HttpServletRequest request = RequestHolder.getRequest();
        // TODO: 2021/7/15 根据iputil获取request中的系统和浏览器信息
        Map<String, String> map = IpUtils.getOsAndBrowserInfo(request);
        String os = map.get(SysConf.OS);
        String browser = map.get(SysConf.BROWSER);
        String ip = map.get(SysConf.IP);
        //设置在线管理员信息
        OnlineAdmin onlineAdmin = new OnlineAdmin();
        onlineAdmin.setAdminId(admin.getId());
        onlineAdmin.setTokenId(admin.getTokenId());
        onlineAdmin.setToken(admin.getValidCode());
        onlineAdmin.setOs(os);
        onlineAdmin.setBrowser(browser);
        onlineAdmin.setIpAddr(ip);
        // TODO: 2021/7/15 编写时间工具
        onlineAdmin.setLoginTime(DateUtils.getNewDate());
        onlineAdmin.setRoleName(admin.getRole().getRoleName());
        onlineAdmin.setUsername(admin.getUsername());
        onlineAdmin.setExpireTime(DateUtils.getDateStr(new Date(), expirationSecond));
        //从redis中获取ip来源
        String jsonResult = redisUtil.get(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isEmpty(jsonResult)) {
            String addresses = IpUtils.getAddresses(SysConf.IP, RedisConf.EQUAL_TO + ip, SysConf.UTF_8);
            if (StringUtils.isNotEmpty(addresses)) {
                jsonResult = addresses;
                redisUtil.setEx(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip, addresses, 24, TimeUnit.HOURS);
            }
        }
        onlineAdmin.setLoginLocation(jsonResult);
        //将登陆的管理员储存到在线用户列表中
        redisUtil.setEx(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + admin.getValidCode(), JsonUtils.objectToJson(onlineAdmin), expirationSecond, TimeUnit.MINUTES);
        //在维护一张用于tokenid - toekn转化的表
        redisUtil.setEx(RedisConf.LOGIN_ID_KEY + RedisConf.SEGMENTATION + admin.getTokenId(), admin.getValidCode(), expirationSecond, TimeUnit.MINUTES);
    }

    @Override
    public String getList(AdminVo adminVo) {
        QueryWrapper<Object> query = new QueryWrapper<>();
        String pictureResult = null;
        if (StringUtils.isNotEmpty(adminVo.getKeyword())) {
            query.like(SQLConf.)
        }
        return null;
    }

    @Override
    public String addAdmin(AdminVo adminVo) {
        return null;
    }

    @Override
    public String editAdmin(AdminVo adminVo) {
        return null;
    }

    @Override
    public String editMe(AdminVo adminVo) {
        return null;
    }

    @Override
    public String changePwd(String oldPwd, String newPwd) {
        return null;
    }

    @Override
    public String resetPwd(AdminVo adminVo) {
        return null;
    }

    @Override
    public String deleteBatchAdmin(List<String> uids) {
        return null;
    }

    @Override
    public String forceLogout(List<String> tokenList) {
        return null;
    }
}
