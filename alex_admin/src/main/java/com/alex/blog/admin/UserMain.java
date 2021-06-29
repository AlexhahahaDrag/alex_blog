package com.alex.blog.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *description:  启动类 注册到nacos注册中心
 *author:       alex
 *createDate:   2020/11/14 12:17
 *version:      1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class
UserMain {

    public static void main(String[] args) {
        SpringApplication.run(UserMain.class, args);
    }
}
