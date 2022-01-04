package com.alex.blog.xo.mapper.blog;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.common.entity.blog.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
@Mapper
public interface CommentMapper extends SuperMapper<Comment> {

}
