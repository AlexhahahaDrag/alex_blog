package com.alex.blog.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 *description:  启动类 注册到nacos注册中心
 *author:       alex
 *createDate:   2020/11/14 12:17
 *version:      1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.alex.blog.xo.mapper")
@ComponentScan(basePackages = {
        "com.alex.blog.xo",
        "com.alex.blog.admin",
        "com.alex.blog.utils.utils",
        "com.alex.blog.common.config",
        "com.alex.blog.base.config"
})
public class AdminMain {

    public static void main(String[] args) {
        SpringApplication.run(AdminMain.class, args);
    }
}