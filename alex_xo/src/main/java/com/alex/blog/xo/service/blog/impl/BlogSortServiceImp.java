package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.enums.EPublish;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.BlogSortVo;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.blog.BlogSortMapper;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.BlogSortService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 博客分类表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:28
 */
@Service
public class BlogSortServiceImp extends SuperServiceImpl<BlogSortMapper, BlogSort> implements BlogSortService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    @Override
    public IPage<BlogSort> getPageList(BlogSortVo BlogSortVo) {
        return null;
    }

    @Override
    public List<BlogSort> getList() {
        QueryWrapper<BlogSort> query = getQuery();
        query.orderByDesc(SysConf.SORT);
        return this.list(query);
    }

    @Override
    public String addBlogSort(BlogSortVo BlogSortVo) {
        QueryWrapper<BlogSort> query = getQuery();
        query.eq(SysConf.SORT_NAME, BlogSortVo.getSortName());
        BlogSort one = this.getOne(query);
        if (one != null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.ENTITY_EXIST);
        }
        BlogSort blogSort = new BlogSort();
        BeanUtils.copyProperties(BlogSortVo, blogSort);
        //默认新增的分类是有效的
        blogSort.setStatus(EStatus.ENABLE.getCode());
        blogSort.insert();
        return ResultUtil.resultErrorWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editBlogSort(BlogSortVo BlogSortVo) {
        BlogSort one = this.getById(BlogSortVo.getId());
        if (one == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        //如果名称不一致校验名称是否重复
        if (!one.getSortName().equals(BlogSortVo.getSortName())) {
            QueryWrapper<BlogSort> query = getQuery();
            query.eq(SysConf.SORT_NAME, BlogSortVo.getSortName());
            BlogSort one1 = this.getOne(query);
            if (one1 != null) {
                return ResultUtil.resultErrorWithMessage("博客分类名称已存在!");
            }
        }
        BeanUtils.copyProperties(BlogSortVo, one);
        one.updateById();
        // 删除和博客相关的Redis缓存
        blogService.deleteRedisByBlogSort();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchBlogSort(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        //判断要删除的博客标签下是否有博客
        QueryWrapper<Blog> blogQuery = new QueryWrapper<>();
        blogQuery.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).in(SysConf.BLOG_SORT_ID, ids);
        int count = blogService.count(blogQuery);
        if (count > 0) {
            return ResultUtil.resultErrorWithMessage(MessageConf.BLOG_UNDER_THIS_SORT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickBlogSort(BlogSortVo BlogSortVo) {
        BlogSort BlogSort = this.getById(BlogSortVo.getId());
        if (BlogSort == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        QueryWrapper<BlogSort> query = getQuery();
        query.orderByDesc(SysConf.SORT).last(SysConf.LIMIT_ONE);
        BlogSort one = this.getOne(query);
        int maxSort = one.getSort() + (one.getId().equals(BlogSortVo.getId()) ? 0 : 1);
        BlogSort.setSort(maxSort);
        BlogSort.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String BlogSortSortByClickCount() {
        QueryWrapper<BlogSort> query = getQuery();
        query.orderByDesc(SysConf.CLICK_COUNT);
        List<BlogSort> BlogSortList = this.list(query);
        for (int i = BlogSortList.size() - 1; i >= 0; i--) {
            BlogSort BlogSort = BlogSortList.get(i);
            BlogSort.setSort(i + 1);
        }
        this.updateBatchById(BlogSortList);
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String BlogSortSortByCite() {
        QueryWrapper<BlogSort> BlogSortQuery = getQuery();
        List<BlogSort> BlogSortList = this.list(BlogSortQuery);
        QueryWrapper<Blog> blogQuery = new QueryWrapper<>();
        blogQuery.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        List<Blog> blogList = blogService.list(blogQuery);
        Map<String, Integer> map = new HashMap<>();
        blogList.forEach(item -> {
            String BlogSortIds = item.getBlogSortId();
            List<String> BlogSortIdsList = StringUtils.splitStringByCode(BlogSortIds, SysConf.FILE_SEGMENTATION);
            for (String BlogSortId : BlogSortIdsList) {
                map.put(BlogSortId, map.getOrDefault(BlogSortId, 0) + 1);
            }
        });
        BlogSortList.forEach(i -> {
            i.setSort(map.get(i.getId()));
        });
        this.updateBatchById(BlogSortList);
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public BlogSort getTopBlogSort() {
        QueryWrapper<BlogSort> query = getQuery();
        query.last(SysConf.LIMIT_ONE).orderByDesc(SysConf.SORT);
        return this.getOne(query);
    }

    private QueryWrapper<BlogSort> getQuery() {
        QueryWrapper<BlogSort> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
