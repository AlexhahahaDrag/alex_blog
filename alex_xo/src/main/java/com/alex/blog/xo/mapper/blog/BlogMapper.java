package com.alex.blog.xo.mapper.blog;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.blog.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2021-11-25 16:17:08
 */
@Mapper
public interface BlogMapper extends SuperMapper<Blog> {

}
