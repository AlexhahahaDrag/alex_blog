package com.alex.blog.xo.mapper.admin;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.admin.Admin;
import org.apache.ibatis.annotations.Param;

/**
 *description:
 *author:       alex
 *createDate:   2021/7/8 7:29
 *version:      1.0.0
 */
public interface AdminMapper extends SuperMapper<Admin> {

    /**
     * @param id
     * @description:  通过uid获取管理员信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.Admin
    */
    Admin getAdminById(@Param("id") Integer id);
}
