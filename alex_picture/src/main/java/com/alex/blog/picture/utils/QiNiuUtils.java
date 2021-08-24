package com.alex.blog.picture.utils;

import com.alex.blog.base.exception.exceptionType.QueryException;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.enums.EOpenStatus;
import com.alex.blog.common.enums.EQiNiuArea;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.FeignUtils;
import com.alex.blog.utils.utils.StringUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *description:  七牛云工具类
 *author:       alex
 *createDate:   2021/8/16 6:29
 *version:      1.0.0
 */
@Slf4j
@Component
public class QiNiuUtils {

    private FeignUtils feignUtils;

    /**
     * @param localFilePath
     * @param qiNiuConfig
     * @description:  根据七牛map配置上传文件到七牛云
     * @author:       alex
     * @return:       java.lang.String
    */
    public String uploadQiniu(File localFilePath, Map<String, String> qiNiuConfig) throws QiniuException {
        String bucket = qiNiuConfig.get(SysConf.QI_NIU_BUCKET);
        String area = qiNiuConfig.get(SysConf.QI_NIU_AREA);
        String accessKey = qiNiuConfig.get(SysConf.QI_NIU_ACCESS_KEY);
        String secretKey = qiNiuConfig.get(SysConf.QI_NIU_SECRET_KEY);
        return uploadFile(localFilePath, area, bucket, accessKey, secretKey);
    }

    /**
     * @param localFilePath
     * @param qiNiuConfig
     * @description:  根据系统配置上传到七牛云
     * @author:       alex
     * @return:       java.lang.String
     */
    public String uploadQiniu(File localFilePath, SystemConfig qiNiuConfig) throws QiniuException {
        String area = qiNiuConfig.getQiNiuArea();
        String accessKey = qiNiuConfig.getQiNiuAccessKey();
        String secretKey = qiNiuConfig.getQiNiuSecretKey();
        String bucket = qiNiuConfig.getQiNiuBucket();
        return uploadFile(localFilePath, area, bucket, accessKey, secretKey);
    }

    /**
     * @param localFilePath
     * @param area
     * @param bucket
     * @param accessKey
     * @param secretKey
     * @description:  上传文件
     * @author:       alex
     * @return:       java.lang.String
    */
    private String uploadFile(File localFilePath, String area, String bucket, String accessKey, String secretKey) throws QiniuException {
        Auth auth = Auth.create(accessKey, secretKey);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(area);
        String upToken = auth.uploadToken(bucket);
        //...其他参数参考类注释
        String key = StringUtils.getUUID();
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(localFilePath, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        log.info("{七牛图片上传key: " + putRet.key + ",七牛图片上传hash: " + putRet.hash + "}");
        return putRet.key;
    }

    /**
     * @param fileName
     * @param qiNiuConfig
     * @description:  根据七牛配置map删除七牛云上文件
     * @author:       alex
     * @return:       int
    */
    public int deleteQiNiuFile(String fileName, Map<String, String> qiNiuConfig) {
        String area = qiNiuConfig.get(SysConf.QI_NIU_AREA);
        //获取上传凭证
        String bucket = qiNiuConfig.get(SysConf.QI_NIU_BUCKET);
        String accessKey = qiNiuConfig.get(SysConf.QI_NIU_ACCESS_KEY);
        String secretKey = qiNiuConfig.get(SysConf.QI_NIU_SECRET_KEY);
        return deleteFile(fileName, area, accessKey, secretKey, bucket);
    }

    /**
     * @param fileNameList
     * @param qiNiuConfig
     * @description:  批量删除文件
     * @author:       alex
     * @return:       java.lang.Boolean
    */
    public Boolean deleteFileList(List<String> fileNameList, Map<String, String> qiNiuConfig) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(qiNiuConfig.get(SysConf.QI_NIU_AREA));
        //获取上传凭证
        String accessKey = qiNiuConfig.get(SysConf.QI_NIU_ACCESS_KEY);
        String secretKey = qiNiuConfig.get(SysConf.QI_NIU_SECRET_KEY);
        String bucket = qiNiuConfig.get(SysConf.QI_NIU_BUCKET);
        int successCount = 0;
        for (String fileName : fileNameList) {
            String key = fileName;
            Auth auth = Auth.create(accessKey, secretKey);
            BucketManager bucketManager = new BucketManager(auth, cfg);
            try {
                bucketManager.delete(bucket, key);
                log.info("{七牛云文件 {} 删除成功", fileName);
                successCount += 1;
            } catch (QiniuException ex) {
                //如果遇到异常，说明删除失败
                log.error(ex.getMessage());
            }
        }
        return successCount == fileNameList.size();
    }

