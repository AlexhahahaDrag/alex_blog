package com.alex.blog.xo.sys.mapper;

import com.alex.blog.xo.sys.entity.Blog;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2022-01-27 10:13:44
 */
@Mapper
public interface BlogMapper extends SuperMapper<Blog> {

}
