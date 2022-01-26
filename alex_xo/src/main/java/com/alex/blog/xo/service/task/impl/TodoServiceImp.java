package com.alex.blog.xo.service.task.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.entity.task.Todo;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.task.TodoVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.task.TodoMapper;
import com.alex.blog.xo.service.task.TodoService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 代办事项表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-26 09:51:38
 */
@Service
public class TodoServiceImp extends SuperServiceImpl<TodoMapper, Todo> implements TodoService {

    @Autowired
    private TodoMapper todoMapper;

    @Override
    public void toggleAll(Integer done, String adminId) {
        QueryWrapper<Todo> query = new QueryWrapper<>();
        query.eq(SysConf.ADMIN_ID, adminId);
        this.update(query);
    }

    @Override
    public IPage<Todo> getPageList(TodoVo todoVo) {
        //获取当前登录人id
        String adminId = RequestHolder.getAdminId();
        Page<Todo> page = new Page<>();
        page.setCurrent(todoVo.getCurrentPage());
        page.setSize(todoVo.getPageSize());
        QueryWrapper<Todo> query = new QueryWrapper<>();
        query.eq(SysConf.ADMIN_ID, adminId).eq(SysConf.STATUS, EStatus.ENABLE.getCode())
                .orderByDesc(SysConf.OPERATE_TIME);
        if (StringUtils.isNotEmpty(todoVo.getKeyword()) && StringUtils.isNotEmpty(todoVo.getKeyword().trim())) {
            query.like(SysConf.TEXT, todoVo.getKeyword().trim());
        }
        IPage<Todo> pageList = this.page(page, query);
        return pageList;
    }

    @Override
    public String addTodo(TodoVo todoVo) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoVo, todo);
        todo.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editTodo(TodoVo todoVo) {
        if (StringUtils.isEmpty(todoVo.getId())) {
            return ResultUtil.resultSuccessWithMessage("id不能为空");
        }
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoVo, todo);
        todo.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchTodo(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultSuccessWithMessage("不能为空");
        }
        todoMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String editBatchTodo(TodoVo todoVo) {
        String adminId = RequestHolder.getAdminId();
        this.toggleAll(todoVo.getDone(), adminId);
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }
}
