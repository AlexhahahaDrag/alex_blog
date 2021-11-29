package com.alex.blog.admin.restApi.blog;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *description:  博客接口 restApi
 *author:       alex
 *createDate:   2021/11/25 15:42
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/blog")
@Api(value = "博客相关接口", tags = {"博客相关接口"})
@Slf4j
public class BlogRestApi {


}
