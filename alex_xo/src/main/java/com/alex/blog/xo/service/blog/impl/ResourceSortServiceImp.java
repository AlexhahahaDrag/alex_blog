package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.entity.blog.ResourceSort;
import com.alex.blog.xo.mapper.blog.ResourceSortMapper;
import com.alex.blog.xo.service.blog.ResourceSortService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import org.springframework.beans.BeanUtils;
import java.util.List;
import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.vo.blog.ResourceSort.ResourceSortVo;
/**
 * <p>
 * @description:  资源分类表服务实现类
 * @author:       alex
 * @createDate:   2022-03-03 21:31:12
 * @version:      1.0.0
 */
@Service
public class ResourceSortServiceImp extends SuperServiceImpl<ResourceSortMapper, ResourceSort> implements ResourceSortService {

    @Override
    public IPage<ResourceSort> getPageList(ResourceSortVo resourceSortVo) {
        QueryWrapper<ResourceSort> query = getQuery();
        Page<ResourceSort> page = new Page<>();
        page.setCurrent(resourceSortVo.getCurrentPage());
        page.setSize(resourceSortVo.getPageSize());
        Page<ResourceSort> resourceSortPage = this.page(page, query);
        return resourceSortPage;
    }

    @Override
    public String addResourceSort(ResourceSortVo resourceSortVo) {
        ResourceSort resourceSort = new ResourceSort();
        BeanUtils.copyProperties(resourceSortVo, resourceSort);
        resourceSort.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editResourceSort(ResourceSortVo resourceSortVo) {
        QueryWrapper<ResourceSort> query = getQuery();
        query.eq(SysConf.ID, resourceSortVo.getId());
        ResourceSort resourceSort = this.getOne(query);
        if (resourceSort == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(resourceSortVo, resourceSort);
        resourceSort.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchResourceSort(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickResourceSort(ResourceSortVo resourceSortVo) {
        return null;
    }

    private QueryWrapper<ResourceSort> getQuery() {
        QueryWrapper<ResourceSort> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
