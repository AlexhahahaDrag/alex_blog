package com.alex.blog.xo.service.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.CategoryMenu;
import com.alex.blog.common.vo.admin.CategoryMenuVo;
import com.alex.blog.xo.mapper.CategoryMenuMapper;
import com.alex.blog.xo.service.CategoryMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *description:  菜单服务实现类
 *author:       alex
 *createDate:   2021/10/25 7:25
 *version:      1.0.0
 */
@Service
public class CategoryMenuServiceImpl extends SuperServiceImpl<CategoryMenuMapper, CategoryMenu> implements CategoryMenuService {

    /**
     * @param categoryMenuVo
     * @description:    
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
    */
    @Override
    public Map<String, Object> getPageList(CategoryMenuVo categoryMenuVo) {
        return null;
    }

    @Override
    public List<CategoryMenu> getAllList(String keyword) {
        return null;
    }

    @Override
    public List<CategoryMenu> getButtonAllList(String keyword) {
        return null;
    }

    @Override
    public String addCategoryMenu(CategoryMenuVo categoryMenuVo) {
        return null;
    }

    @Override
    public String editCategoryMenu(CategoryMenuVo categoryMenuVo) {
        return null;
    }

    @Override
    public String deleteCategoryMenu(CategoryMenuVo categoryMenuVo) {
        return null;
    }

    @Override
    public String stickCategoryMenu(CategoryMenuVo categoryMenuVo) {
        return null;
    }
}
