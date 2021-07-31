package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.Role;
import com.alex.blog.xo.vo.RoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *description:  角色服务类
 *author:       alex
 *createDate:   2021/7/31 13:57
 *version:      1.0.0
 */
public interface RoleService extends SuperService<Role> {

    /**
     * @param roleVo
     * @description:  获取角色列表
     * @author:       alex
     * @return:       com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.Role>
    */
    IPage<Role> getPageList(RoleVo roleVo);

    /**
     * @param roleVo
     * @description:  添加角色
     * @author:       alex
     * @return:       java.lang.String
    */
    String addRole(RoleVo roleVo);

    /**
     * @param roleVo
     * @description:  编辑角色
     * @author:       alex
     * @return:       java.lang.String
    */
    String editRole(RoleVo roleVo);

    /**
     * @param roleVo
     * @description:  删除角色
     * @author:       alex
     * @return:       java.lang.String
    */
    String deleteRole(RoleVo roleVo);
}
