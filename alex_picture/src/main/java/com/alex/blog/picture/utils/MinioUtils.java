package com.alex.blog.picture.utils;

import com.alex.blog.utils.utils.FeignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *description:  minio上传工具类
 *author:       alex
 *createDate:   2021/8/13 22:08
 *version:      1.0.0
 */
@Component
@Slf4j
public class MinioUtils {

    private FeignUtils feignUtils;

    /**
     * @param data
     * @description:  上传文件
     * @author:       alex
     * @return:       java.lang.String
    */
    public String uploadFile(MultipartFile data) {
        return uploadSingleFile(data);
    }

    /**
     * @param list
     * @description:  批量上传文件
     * @author:       alex
     * @return:       java.lang.String
    */
    public String batchUploadFile(List<MultipartFile> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        list.forEach(item -> {
            sb.append(uploadSingleFile(item)).append(",");
        });
        return sb.replace(sb.length() - 1, sb.length(), "").toString();
    }

    private String uploadSingleFile(MultipartFile multipartFile) {

    }
}
