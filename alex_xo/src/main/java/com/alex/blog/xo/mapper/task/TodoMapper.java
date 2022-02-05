package com.alex.blog.xo.mapper.task;

import com.alex.blog.common.entity.task.Todo;
import com.alex.blog.common.vo.task.TodoVo;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 代办事项表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2022-01-26 09:51:38
 */
@Mapper
public interface TodoMapper extends SuperMapper<Todo> {

}
