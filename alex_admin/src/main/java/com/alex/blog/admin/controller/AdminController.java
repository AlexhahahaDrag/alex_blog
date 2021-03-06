package com.alex.blog.admin.controller;

import com.alex.blog.common.entity.LoginParams;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.common.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *description:
 *author:       alex
 *createDate:   2020/11/7 10:28
 *version:      1.0.0
 */
@RestController
@Api(value = "管理员controller", tags = "管理员管理")
@RequestMapping("/authc")
@RefreshScope
@Slf4j
public class AdminController {

}
