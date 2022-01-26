package com.alex.blog.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 *description:  search微服务启动类
 *author:       alex
 *createDate:   2022/1/21 17:22
 *version:      1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.alex.blog.xo",
        "com.alex.blog.search",
        "com.alex.blog.utils.utils",
        "com.alex.blog.common.config"
})
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
