package com.alex.blog.xo.mapper.blog;

import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:48
 */
@Mapper
public interface TagMapper extends SuperMapper<Tag> {

}
