package com.alex.blog.common.config.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

/**
 *description:  feign配置文件
 *author:       alex
 *createDate:   2021/8/21 21:29
 *version:      1.0.0
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
        return new BasicAuthenticationInterceptor("user", "password123");
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }
}
