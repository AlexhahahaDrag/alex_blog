package com.alex.blog.utils.utils;

import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.enums.EOpenStatus;
import com.alex.blog.common.exception.AlexException;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *description:  feignUtils工具类
 *author:       alex
 *createDate:   2021/8/9 6:06
 *version:      1.0.0
 */
@Component
@Slf4j
public class FeignUtils {

    @Autowired
    private RedisUtils redisUtils;

//    @Autowired
//    private AdminFeignClient adminFeignClient;

    /**
     * @param token
     * @description:  通过token获取系统配置文件
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.String>
    */
    public Map<String, String> getSystemConfigMap(String token) {
        //判断该token的有效性
        String adminJsonResult = redisUtils.get(RedisConf.LOGIN_TOKEN_KEY + RedisConf.SEGMENTATION + token);
        if (StringUtils.isEmpty(adminJsonResult)) {
            throw new AlexException(SysConf.ERROR, MessageConf.INVALID_TOKEN);
        }
        Map<String, String> resultMap = new HashMap<>();
        String systemConfigResult = redisUtils.get(RedisConf.SYSTEM_CONFIG);
        if (StringUtils.isNotEmpty(systemConfigResult)) {
            resultMap = JsonUtils.jsonToMap(systemConfigResult, String.class);
        } else {
            String systemConfigStr = "";
//            String systemConfigStr = adminFeignClient.getSystemConfig();
            resultMap = JsonUtils.jsonToMap(systemConfigStr, String.class);
            if (SysConf.SUCCESS.equals(resultMap.get(SysConf.CODE))) {
                //将token存储到redis中，设置30分钟过期时间
                redisUtils.setEx(RedisConf.SYSTEM_CONFIG, JsonUtils.objectToJson(resultMap), 30, TimeUnit.MINUTES);
            }
        }
        return resultMap;
    }

    /**
     * @description:  获取系统配置，无论是admin还是web
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.SystemConfig
    */
    public SystemConfig getSystemConfig() {
        HttpServletRequest request = RequestHolder.getRequest();
        SystemConfig systemConfig = new SystemConfig();
        if (request != null) {
            //后台携带token
            Object token = request.getAttribute(SysConf.TOKEN);
            //参数中携带token
            String paramsToken = request.getParameter(SysConf.TOKEN);
            //获取平台（web：门户，admin：管理员）
            String platform = request.getParameter(SysConf.PLATFORM);
            Map<String, String> systemConfigMap;
            //判断是否是web端发送过来的请求
            if (SysConf.WEB.equals(platform) || (paramsToken != null && paramsToken.length() == Constants.THIRTY_TWO)) {
                systemConfigMap = getSystemConfigByWebToken(paramsToken);
            } else {
                if (token != null) {
                    systemConfigMap = getSystemConfigMap((String) token);
                } else {
                    systemConfigMap = getSystemConfigMap(paramsToken);
                }
            }
            if (systemConfigMap == null) {
                log.error("请先设置系统配置信息");
                throw new AlexException("00107", "请先设置系统配置信息");
            }
            String uploadQiNiu = systemConfigMap.get(SysConf.UPLOAD_QI_NIU);
            String uploadLocal = systemConfigMap.get(SysConf.UPLOAD_LOCAL);
            String localPictureBaseUrl = systemConfigMap.get(SysConf.LOCAL_PICTURE_BASE_URL);

            String qiNiuPictureBaseUrl = systemConfigMap.get(SysConf.QI_NIU_PICTURE_BASE_URL);
            String qiNiuAccessKey = systemConfigMap.get(SysConf.QI_NIU_ACCESS_KEY);
            String qiNiuSecretKey = systemConfigMap.get(SysConf.QI_NIU_SECRET_KEY);
            String qiNiuBucket = systemConfigMap.get(SysConf.QI_NIU_BUCKET);
            String qiNiuArea = systemConfigMap.get(SysConf.QI_NIU_AREA);

            String minioEndPoint = systemConfigMap.get(SysConf.MINIO_END_POINT);
            String minioAccessKey = systemConfigMap.get(SysConf.MINIO_ACCESS_KEY);
            String minioSecretKey = systemConfigMap.get(SysConf.MINIO_SECRET_KEY);
            String minioBucket = systemConfigMap.get(SysConf.MINIO_BUCKET);
            String uploadMinio = systemConfigMap.get(SysConf.UPLOAD_MINIO);
            String minioPictureBaseUrl = systemConfigMap.get(SysConf.MINIO_PICTURE_BASE_URL);
            String minioSecure = systemConfigMap.get(SysConf.MINIO_SECURE);
            String minioPort = systemConfigMap.get(SysConf.MINIO_PORT);

            //判断七牛云配置参数
            if (EOpenStatus.OPEN.getCode().equals(uploadQiNiu) && (StringUtils.isEmpty(qiNiuAccessKey)
            || StringUtils.isEmpty(qiNiuArea) || StringUtils.isEmpty(qiNiuBucket) || StringUtils.isEmpty(qiNiuPictureBaseUrl) || StringUtils.isEmpty(qiNiuSecretKey))) {
                throw new AlexException("00107", MessageConf.PLEASE_SET_QI_NIU);
            }

            //判断本地配置参数
            if (EOpenStatus.OPEN.getCode().equals(uploadLocal) && StringUtils.isEmpty(localPictureBaseUrl)) {
                throw new AlexException("00108", MessageConf.PLEASE_SET_LOCAL);
            }

            //判断minio配置参数
            if (EOpenStatus.OPEN.getCode().equals(uploadMinio) && (StringUtils.isEmpty(minioAccessKey)
                    || StringUtils.isEmpty(minioBucket) || StringUtils.isEmpty(minioEndPoint) || StringUtils.isEmpty(minioPictureBaseUrl) || StringUtils.isEmpty(minioPort) || StringUtils.isEmpty(minioSecretKey) || StringUtils.isEmpty(minioSecure))) {
                throw new AlexException("00109", MessageConf.PLEASE_SET_MINIO);
            }

            //设置本地信息
            systemConfig.setUploadQiNiu(uploadQiNiu);
            systemConfig.setUploadLocal(uploadLocal);
            systemConfig.setLocalPictureBaseUrl(localPictureBaseUrl);
            //设置七牛信息
            systemConfig.setQiNiuPictureBaseUrl(qiNiuPictureBaseUrl);
            systemConfig.setQiNiuAccessKey(qiNiuAccessKey);
            systemConfig.setQiNiuSecretKey(qiNiuSecretKey);
            systemConfig.setQiNiuBucket(qiNiuBucket);
            systemConfig.setQiNiuArea(qiNiuArea);
            //设置minio信息
            systemConfig.setMinioEndPoint(minioEndPoint);
            systemConfig.setMinioAccessKey(minioAccessKey);
            systemConfig.setMinioSecretKey(minioSecretKey);
            systemConfig.setMinioBucket(minioBucket);
            systemConfig.setUploadMinio(uploadMinio);
            systemConfig.setMinioPictureBaseUrl(minioPictureBaseUrl);
            systemConfig.setMinioSecure(Boolean.parseBoolean(minioSecure));
            systemConfig.setMinioPort(Integer.parseInt(minioPort));
        }
        return systemConfig;
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
