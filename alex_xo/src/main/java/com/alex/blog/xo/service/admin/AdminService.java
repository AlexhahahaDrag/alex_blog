package com.alex.blog.xo.service.admin;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.vo.admin.AdminVo;

import java.util.List;

/**
 *description:  admin service
 *author:       alex
 *createDate:   2021/7/5 20:48
 *version:      1.0.0
 */

public interface AdminService extends SuperService<Admin> {

    /**
     * @param id
     * @description:  通过id获取admin信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.Admin
     */
    Admin getAdminById(Integer id);

    /**
     * @param adminVo
     * @description:  获取在线用户列表
     * @author:       alex
     * @return:       java.lang.String
     */
    String getOnlineAdminList(AdminVo adminVo);

    /*
     * @param username 用户名
     * @description:   web端通过用户名获取一个admin
     * @author:        alex
     * @return:        com.alex.blog.common.entity.admin.Admin
     */
    Admin getAdminByUser(String username);

    /**
     * @description:  获取当前管理员
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.Admin
     */
    Admin getMe();

    /**
     * @param admin
     * @param expirationSecond    过期时间
     * @description:  添加在线用户
     * @author:       alex
     * @return:       void
     */
    void addOnLineAdmin(Admin admin, Long expirationSecond);

    /**
     * @param adminVo
     * @description:  获取管理员列表
     * @author:       alex
     * @return:       java.lang.String
     */
    String getList(AdminVo adminVo);

    /**
     * @param adminVo
     * @description:  添加管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    String addAdmin(AdminVo adminVo);

    /**
     * @param adminVo
     * @description:  编辑管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    String editAdmin(AdminVo adminVo);

    /**
     * @param adminVo
     * @description:  编辑当前管理员信息
     * @author:       alex
     * @return:       java.lang.String
     */
    String editMe(AdminVo adminVo);

    /**
     * @param oldPwd
     * @param newPwd
     * @description:  修改密码
     * @author:       alex
     * @return:       java.lang.String
     */
    String changePwd(String oldPwd, String newPwd);

    /**
     * @param adminVo
     * @description:  重置密码
     * @author:       alex
     * @return:       java.lang.String
     */
    String resetPwd(AdminVo adminVo);

    /**
     * @param ids
     * @description:  批量删除管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    String deleteBatchAdmin(List<String> ids);

    /**
     * @param tokenIdList
     * @description:  强制登出
     * @author:       alex
     * @return:       java.lang.String
    */
    String forceLogout(List<String> tokenIdList);

    /**
     * @param token
     * @description:  根据token获取用户信息
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
    */
    String info(String token);

    /**
     * @description:  获取菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    String getMenu();
}