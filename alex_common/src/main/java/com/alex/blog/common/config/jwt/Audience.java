package com.alex.blog.common.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *description:  jwt相关配置类
 *author:       alex
 *createDate:   2021/7/24 22:00
 *version:      1.0.0
 */
@ConfigurationProperties(prefix = "audience")
@Component
@Data
public class Audience {

    private String clientId;

    private String base64Secret;

    private String name;

    private int expiresSecond;
}
