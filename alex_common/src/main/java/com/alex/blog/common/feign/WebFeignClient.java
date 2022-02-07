package com.alex.blog.common.feign;

import com.alex.blog.common.entity.blog.Blog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *description:  web服务feign接口
 *author:       alex
 *createDate:   2022/1/25 17:01
 *version:      1.0.0
 */
//@FeignClient(name = "alex_admin", configuration = FeignConfiguration.class)
public interface WebFeignClient {

    /**
     * @param id
     * @description: 通过id获取博客信息
     * @author:      alex
     * @createDate:  2022/1/25 17:11
     * @return:      java.lang.String
     */
    @GetMapping(value = "/blog/getBlogById")
    Blog getBlogById(@RequestParam(value = "id") String id);

    /**
     * @param pageSize
     * @param currentPage
     * @description: 通过id获取博客信息
     * @author:      alex
     * @createDate:  2022/1/25 17:11
     * @return:      java.lang.String
     */
    @GetMapping(value = "/blog/getBlogById")
    IPage<Blog> getBlogPage(@RequestParam(value = "currentPage") Long currentPage,
                            @RequestParam(value = "pageSize") Long pageSize);
}
