package com.alex.blog.xo.service.sys;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.vo.admin.SystemConfigVo;

import java.util.List;

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
     * @return:       com.alex.blog.common.entity.admin.SystemConfig
    */
    SystemConfig getConfig();

    /**
     * @param systemCOnfigVo
     * @description:  修改系统配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String editSystemConfig(SystemConfigVo systemCOnfigVo);

    /**
     * @param keys
     * @description: 通过key前缀清空redis中的缓存
     * @author:      alex
     * @createDate:  2022/2/14 17:45
     * @return:      java.lang.String
    */
    String cleanRedisByKey(List<String> keys);

    /**
     * @description: 获取系统配置中的搜索模式
     * @author:      alex
     * @createDate:  2022/2/14 17:45
     * @return:      java.lang.String
    */
    String getSearchModel();
}
