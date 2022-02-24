package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.vo.blog.BlogSortVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 博客分类表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:28
 */
public interface BlogSortService extends SuperService<BlogSort> {

    /**
     * @param BlogSortVo
     * @description: 获取博客分类列表
     * @author:      alex
     * @createDate:  2022/2/11 10:27
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.BlogSort>
     */
    IPage<BlogSort> getPageList(BlogSortVo BlogSortVo);

    /**
     * @description: 获取全部博客分类列表
     * @author:      alex
     * @createDate:  2022/2/11 10:27
     * @return:      java.util.List<com.alex.blog.common.entity.blog.BlogSort>
     */
    List<BlogSort> getList();

    /**
     * @param BlogSortVo
     * @description: 新增博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:28
     * @return:      java.lang.String
     */
    String addBlogSort(BlogSortVo BlogSortVo);

    /**
     * @param BlogSortVo
     * @description: 编辑博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:28
     * @return:      java.lang.String
     */
    String editBlogSort(BlogSortVo BlogSortVo);

    /**
     * @param ids
     * @description: 批量删除博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:30
     * @return:      java.lang.String
     */
    String deleteBatchBlogSort(List<String> ids);

    /**
     * @param BlogSortVo
     * @description: 置顶博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:30
     * @return:      java.lang.String
     */
    String stickBlogSort(BlogSortVo BlogSortVo);

    /**
     * @description: 通过点击量排序博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:31
     * @return:      java.lang.String
     */
    String BlogSortSortByClickCount();

    /**
     * @description: 通过引用量排序博客
     * @author:      alex
     * @createDate:  2022/2/14 15:16
     * @return:      java.lang.String
     */
    String BlogSortSortByCite();

    /**
     * @description: 获取排序最高的博客分类
     * @author:      alex
     * @createDate:  2022/2/11 10:32
     * @return:      com.alex.blog.common.entity.blog.BlogSort
     */
    BlogSort getTopBlogSort();
}
