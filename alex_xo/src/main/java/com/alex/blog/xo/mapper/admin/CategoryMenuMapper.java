package com.alex.blog.xo.mapper.admin;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.admin.CategoryMenu;

import java.util.List;

/**
 *description:  菜单mapper
 *author:       alex
 *createDate:   2021/10/25 7:24
 *version:      1.0.0
 */
public interface CategoryMenuMapper extends SuperMapper<CategoryMenu> {

    /*
     * @param id
     * @description:  获取菜单下所有菜单
     * @author:       alex
     * @return:       java.util.List<com.alex.blog.common.entity.admin.CategoryMenu>
    */
    List<CategoryMenu> findAllList(Integer id);
}
