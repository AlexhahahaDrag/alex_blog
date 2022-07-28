package com.alex.blog.admin.restApi.task;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.task.TodoVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.task.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *description:  待办rest api
 *author:       alex
 *createDate:   2022/1/26 9:32
 *version:      1.0.0
 */
@RestController
@Api(value = "待办事项相关接口", tags = {"待办事项相关接口"})
@RequestMapping(value = "/todo")
@RequiredArgsConstructor
public class TodoRestApi {

    private final TodoService todoService;

    @AuthorityVerify
    @ApiOperation(value = "获取待办列表", notes = "获取待办列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getTodoList(@Validated({GetList.class}) @RequestBody TodoVo todoVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return ResultUtil.resultSuccessWithData(todoService.getPageList(todoVo));
    }

    @AuthorityVerify
    @ApiOperation(value = "新增待办列表", notes = "新增待办列表", response = String.class)
    @OperationLogger(value = "新增待办事项")
    @PutMapping(value = "/addTodo")
    public String addTodo(@Validated({Insert.class}) @RequestBody TodoVo todoVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return todoService.addTodo(todoVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "编辑待办列表", notes = "编辑待办列表", response = String.class)
    @OperationLogger(value = "编辑待办事项")
    @PutMapping(value = "/editTodo")
    public String editTodo(@Validated({Update.class}) @RequestBody TodoVo todoVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return todoService.editTodo(todoVo);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除待办列表", notes = "删除待办列表", response = String.class)
    @OperationLogger(value = "删除待办事项")
    @PutMapping(value = "/deleteTodo")
    public String deleteTodo(@Validated({Delete.class}) @RequestBody List<String> ids) {
        return todoService.deleteBatchTodo(ids);
    }

    @AuthorityVerify
    @ApiOperation(value = "批量编辑待办列表", notes = "批量编辑待办列表", response = String.class)
    @OperationLogger(value = "批量编辑待办事项")
    @PutMapping(value = "/batchUpdateTodo")
    public String batchEditTodo(@RequestBody TodoVo todoVo) {
        return todoService.editBatchTodo(todoVo);
    }
}
