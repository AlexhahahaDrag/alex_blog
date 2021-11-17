package com.alex.blog.xo.service.impl;

import com.alex.blog.base.entity.BaseEntity;
import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.CategoryMenu;
import com.alex.blog.common.enums.EMenuType;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.admin.CategoryMenuVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.CategoryMenuMapper;
import com.alex.blog.xo.service.CategoryMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *description:  菜单服务实现类
 *author:       alex
 *createDate:   2021/10/25 7:25
 *version:      1.0.0
 */
@Service
public class CategoryMenuServiceImpl extends SuperServiceImpl<CategoryMenuMapper, CategoryMenu> implements CategoryMenuService {

    @Autowired
    private CategoryMenuService categoryMenuService;
    /**
     * @param categoryMenuVo
     * @description:    
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
    */
    @Override
    public Map<String, Object> getPageList(CategoryMenuVo categoryMenuVo) {
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<CategoryMenu> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(categoryMenuVo.getKeyword()) && StringUtils.isNotEmpty(categoryMenuVo.getKeyword().trim())) {
            query.like(SysConf.NAME, categoryMenuVo.getKeyword().trim());
        }
        if (categoryMenuVo.getMenuLevel() != 0) {
            query.eq(SysConf.MENU_LEVEL, categoryMenuVo.getMenuLevel());
        }
        Page<CategoryMenu> page = new Page<>();
        page.setSize(categoryMenuVo.getPageSize() == null ? Constants.NUM_TEN : categoryMenuVo.getPageSize());
        page.setCurrent(categoryMenuVo.getCurrentPage() == null ? 0 : categoryMenuVo.getCurrentPage());
        query.eq(SysConf.STATUS, EStatus.ENABLE.getValue());
        query.orderByDesc(SysConf.SORT);
        Page<CategoryMenu> pageList = categoryMenuService.page(page, query);
        List<CategoryMenu> records = pageList.getRecords();
        //获取父级id
        List<Long> pIds = records.stream().filter(item -> item.getPid() != null).map(CategoryMenu::getPid).collect(Collectors.toList());
        //如果父级id不为空，这查询父级id,并设置父级
        if (pIds.size() > 0) {
            List<CategoryMenu> pList = categoryMenuService.listByIds(pIds);
            resultMap.put(SysConf.OTHER_DATA, pList);
            Map<Long, CategoryMenu> map = pList.stream().collect(Collectors.toMap(BaseEntity::getId, CategoryMenu -> CategoryMenu));
            records.forEach(item -> {
                if (item.getPid() != 0) {
                    item.setParentCategoryMenu(map.get(item.getPid()));
                }
            });
            pageList.setRecords(records);
        }
        resultMap.put(SysConf.DATA, pageList);
        return resultMap;
    }

    /**
     * @param id
     * @description:  获取该菜单下所有菜单
     * @author:       alex
     * @return:       java.util.List<com.alex.blog.common.entity.admin.CategoryMenu>
    */
    @Override
    public List<CategoryMenu> getAllList(Integer id) {
        QueryWrapper<CategoryMenu> query = new QueryWrapper<>();
        query.eq(SysConf.MENU_LEVEL, Constants.NUM_ONE);
        if (id != null) {
            query.eq(SysConf.ID, id);
        }

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

    /**
     * @param categoryMenuVo
     * @description:  置顶菜单
     * @author:       alex
     * @return:       java.lang.String
    */
    @Override
    public String stickCategoryMenu(CategoryMenuVo categoryMenuVo) {
        CategoryMenu categoryMenu = categoryMenuService.getById(categoryMenuVo.getId());
        //查找出最大的那个菜单
        QueryWrapper<CategoryMenu> query = new QueryWrapper<>();
        //如果是二级菜单或者按钮，就在当前的兄弟中查找最大的一个
        if (categoryMenu.getMenuLevel() == Constants.NUM_TWO || categoryMenu.getMenuType().equals(EMenuType.BUTTON.getCode())) {
            query.eq(SysConf.PID, categoryMenu.getPid());
        }
        query.eq(SysConf.MENU_LEVEL, categoryMenu.getMenuLevel()).orderByDesc(SysConf.SORT).last(SysConf.LIMIT_ONE);
        CategoryMenu maxMenu = categoryMenuService.getOne(query);
        if (maxMenu.getId() == null) {
            return ResultUtil.result(SysConf.ERROR, "操作失败!");
        }
        categoryMenu.setSort(maxMenu.getSort() + 1);
        categoryMenu.updateById();
        return ResultUtil.result(SysConf.SUCCESS, "操作成功！");
    }
}
