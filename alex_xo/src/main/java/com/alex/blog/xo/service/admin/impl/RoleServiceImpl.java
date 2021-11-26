package com.alex.blog.xo.service.admin.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.entity.admin.Role;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.xo.mapper.admin.RoleMapper;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.admin.RoleService;
import com.alex.blog.common.vo.admin.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *description:  角色服务实现类
 *author:       alex
 *createDate:   2021/7/31 14:08
 *version:      1.0.0
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Override
    public IPage<Role> getPageList(RoleVo roleVo) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(roleVo.getKeyword()) &&StringUtils.isNotEmpty(roleVo.getKeyword().trim())) {
            query.like(SQLConf.ROLENAME, roleVo.getKeyword().trim());
        }
        query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
        Page<Role> page = new Page<>();
        page.setCurrent(roleVo.getCurrentPage());
        page.setSize(roleVo.getPageSize());
        return roleService.page(page, query);
    }

    @Override
    public String addRole(RoleVo roleVo) {
        String roleName = roleVo.getRoleName();
        QueryWrapper<Role> query = new QueryWrapper<>();
        query.eq(SQLConf.ROLENAME, roleName);
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        query.eq("is_delete", 0);
        Role one = roleService.getOne(query);
        if (one == null) {
            Role role = new Role();
            BeanUtils.copyProperties(roleVo, role);
            role.insert();
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
        }
        return ResultUtil.result(SysConf.ERROR, "角色名称已存在！");
    }

    @Override
    public String editRole(RoleVo roleVo) {
        Role one = roleService.getById(roleVo.getId());
        if (one == null) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        //判断角色名称是否存在
        if (StringUtils.isNotBlank(roleVo.getRoleName())) {
            String roleName = roleVo.getRoleName();
            QueryWrapper<Role> query = new QueryWrapper<>();
            query.eq(SQLConf.ROLENAME, roleName);
            query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
            query.eq("is_delete", 0);
            Role roleInfo = roleService.getOne(query);
            if (roleInfo != null && !roleInfo.getId().equals(roleVo.getId())) {
                return ResultUtil.result(SysConf.ERROR, "角色名称已存在！");
            }

        }
        one.setRoleName(roleVo.getRoleName());
        one.setCategoryMenuIds(roleVo.getCategoryMenuIds());
        one.setSummary(roleVo.getSummary());
        one.updateById();
        //修改角色信息成功后，需要删除redis中所有的admin访问路径
        deleteAdminVisitUrl();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteRole(Integer id) {
        //判断该角色下是否绑定管理员
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
        query.in(SQLConf.ROLEID, id);
        int count = adminService.count(query);
        if (count > 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.ADMIN_UNDER_THIS_ROLE);
        }
        Role role = roleService.getById(id);
        role.setStatus(EStatus.DISABLED.getCode());
        role.updateById();
        //删除角色信息成功后，需要删除redis中所有的admin访问路径
        deleteAdminVisitUrl();
        return ResultUtil.result(SysConf.SUCCESS, "删除角色成功！");
    }

    private void deleteAdminVisitUrl() {
        Set<String> keys = redisUtils.keys(RedisConf.ADMIN_VISIT_MENU + "*");
        redisUtils.delete(keys);
    }
}
