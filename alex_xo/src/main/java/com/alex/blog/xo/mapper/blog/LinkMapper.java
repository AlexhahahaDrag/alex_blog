package com.alex.blog.xo.mapper.blog;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.blog.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 友情链接表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2022-01-27 17:32:37
 */
@Mapper
public interface LinkMapper extends SuperMapper<Link> {

}
