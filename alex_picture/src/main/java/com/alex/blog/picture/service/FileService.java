package com.alex.blog.picture.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.entity.file.File;
import com.alex.blog.common.vo.file.FileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *description:  文件服务类
 *author:       alex
 *createDate:   2021/8/7 20:25
 *version:      1.0.0
 */
public interface FileService extends SuperService<File> {

    /**
     * @param multipartFileList
     * @description:  截图上传
     * @author:       alex
     * @return:       java.lang.String
     */
    String cropperPicture(List<MultipartFile> multipartFileList);

    /**
     * @param fileIds  文件id
     * @param code
     * @description:   通过fileIds获取图片信息
     * @author:       alex
     * @return:       java.lang.String
     */
    String getPicture(String fileIds, String code);

    /**
     * @param request                请求
     * @param multipartFileList     文件列表
     * @param systemConfig          系统配置
     * @description:                批量上传文件
     * @author:                     alex
     * @return:                     java.lang.String
    */
    String batchUploadFile(HttpServletRequest request, List<MultipartFile> multipartFileList, SystemConfig systemConfig);

    /**
     * @param fileVo
     * @description:  通过url上传图片
     * @author:       alex
     * @return:       java.lang.String
    */
    String uploadPictureByUrl(FileVo fileVo);

    /**
     * @param request
     * @description:  ckeditor图像中的图片上传
     * @author:       alex
     * @return:       java.lang.String
    */
    Object ckeditorUploadFile(HttpServletRequest request);

    /**
     * @description:  ckedit上传复制的图片
     * @author:       alex
     * @return:       java.lang.Object
    */
    Object ckeditUploadCopyFile();

    /**
     * @param request
     * @description:  工具栏 “插入\编辑超链接”的文件上传
     * @author:       alex
     * @return:       java.lang.Object
    */
    Object ckeditorUplaodToolFile(HttpServletRequest request);
}
