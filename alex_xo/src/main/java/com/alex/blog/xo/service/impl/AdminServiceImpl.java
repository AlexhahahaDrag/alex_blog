package com.alex.blog.xo.service.impl;

import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.RedisUtil;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.service.AdminService;
import com.alex.blog.xo.service.mapper.AdminMapper;
import com.alex.blog.xo.vo.AdminVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

    @Override
    public Admin getMe() {
        RequestHolder
        return null;
    }

    @Override
    public void addOnLineAdmin(Admin admin, Long expirationSecond) {

    }

    @Override
    public String getList(AdminVo adminVo) {
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
