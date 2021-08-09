package com.alex.blog.picture.service;

import com.alex.blog.common.entity.file.FileSort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *description:  本地文件系统服务类
 *author:       alex
 *createDate:   2021/8/10 6:19
 *version:      1.0.0
 */
public interface LocalService {

    /**
     * 多文件上传
     *
     * @param multipartFileList
     * @param fileSort
     * @return
     * @throws IOException
     */
    List<String> batchUploadFile(List<MultipartFile> multipartFileList, FileSort fileSort) throws IOException;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param fileSort
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartFile multipartFile, FileSort fileSort) throws IOException;

    /**
     * 通过URL上传图片
     *
     * @param url
     * @param fileSort
     * @return
     */
    String uploadPictureByUrl(String url, FileSort fileSort);
}
