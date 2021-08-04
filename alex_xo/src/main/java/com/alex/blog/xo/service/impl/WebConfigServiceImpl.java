package com.alex.blog.xo.service.impl;

import com.alex.blog.base.exception.exceptionType.QueryException;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.WebConfig;
import com.alex.blog.common.enums.EAccountType;
import com.alex.blog.common.enums.ELoginType;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.global.SQLConf;
import com.alex.blog.xo.mapper.WebConfigMapper;
import com.alex.blog.xo.service.WebConfigService;
import com.alex.blog.xo.vo.WebConfigVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *description:  网站配置服务实现类
 *author:       alex
 *createDate:   2021/8/1 21:06
 *version:      1.0.0
 */
@Service
public class WebConfigServiceImpl extends SuperServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

    @Autowired
    private WebConfigService webConfigService;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Autowired
    private WebUtils webUtils;
    
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @description:  获取网站配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.WebConfig
    */
    @Override
    public WebConfig getWebConfig() {
        QueryWrapper<WebConfig> query = new QueryWrapper<>();
        query.orderByDesc(SysConf.UPDATE_TIME);
        WebConfig webConfig = webConfigService.getOne(query);
        if (webConfig != null) {
            //获取图片
            if (StringUtils.isNotEmpty(webConfig.getLogo())) {
                String pictureList = pictureFeignClient.getPicture(webConfig.getLogo(), SysConf.FILE_SEGMENTATION);
                webConfig.setPhotoList(webUtils.getPicture(pictureList));
            }
            //获取支付宝收款二维码
            if (StringUtils.isNotEmpty(webConfig.getAliPay())) {
                String pictureList = pictureFeignClient.getPicture(webConfig.getAliPay(), SysConf.FILE_SEGMENTATION);
                webConfig.setAliPayPhoto(webUtils.getPicture(pictureList).get(0));
            }
            //获取微信收款二维码
            if (StringUtils.isNotEmpty(webConfig.getWeChatPay())) {
                String pictureList = pictureFeignClient.getPicture(webConfig.getWeChatPay(), SysConf.FILE_SEGMENTATION);
                webConfig.setWeChatPhoto(webUtils.getPicture(pictureList).get(0));
            }
        }
        return webConfig;
    }

    /**
     * @description:  获取网站名称
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String getWebSiteName() {
        QueryWrapper<WebConfig> query = new QueryWrapper<>();
        query.last(SysConf.LIMIT_ONE);
        WebConfig webConfig = webConfigService.getOne(query);
        return webConfig == null || webConfig.getName() == null ? "" : webConfig.getName();
    }

    /**
     * @description:  获取网站配置显示列表    
     * @author:       alex
     * @return:       com.alex.blog.common.entity.WebConfig
    */
    @Override
    public WebConfig getWebConfigByShowList() {
        //从redis中获取ip来源
        String webConfigResult = redisUtils.get(RedisConf.WEB_CONFIG);
        if (StringUtils.isNotEmpty(webConfigResult)) {
            return JsonUtils.jsonToPojo(webConfigResult, WebConfig.class);
        }
        QueryWrapper<WebConfig> query = new QueryWrapper<>();
        query.orderByDesc(SQLConf.UPDATE_TIME);
        WebConfig webConfig = webConfigService.getOne(query);
        if (webConfig == null) {
            throw new QueryException("00101", MessageConf.SYSTEM_CONFIG_NOT_EXIST);
        }
        StringBuffer sb = new StringBuffer;
        if (StringUtils.isNotEmpty(webConfig.getLogo())) {
            sb.append(webConfig.getLogo()).append(Constants.SYMBOL_COMMA);
        }

        if (StringUtils.isNotEmpty(webConfig.getAliPay())) {
            sb.append(webConfig.getAliPay()).append(Constants.SYMBOL_COMMA);
        }

        if (StringUtils.isNotEmpty(webConfig.getWeChatPay())) {
            sb.append(webConfig.getWeChatPay()).append(Constants.SYMBOL_COMMA);
        }
        String pictureResult = "";
        if (sb.length() > 0) {
            // TODO: 2021/8/4 添加图片服务模块 
            pictureResult = pictureFeignClient.getPicture(sb.toString(), Constants.SYMBOL_COMMA);
        }
        List<Map<String, Object>> pictureList = webUtils.getPictureMap(pictureResult);
        HashMap<String, String> pictureMap = new HashMap<>();
        pictureList.forEach(item -> {
            pictureMap.put(item.get(SysConf.ID).toString(), item.get(SQLConf.URL).toString());
        });
        //获取logo
        if (StringUtils.isNotEmpty(webConfig.getLogo()) && pictureMap.get(webConfig.getLogo()) != null) {
            webConfig.setLogoPhoto(pictureMap.get(webConfig.getLogo()));
        }
        //获取阿里支付码
        if (StringUtils.isNotEmpty(webConfig.getAliPay()) && pictureMap.get(webConfig.getAliPay()) != null) {
            webConfig.setLogoPhoto(pictureMap.get(webConfig.getAliPay()));
        }
        //获取微信支付码
        if (StringUtils.isNotEmpty(webConfig.getWeChatPay()) && pictureMap.get(webConfig.getWeChatPay()) != null) {
            webConfig.setLogoPhoto(pictureMap.get(webConfig.getWeChatPay()));
        }
        String email = webConfig.getEmail();
        String qqNumber = webConfig.getQqNumber();
        String qqGroup = webConfig.getQqGroup();
        String github = webConfig.getGithub();
        String gitee = webConfig.getGitee();
        String weChat = webConfig.getWeChat();
        // 将联系方式全部置空
        webConfig.setEmail("");
        webConfig.setQqNumber("");
        webConfig.setQqGroup("");
        webConfig.setGithub("");
        webConfig.setGitee("");
        webConfig.setWeChat("");
        List<String> showList = JsonUtils.jsonToList(webConfig.getShowList(), String.class);
        showList.forEach(item -> {
            if (EAccountType.EMAIL.getCode().equals(item)) {
                webConfig.setEmail(email);
            } else if (EAccountType.QQNUMBER.getCode().equals(item)) {
                webConfig.setQqNumber(qqNumber);
            } else if (EAccountType.QQGROUP.getCode().equals(item)) {
                webConfig.setQqGroup(qqGroup);
            } else if (EAccountType.GITHUB.getCode().equals(item)) {
                webConfig.setGithub(github);
            } else if (EAccountType.GITEE.getCode().equals(item)) {
                webConfig.setGitee(gitee);
            } else if (EAccountType.WECHAT.getCode().equals(item)) {
                webConfig.setWeChat(weChat);
            }
        });
        //将webConfig存放到redis中去。过期时间24小时
        redisUtils.setEx(RedisConf.WEB_CONFIG, JsonUtils.objectToJson(webConfig), 24, TimeUnit.HOURS);
        return webConfig;
    }

    /**
     * @param webConfigVo
     * @description:  新增或修改网站配置  
     * @author:       alex
     * @return:       java.lang.String
    */
    @Override
    public String addOrEditWebConfig(WebConfigVo webConfigVo) {
        if (StringUtils.isEmpty(webConfigVo.getId())) {
            WebConfig webConfig = new WebConfig();
            BeanUtils.copyProperties(webConfigVo, webConfig);
            webConfigService.save(webConfig);
        } else {
            WebConfig webConfig = webConfigService.getById(webConfigVo.getId());
            BeanUtils.copyProperties(webConfigVo, webConfig, SQLConf.STATUS, SysConf.ID);
            webConfigService.updateById(webConfig);
        }
        //修改配置后，清空redis中的webConfig
        redisUtils.delete(RedisConf.WEB_CONFIG);
        //同时清空redis中的登陆方式
        Set<String> keys = redisUtils.keys(RedisConf.LOGIN_TYPE + Constants.SYMBOL_STAR);
        redisUtils.delete(keys);
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    /**
     * @param loginType
     * @description:  是公开的登录方式
     * @author:       alex
     * @return:       boolean
    */
    @Override
    public boolean isOpenLoginType(String loginType) {
        String loginTypeJson = redisUtils.get(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + loginType);
        if (StringUtils.isNotEmpty(loginTypeJson)) {
            return true;
        } else if (loginTypeJson != null && loginTypeJson.length() == 0) {
            return false;
        }
        QueryWrapper<WebConfig> query = new QueryWrapper<>();
        query.orderByDesc(SysConf.UPDATE_TIME);
        WebConfig webConfig = webConfigService.getOne(query);
        if (webConfig == null) {
            throw new QueryException("00101", MessageConf.SYSTEM_CONFIG_NOT_EXIST);
        }
        String loginTypeListJson = webConfig.getLoginTypeList();
        //
        List<String> loginTypeList = JsonUtils.jsonToList(loginTypeListJson, String.class);
        for(String item : loginTypeList) {
            if (ELoginType.PASSWORD.getCode().equals(item)) {
                redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + RedisConf.PASSWORD, ELoginType.PASSWORD.getValue());
            } else if (ELoginType.GITEE.getCode().equals(item)) {
                redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + RedisConf.GITEE, ELoginType.GITEE.getValue());
            }else if (ELoginType.GITHUB.getCode().equals(item)) {
                redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + RedisConf.GITHUB, ELoginType.GITHUB.getValue());
            }else if (ELoginType.QQ.getCode().equals(item)) {
                redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + RedisConf.QQ, ELoginType.QQ.getValue());
            } else if (ELoginType.WECHAT.getCode().equals(item)) {
                redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + RedisConf.WECHAT, ELoginType.WECHAT.getValue());
            }
        }
        loginTypeJson = redisUtils.get(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + loginType);
        if (StringUtils.isNotEmpty(loginTypeJson)) {
            return true;
        } else {
            redisUtils.set(RedisConf.LOGIN_TYPE + RedisConf.SEGMENTATION + loginType, "");
            return false;
        }
    }
}
