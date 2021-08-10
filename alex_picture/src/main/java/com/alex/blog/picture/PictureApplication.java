package com.alex.blog.picture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 *description:  picture启动类
 *author:       alex
 *createDate:   2021/8/5 7:03
 *version:      1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.alex.blog.xo.mapper")
@ComponentScan(basePackages = {
        "com.alex.blog.xo",
        "com.alex.blog.utils.utils",
        "com.alex.blog.common.config"
})
public class PictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class, args);
    }
}
