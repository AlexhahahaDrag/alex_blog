package com.alex.blog.xo.utils;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.exception.exceptionType.QueryException;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.enums.EFilePriority;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.global.SQLConf;
import com.alex.blog.xo.service.SystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *description:  web工具类
 *author:       alex
 *createDate:   2021/8/1 22:19
 *version:      1.0.0
 */
@Component
@Slf4j
public class WebUtils {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * @param pictureList
     * @description:  格式化数据获取图片列表
     * @author:       alex
     * @return:       java.util.List<java.lang.String>
     */
    public List<String> getPicture(String pictureList) {
        Map<String, Object> picMap = new HashMap<>();
        SystemConfig systemConfig = parsePictureInfo(pictureList, picMap);
                String picturePriority = systemConfig.getPicturePriority();
        String localPictureBaseUrl = systemConfig.getLocalPictureBaseUrl();
        String qiNiuPictureBaseUrl = systemConfig.getQiNiuPictureBaseUrl();
        String minioPictureBaseUrl = systemConfig.getMinioPictureBaseUrl();
        List<String> picUrls = new ArrayList<>();
        if (SysConf.SUCCESS.equals(picMap.get(SysConf.CODE))) {
            List<Map<String, Object>> picData = (List<Map<String, Object>>) picMap.get(SysConf.DATA);
            if (picData != null && picData.size() > 0) {
                for (Map<String, Object> map : picData) {
                    if (EFilePriority.LOCAL.getCode().equals(picturePriority)) {
                        picUrls.add(localPictureBaseUrl + map.get(SysConf.PIC_URL));
                    } else if(EFilePriority.QI_NIU.getCode().equals(picturePriority)) {
                        picUrls.add(qiNiuPictureBaseUrl + map.get(SysConf.QI_NIU_URL));
                    } else if(EFilePriority.MINIO.getCode().equals(picturePriority)) {
                        picUrls.add(minioPictureBaseUrl + map.get(SysConf.MINIO));
                    }
                }
            }
        }
        return picUrls;
    }

    /**
     * @param pictureList
     * @description:  根据图片列表获取结果集合
     * @author:       alex
     * @return:       java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getPictureMap(String pictureList) {
        Map<String, Object> picMap = new HashMap<>();
        SystemConfig systemConfig = parsePictureInfo(pictureList, picMap);
        String picturePriority = systemConfig.getPicturePriority();
        String localPictureBaseUrl = systemConfig.getLocalPictureBaseUrl();
        String qiNiuPictureBaseUrl = systemConfig.getQiNiuPictureBaseUrl();
        String minioPictureBaseUrl = systemConfig.getMinioPictureBaseUrl();
        List<Map<String, Object>> picUrls = new ArrayList<>();
        if (SysConf.SUCCESS.equals(picMap.get(SysConf.CODE))) {
            List<Map<String, Object>> picData = (List<Map<String, Object>>) picMap.get(SysConf.DATA);
            if (picData != null && picData.size() > 0) {
                for (Map<String, Object> map : picData) {
                    if (StringUtils.isEmpty((String) map.get(SysConf.ID))) {
                        continue;
                    }
                    Map<String, Object> m = new HashMap<>();
                    String url = null;
                    if (EFilePriority.LOCAL.getCode().equals(picturePriority)) {
                        url = localPictureBaseUrl + map.get(SysConf.PIC_URL);
                    } else if(EFilePriority.QI_NIU.getCode().equals(picturePriority)) {
                        url = qiNiuPictureBaseUrl + map.get(SysConf.QI_NIU_URL);
                    } else if(EFilePriority.MINIO.getCode().equals(picturePriority)) {
                        url = minioPictureBaseUrl + map.get(SysConf.MINIO);
                    }
                    m.put(SysConf.ID, url);
                    picUrls.add(m);
                }
            }
        }
        return picUrls;
    }

    /**
     * @param pictureList
     * @param map
     * @description:  解析pictureList信息
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.SystemConfig
    */
    private SystemConfig parsePictureInfo(String pictureList, Map<String, Object> map) {
        SystemConfig systemConfig;
        //从redis中获取系统配置
        String systemConfigJson = redisUtils.get(RedisConf.SYSTEM_CONFIG);
        if (StringUtils.isEmpty(systemConfigJson)) {
            QueryWrapper<SystemConfig> query = new QueryWrapper<>();
            query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
            systemConfig = systemConfigService.getOne(query);
            if (systemConfig == null) {
                // TODO: 2021/8/2 添加queryException
                throw new QueryException(MessageConf.SYSTEM_CONFIG_IS_NOT_EXIST);
            } else {
                //将系统配置存入redis中，有效期24小时
                redisUtils.setEx(RedisConf.SYSTEM_CONFIG, JsonUtils.objectToJson(systemConfig), 24, TimeUnit.HOURS);
            }

        } else {
            systemConfig = JsonUtils.jsonToPojo(systemConfigJson, SystemConfig.class);
            if (systemConfig == null) {
                // TODO: 2021/8/2 添加错误编码
                throw new QueryException("系统配置转化错误");
            }
        }
        map.putAll((Map<String, Object>) JsonUtils.jsonToObject(pictureList, Map.class));
        return systemConfig;
    }
}
