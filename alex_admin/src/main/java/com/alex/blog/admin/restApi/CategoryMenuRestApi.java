package com.alex.blog.admin.restApi;

import com.alex.blog.admin.annotion.AuthorityVerify;
import com.alex.blog.admin.annotion.OperationLogger;
import com.alex.blog.base.validator.group.Update;
import com.alex.blog.common.exception.ThrowableUtils;
import com.alex.blog.common.vo.admin.CategoryMenuVo;
import com.alex.blog.xo.service.CategoryMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @OperationLogger(value = "置顶菜单")
    @ApiOperation(value = "置顶菜单", notes = "置顶菜单", response = String.class)
    @PostMapping("/stick")
    public String stick(@Validated({Update.class}) @RequestBody CategoryMenuVo categoryMenuVo, BindingResult result) {
        //参数校验
        ThrowableUtils.checkParamArgument(result);
        return categoryMenuService.stickCategoryMenu(categoryMenuVo);
    }
}
