package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.xo.mapper.blog.BlogSortMapper;
import com.alex.blog.xo.service.blog.BlogSortService;
import org.springframework.stereotype.Service;

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

}
