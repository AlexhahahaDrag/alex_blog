package com.alex.blog.xo.service.task;

import com.alex.blog.common.entity.task.Todo;
import com.alex.blog.common.vo.task.TodoVo;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 代办事项表 服务类
 * </p>
 *
 * @author alex
 * @since 2022-01-26 09:51:38
 */
public interface TodoService extends SuperService<Todo> {

    /**
     * @param done
     * @param adminId
     * @description: 批量更新待办事项的状态
     * @author:      alex
     * @createDate:  2022/1/26 9:59
     * @return:      void
    */
    void toggleAll(Integer done, String adminId);

    /**
     * @param todoVo
     * @description: 获取待办列表
     * @author:      alex
     * @createDate:  2022/1/26 10:00
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.task.Todo>
    */
    IPage<Todo> getPageList(TodoVo todoVo);

    /**
     * @param todoVo
     * @description: 添加待办事项
     * @author:      alex
     * @createDate:  2022/1/26 10:01
     * @return:      java.lang.String
    */
    String addTodo(TodoVo todoVo);

    /**
     * @param todoVo
     * @description: 更新待办事项
     * @author:      alex
     * @createDate:  2022/1/26 10:01
     * @return:      java.lang.String
    */
    String editTodo(TodoVo todoVo);

    /**
     * @param ids
     * @description: 批量删除待办事项
     * @author:      alex
     * @createDate:  2022/1/26 10:02
     * @return:      java.lang.String
    */
    String deleteBatchTodo(List<String> ids);

    /**
     * @param todoVo
     * @description: 批量编辑待办事项
     * @author:      alex
     * @createDate:  2022/1/26 10:03
     * @return:      java.lang.String
    */
    String editBatchTodo(TodoVo todoVo);
}
