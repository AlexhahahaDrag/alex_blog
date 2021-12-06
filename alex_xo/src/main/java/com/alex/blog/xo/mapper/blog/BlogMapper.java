package com.alex.blog.xo.mapper.blog;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.blog.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> getBlogCountByTag();

    List<Map<String, Object>> getBlogCountBySort();
}
