package com.alex.blog.miaosha.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *description:  秒杀系统启动类
 *author:       alex
 *createDate:   2022/6/1 16:34
 *version:      1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.alex.blog.miaosha.web"})
@MapperScan("com.alex.blog.miaosha.xo.mapper")
public class MiaoshaMain {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaMain.class, args);
    }
}
