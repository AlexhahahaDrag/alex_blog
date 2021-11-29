package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.admin.CategoryMenu;
import com.alex.blog.common.vo.admin.CategoryMenuVo;

import java.util.List;
import java.util.Map;

/**
 *description:  菜单服务类
 *author:       alex
 *createDate:   2021/10/20 7:13
 *version:      1.0.0
 */
public interface CategoryMenuService extends SuperService<CategoryMenu> {

    /**
     * @param categoryMenuVo
     * @description:  获取菜单列表
     * @author:       alex
     * @return:       Map<String,Object>
    */
    Map<String, Object> getPageList(CategoryMenuVo categoryMenuVo);

    /**
     * @param id
     * @description:  根据关键字获取全部列表
     * @author:       alex
     * @return:       java.util.List<com.alex.blog.common.entity.admin.CategoryMenu>
    */
    List<CategoryMenu> getAllList(Integer id);

    /**
     * @param keyword
     * @description:  获取所有二级菜单按钮列表
     * @author:       alex
     * @return:       java.util.List<com.alex.blog.common.entity.admin.CategoryMenu>
    */
    List<CategoryMenu> getButtonAllList(String keyword);

    /**
     * @param categoryMenuVo
     * @description:  新增菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    String addCategoryMenu(CategoryMenuVo categoryMenuVo);

    /**
     * @param categoryMenuVo
     * @description:  编辑菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    String editCategoryMenu(CategoryMenuVo categoryMenuVo);

    /**
     * @param id
     * @description:  删除菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    String deleteCategoryMenu(Integer id);

    /**
     * @param categoryMenuVo
     * @description:  置顶菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    String stickCategoryMenu(CategoryMenuVo categoryMenuVo);
}
