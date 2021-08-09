package com.alex.blog.picture.service;

import com.alex.blog.common.entity.admin.SystemConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *description:  七牛服务类
 *author:       alex
 *createDate:   2021/8/10 6:19
 *version:      1.0.0
 */
public interface QiNiuService {

    // TODO: 2021/8/10 编写实现类
    /**
     * @param multipartFileList
     * @description:  批量上传文件
     * @author:       alex
     * @return:       java.util.List<java.lang.String>
    */
    List<String> batchUploadFile(List<MultipartFile> multipartFileList) throws IOException;

    /**
     * @param multipartFile
     * @description:  上传文件
     * @author:       alex
     * @return:       java.lang.String
    */
    String uploadFile(MultipartFile multipartFile) throws IOException;

    /**
     * @param url
     * @param systemConfig
     * @description:  通过url上传图片
     * @author:       alex
     * @return:       java.lang.String
    */
    String uploadPictureByUrl(String url, SystemConfig systemConfig);
}
