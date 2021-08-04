package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.SystemConfig;
import com.alex.blog.xo.vo.SystemConfigVo;

/**
 *description:  系统配置服务类
 *author:       alex
 *createDate:   2021/8/2 20:28
 *version:      1.0.0
 */
public interface SystemConfigService extends SuperService<SystemConfig> {

    /**
     * @description:  获取系统配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.SystemConfig
    */
    SystemConfig getConfig();

    /**
     * @param systemCOnfigVo
     * @description:  修改系统配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String editSystemConfig(SystemConfigVo systemCOnfigVo);
}
