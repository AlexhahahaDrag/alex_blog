package com.alex.blog.admin.restApi;

import com.alex.blog.xo.service.CategoryMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


}
