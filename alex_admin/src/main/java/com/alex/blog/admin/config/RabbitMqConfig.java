package com.alex.blog.admin.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *description:  rabbitmq 配置类
 *author:       alex
 *createDate:   2021/12/7 11:37
 *version:      1.0.0
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
