package com.alex.blog.xo.service.impl;

import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.RedisUtil;
import com.alex.blog.xo.entity.OnlineAdmin;
import com.alex.blog.xo.service.AdminService;
import com.alex.blog.xo.service.mapper.AdminMapper;
import com.alex.blog.xo.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        onlineAdminSubList.stream().forEach(
                OnlineAdmin  = JsonUtils.
        );
        return null;
    }

    @Override
    public Admin getAdminByUser(String username) {
        return null;
    }

    @Override
    public Admin getMe() {
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
