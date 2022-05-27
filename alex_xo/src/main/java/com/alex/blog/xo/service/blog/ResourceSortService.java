package com.alex.blog.xo.service.blog;

import com.alex.blog.common.entity.blog.ResourceSort;
import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.vo.blog.ResourceSortVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
/**
 * 资源分类表 服务类
 * @author: alex
 * @createDate: 2022-03-03 21:31:12
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface ResourceSortService extends SuperService<ResourceSort> {

    /**
     * @param: resourceSortVo
     * @description: 获取资源分类表列表
     * @author:      alex
     * @createDate:  2022-03-03 21:31:12
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.ResourceSort>
     */
    IPage<ResourceSort> getPageList(ResourceSortVo resourceSortVo);

    /**
     * @param: resourceSortVo
     * @description: 新增资源分类表
     * @author:      alex
     * @createDate:  2022-03-03 21:31:12
     * @return:      java.lang.String
     */
    String addResourceSort(ResourceSortVo resourceSortVo);

    /**
     * @param: resourceSortVo
     * @description: 编辑资源分类表
     * @author:      alex
     * @createDate:  2022-03-03 21:31:12
     * @return:      java.lang.String
     */
    String editResourceSort(ResourceSortVo resourceSortVo);

    /**
     * @param ids
     * @description: 批量删除资源分类表
     * @author:      alex
     * @createDate:  2022-03-03 21:31:12
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