    /**
     * @param fileName
     * @param area
     * @param accessKey
     * @param secretKey
     * @param bucket
     * @description:  删除文件
     * @author:       alex
     * @return:       int
    */
    private int deleteFile(String fileName, String area, String accessKey, String secretKey, String bucket) {
        int res = -1;
        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(area);
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response delete = bucketManager.delete(bucket, fileName);
            log.info("{七牛云文件 {} 删除成功", fileName);
            res =  delete.statusCode;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error(ex.getMessage());
        }
        return res;
    }

    /**
     * @param area
     * @description:  设置七牛云空间
     * @author:       alex
     * @return:       com.qiniu.storage.Configuration
    */
    private Configuration setQiNiuArea(String area) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = null;

        //zong2() 代表华南地区
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

    /**
     * @description:  获取七牛配置
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.String>
    */
    public Map<String, String> getQiNiuConfig() {
        HttpServletRequest request = RequestHolder.getRequest();
        // 后台携带的token
        Object token = request.getAttribute(SysConf.TOKEN);
        // 参数中携带的token
        String paramsToken = request.getParameter(SysConf.TOKEN);
        // 获取平台【web：门户，admin：管理端】
        String platform = request.getParameter(SysConf.PLATFORM);
        Map<String, String> qiNiuResultMap;
        // 判断是否是web端发送过来的请求【后端发送过来的token长度为32】
        if (SysConf.WEB.equals(platform) || paramsToken.length() == Constants.THIRTY_TWO) {
            // 如果是调用web端获取配置的接口
            qiNiuResultMap = feignUtils.getSystemConfigByWebToken(paramsToken);
        } else {
            // 调用admin端获取配置接口
            if (token != null) {
                // 判断是否是后台过来的请求
                qiNiuResultMap = feignUtils.getSystemConfigMap(token.toString());
            } else {
                // 判断是否是通过params参数传递过来的
                qiNiuResultMap = feignUtils.getSystemConfigMap(paramsToken);
            }
        }

        if (qiNiuResultMap == null) {
            log.error(MessageConf.PLEASE_SET_QI_NIU);
            throw new QueryException("00107", MessageConf.PLEASE_SET_QI_NIU);
        }

        String uploadQiNiu = qiNiuResultMap.get(SysConf.UPLOAD_QI_NIU);
        String uploadLocal = qiNiuResultMap.get(SysConf.UPLOAD_LOCAL);
        String localPictureBaseUrl = qiNiuResultMap.get(SysConf.LOCAL_PICTURE_BASE_URL);
        String qiNiuPictureBaseUrl = qiNiuResultMap.get(SysConf.QI_NIU_PICTURE_BASE_URL);
        String qiNiuAccessKey = qiNiuResultMap.get(SysConf.QI_NIU_ACCESS_KEY);
        String qiNiuSecretKey = qiNiuResultMap.get(SysConf.QI_NIU_SECRET_KEY);
        String qiNiuBucket = qiNiuResultMap.get(SysConf.QI_NIU_BUCKET);
        String qiNiuArea = qiNiuResultMap.get(SysConf.QI_NIU_AREA);

        if (EOpenStatus.OPEN.equals(uploadQiNiu) && (StringUtils.isEmpty(qiNiuPictureBaseUrl) || StringUtils.isEmpty(qiNiuAccessKey)
                || StringUtils.isEmpty(qiNiuSecretKey) || StringUtils.isEmpty(qiNiuBucket) || StringUtils.isEmpty(qiNiuArea))) {
            log.error(MessageConf.PLEASE_SET_QI_NIU);
            throw new QueryException("00107", MessageConf.PLEASE_SET_QI_NIU);
        }

        if (EOpenStatus.OPEN.equals(uploadLocal) && StringUtils.isEmpty(localPictureBaseUrl)) {
            log.error(MessageConf.PLEASE_SET_QI_NIU);
            throw new QueryException("00107", MessageConf.PLEASE_SET_LOCAL);
        }
        // 七牛云配置
        Map<String, String> qiNiuConfig = new HashMap<>();
        return qiNiuConfig;
    }
}
