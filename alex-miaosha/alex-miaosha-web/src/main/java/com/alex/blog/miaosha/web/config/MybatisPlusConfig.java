package com.alex.blog.miaosha.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *description:  
 *author:       alex
 *createDate:   2020/11/7 10:50
 *version:      1.0.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.alex.blog.miaosha.xo.mapper")
public class MybatisPlusConfig {

    // 最新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MybatisPlusSqlInjector mybatisPlusSqlInjector() {
        return new MybatisPlusSqlInjector();
    }

    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceMonitorInterceptor performanceInterceptor() {
        return new PerformanceMonitorInterceptor();
    }
}
