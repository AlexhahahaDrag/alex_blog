package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.xo.vo.AdminVo;

import java.util.List;

/**
 *description:  admin service
 *author:       alex
 *createDate:   2021/7/5 20:48
 *version:      1.0.0
 */

public interface AdminService extends SuperService<Admin> {

    /**
     * @param uid
     * @description:  通过uid获取admin信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.Admin
     */
    Admin getAdminByUid(String uid);

    /**
     * @param adminVo
     * @description:  获取在线用户列表
     * @author:       alex
     * @return:       java.lang.String
     */
    String getOnLineAdminList(AdminVo adminVo);

    /*
     * @param username 用户名
     * @description:   web端通过用户名获取一个admin
     * @author:        alex
     * @return:        com.alex.blog.common.entity.Admin
     */
    Admin getAdminByUser(String username);

    /**
     * @description:  获取当前管理员
     * @author:       alex
     * @return:       com.alex.blog.common.entity.Admin
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
     * @param uids
     * @description:  批量删除管理员
     * @author:       alex
     * @return:       java.lang.String
     */
    String deleteBatchAdmin(List<String> uids);

    /**
     * @param tokenList
     * @description:  强制登出
     * @author:       alex
     * @return:       java.lang.String
    */
    String forceLogout(List<String> tokenList);
}