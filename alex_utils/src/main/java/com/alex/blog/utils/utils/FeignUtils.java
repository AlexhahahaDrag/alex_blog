package com.alex.blog.utils.utils;

import com.alex.blog.common.entity.admin.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *description:  feignUtils工具类
 *author:       alex
 *createDate:   2021/8/9 6:06
 *version:      1.0.0
 */
@Component
@Slf4j
public class FeignUtils {

    // TODO: 2021/8/13 完善方法
    public Map<String, String> getSystemConfigMap(String token) {
        return null;
    }

    public SystemConfig getSystemConfig() {
        return null;
    }

    public SystemConfig getSystemConfig(String token) {
        return null;
    }

    public SystemConfig getSystemConfigByMap(Map<String, String> systemConfigMap) {
        return null;
    }

    public Map<String, String> getSystemConfigByWebToken(String token) {
        return null;
    }
}
