package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.ResourceSort;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.ResourceSortVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.mapper.blog.ResourceSortMapper;
import com.alex.blog.xo.service.blog.ResourceSortService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-02-15 09:59:56
 */
@Service
public class ResourceSortServiceImp extends SuperServiceImpl<ResourceSortMapper, ResourceSort> implements ResourceSortService {

    @Override
    public IPage<ResourceSort> getPageList(ResourceSortVo resourceSortVo) {
        return null;
    }

    @Override
    public String addResourceSort(ResourceSortVo resourceSortVo) {
        //判断文件资源分类是否存在
        QueryWrapper<ResourceSort> query = getQuery();
        query.eq(SysConf.SORT_NAME, resourceSortVo.getSortName());
        ResourceSort one = this.getOne(query);
        if (one != null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.ENTITY_EXIST);
        }
        return null;
    }

    @Override
    public String editResourceSort(ResourceSortVo resourceSortVo) {
        return null;
    }

    @Override
    public String deleteBatchResourceSort(List<String> ids) {
        return null;
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
