package com.alex.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *description:  网关启动类  注册到nacos注册中心
 *author:       alex
 *createDate:   2020/11/14 12:25
 *version:      1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
    }
}
