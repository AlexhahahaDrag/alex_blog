package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.ResourceSort;
import com.alex.blog.common.vo.blog.ResourceSortVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author alex
 * @since 2022-02-15 09:59:56
 */
public interface ResourceSortService extends SuperService<ResourceSort> {

    /**
     * @param resourceSortVo
     * @description: 获取资源分类列表
     * @author:      alex
     * @createDate:  2022/2/15 17:56
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.ResourceSort>
     */
    IPage<ResourceSort> getPageList(ResourceSortVo resourceSortVo);

    /**
     * @param resourceSortVo
     * @description: 新增资源分类
     * @author:      alex
     * @createDate:  2022/1/28 9:42
     * @return:      java.lang.String
     */
    String addResourceSort(ResourceSortVo resourceSortVo);

    /**
     * @param resourceSortVo
     * @description: 编辑资源分类
     * @author:      alex
     * @createDate:  2022/1/28 9:43
     * @return:      java.lang.String
     */
    String editResourceSort(ResourceSortVo resourceSortVo);

    /**
     * @param ids
     * @description: 批量删除资源分类
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
     */
    String deleteBatchResourceSort(List<String> ids);

    /**
     * @param resourceSortVo
     * @description: 置顶资源分类
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
     */
    String stickResourceSort(ResourceSortVo resourceSortVo);
}
