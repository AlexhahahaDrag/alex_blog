package com.alex.blog.xo.mapper.blog;

import com.alex.blog.xo.blog.entity.Feedback;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 反馈表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2022-02-14 16:38:58
 */
@Mapper
public interface FeedbackMapper extends SuperMapper<Feedback> {

}
