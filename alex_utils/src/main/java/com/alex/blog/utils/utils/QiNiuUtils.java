package com.alex.blog.utils.utils;

import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.enums.EQiNiuArea;
import com.alex.blog.common.global.SysConf;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 *description:  七牛工具类
 *author:       alex
 *createDate:   2021/8/11 7:05
 *version:      1.0.0
 */
@Component
@Slf4j
public class QiNiuUtils {

    /*
     * @param localFilePath
     * @param qiNiuConfig
     * @description:  上传文件到七牛云
     * @author:       alex
     * @return:       java.lang.String
    */
    public String uploadQiNiu(File localFilePath, Map<String, String> qiNiuConfig) throws QiniuException {
        //构造一个带指定zone对象的配置类
        Configuration cfg = setQiNiuArea(qiNiuConfig.get(SysConf.QI_NIU_AREA));
        //获取七牛配置
        String accessKey = qiNiuConfig.get(SysConf.QI_NIU_ACCESS_KEY);
        String secretKey = qiNiuConfig.get(SysConf.QI_NIU_SECRET_KEY);
        String bucket = qiNiuConfig.get(SysConf.QI_NIU_BUCKET);
        UploadManager uploadManager = new UploadManager(cfg);
        String key = StringUtils.getUUID();
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        Response res = uploadManager.put(localFilePath, key, token);
        DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
        log.info("七牛云图片上传key:{},七牛云图片上传hash:{}", key, putRet.hash);
        return putRet.key;
    }

    public String uploadQiNiu(File localFilePath, SystemConfig systemConfig) throws QiniuException {
        //构造一个带指定zone对象的配置类

    }

    /**
     * @param area
     * @description:  设置七牛地区
     * @author:       alex
     * @return:       com.qiniu.storage.Configuration
    */
    private Configuration setQiNiuArea(String area) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = null;

        //zon2() 代表华南地区
        switch (EQiNiuArea.valueOf(area).getCode()) {
            case "z0": {
                cfg = new Configuration(Zone.zone0());
            }
            break;
            case "z1": {
                cfg = new Configuration(Zone.zone1());
            }
            break;
            case "z2": {
                cfg = new Configuration(Zone.zone2());
            }
            break;
            case "na0": {
                cfg = new Configuration(Zone.zoneNa0());
            }
            break;
            case "as0": {
                cfg = new Configuration(Zone.zoneAs0());
            }
            break;
            default: {
                return null;
            }
        }
        return cfg;
    }
}
