package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.WebConfig;
import com.alex.blog.xo.vo.WebConfigVo;

/**
 *description:  网站配置服务类
 *author:       alex
 *createDate:   2021/8/1 19:44
 *version:      1.0.0
 */
public interface WebConfigService extends SuperService<WebConfig> {

    /**
     * @description:  获取网站配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.WebConfig
    */
    WebConfig getWebConfig();

    /**
     * @description:  获取网站名称
     * @author:       alex
     * @return:       java.lang.String
    */
    String getWebSiteName();

    /**
     * @description:  通过显示列表获取配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.WebConfig
    */
    WebConfig getWebConfigByShowList();

    /**
     * @param webConfigVo
     * @description:  编辑网站配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String addOrEditWebConfig(WebConfigVo webConfigVo);

    /**
     * @param loginType
     * @description:  是否开启该登陆方式(账号密码、码云、github、qq、微信)
     * @author:       alex
     * @return:       boolean
    */
    boolean isOpenLoginType(String loginType);
}
