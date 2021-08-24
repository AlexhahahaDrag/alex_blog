package com.alex.blog.common.feign;

import com.alex.blog.common.config.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *description:  后台管理员feign远程调用
 *author:       alex
 *createDate:   2021/8/16 7:01
 *version:      1.0.0
 */
@FeignClient(name = "alex_admin", configuration = FeignConfiguration.class)
public interface AdminFeignClient {

    /**
     * @description:  获取系统配置
     * @author:       alex
     * @return:       java.lang.String
     */
    @RequestMapping(value = "/systemConfig/getSystemConfig", method = RequestMethod.GET)
    String getSystemConfig();
}
