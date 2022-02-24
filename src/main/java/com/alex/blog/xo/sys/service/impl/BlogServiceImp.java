package com.alex.blog.xo.sys.service.impl;

import com.alex.blog.xo.sys.entity.Blog;
import com.alex.blog.xo.sys.mapper.BlogMapper;
import com.alex.blog.xo.sys.service.BlogService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-27 10:13:44
 */
@Service
public class BlogServiceImp extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

}
