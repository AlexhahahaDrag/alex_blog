package com.alex.blog.picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *description:  picture启动类
 *author:       alex
 *createDate:   2021/8/5 7:03
 *version:      1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class, args);
    }
}
