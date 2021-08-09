package com.alex.blog.picture.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *description:  minIo服务类
 *author:       alex
 *createDate:   2021/8/10 6:18
 *version:      1.0.0
 */
public interface MinIoService {

    // TODO: 2021/8/10 编写实现类

    /**
     * 多文件上传
     *
     * @param multipartFileList
     * @return
     * @throws IOException
     */
    List<String> batchUploadFile(List<MultipartFile> multipartFileList) throws IOException;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartFile multipartFile) throws IOException;

    /**
     * 通过URL上传图片
     *
     * @param url
     * @return
     */
    String uploadPictureByUrl(String url);
}
