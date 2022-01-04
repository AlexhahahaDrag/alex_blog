package com.alex.blog.admin.restApi.admin;

import com.alex.blog.admin.annotion.authorityVerify.AuthorityVerify;
import com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit;
import com.alex.blog.admin.annotion.operationLogger.OperationLogger;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.base.validator.group.Insert;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.admin.CategoryMenuVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.service.admin.CategoryMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *description:  菜单信息相关接口
 *author:       alex
 *createDate:   2021/10/20 7:11
 *version:      1.0.0
 */
@RestController
@RequestMapping("/categoryMenu")
@Api(value = "菜单信息相关接口", tags = {"菜单信息相关接口"})
@Slf4j
public class CategoryMenuRestApi {

    @Autowired
    private CategoryMenuService categoryMenuService;

    @AuthorityVerify
    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody CategoryMenuVo categoryMenuVo) {
        return ResultUtil.resultSuccessWithData(categoryMenuService.getPageList(categoryMenuVo));
    }

    @AuthorityVerify
    @ApiOperation(value = "获取所有菜单列表", notes = "获取所有菜单列表", response = String.class)
    @PostMapping(value = "/getAllList")
    public String getAllList(@ApiParam(value = "keyword", name = "id") @RequestParam(value = "keyword", required = false) String keyword) {
        return ResultUtil.resultSuccessWithData(categoryMenuService.getAllList(keyword));
    }

    @AuthorityVerify
    @ApiOperation(value = "获取所有二级按钮", notes = "获取所有二级按钮", response = String.class)
    @PostMapping(value = "/getButtonAll")
    public String getButtonAll(@ApiParam(value = "keyword", name = "id") @RequestParam(value = "ketword", required = false) String keyword) {
        return ResultUtil.resultSuccessWithData(categoryMenuService.getButtonAllList(keyword));
    }

    @AvoidRepeatableCommit
    @AuthorityVerify
    @OperationLogger(value = "新增菜单")
    @ApiOperation(value = "新增菜单", notes = "新增菜单", response = String.class)
    @PutMapping(value = "/add")
    public String addCategory(@Validated({Insert.class}) @RequestBody CategoryMenuVo categoryMenuVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryMenuService.addCategoryMenu(categoryMenuVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "编辑菜单")
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单", response = String.class)
    @PostMapping(value = "/edit")
    public String updateCategory(@Validated({Update.class}) @RequestBody CategoryMenuVo categoryMenuVo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return categoryMenuService.editCategoryMenu(categoryMenuVo);
    }

    @AuthorityVerify
    @OperationLogger(value = "删除菜单")
    @ApiOperation(value = "删除菜单", notes = "删除菜单", response = String.class)
    @PostMapping(value = "/delete")
    public String deleteCategory(@Validated({Delete.class}) @ApiParam(value = "id", name = "id", required = true) @RequestParam(value = "id", name = "id") String id) {
        return categoryMenuService.deleteCategoryMenu(id);
    }

    @AuthorityVerify
    @OperationLogger(value = "置顶菜单")
    @ApiOperation(value = "置顶菜单", notes = "置顶菜单", response = String.class)
    @PostMapping("/stick")
    public String stick(@Validated({Update.class}) @RequestBody CategoryMenuVo categoryMenuVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return categoryMenuService.stickCategoryMenu(categoryMenuVo);
    }
}
