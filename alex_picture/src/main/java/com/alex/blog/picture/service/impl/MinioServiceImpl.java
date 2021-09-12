package com.alex.blog.picture.service.impl;

import com.alex.blog.base.exception.exceptionType.AlexException;
import com.alex.blog.picture.service.MinIoService;
import com.alex.blog.picture.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *description:  minio服务实现类
 *author:       alex
 *createDate:   2021/8/13 22:01
 *version:      1.0.0
 */
@Service
@Slf4j
public class MinioServiceImpl  implements MinIoService {

    @Autowired
    private MinioUtils minioUtils;

    @Override
    public List<String> batchUploadFile(List<MultipartFile> multipartFileList) throws IOException {
        if (multipartFileList == null || multipartFileList.size() == 0) {
            log.error("文件不能为空！");
            throw new AlexException("500", "请上传文件");
        }
        return multipartFileList.stream().map(item -> {
            String url = null;
            try {
                url = uploadFile(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }).collect(Collectors.toList());
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return null;
    }

    @Override
    public String uploadPictureByUrl(String url) {
        return null;
    }
}
