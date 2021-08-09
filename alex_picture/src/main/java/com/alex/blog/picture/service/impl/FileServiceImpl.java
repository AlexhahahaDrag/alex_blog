package com.alex.blog.picture.service.impl;

import com.alex.blog.base.global.Constants;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.entity.file.File;
import com.alex.blog.common.entity.file.FileSort;
import com.alex.blog.common.enums.EFilePriority;
import com.alex.blog.common.enums.EOpenStatus;
import com.alex.blog.common.enums.EStatus;
import com.alex.blog.common.exception.AlexException;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.file.FileVo;
import com.alex.blog.picture.mapper.FileMapper;
import com.alex.blog.picture.service.FileService;
import com.alex.blog.picture.service.FileSortService;
import com.alex.blog.utils.utils.FeignUtils;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.global.SQLConf;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *description:  文件服务实现类
 *author:       alex
 *createDate:   2021/8/7 20:52
 *version:      1.0.0
 */
@Service
public class FileServiceImpl extends SuperServiceImpl<FileMapper, File> implements FileService {

    @Autowired
    private FileService fileService;

    @Autowired
    private FeignUtils feignUtils;

    @Autowired
    private FileSortService fileSortService;

    @Override
    public String cropperPicture(List<MultipartFile> multipartFileList) {
        SystemConfig systemConfig = feignUtils.getSystemConfig();
        String qiNiuPictureBaseUrl = systemConfig.getQiNiuPictureBaseUrl();
        String minioPictureBaseUrl = systemConfig.getMinioPictureBaseUrl();
        String localPictureBaseUrl = systemConfig.getLocalPictureBaseUrl();
        HttpServletRequest request = RequestHolder.getRequest();
        String result = fileService.batchUploadFile(request, multipartFileList, systemConfig);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> picMap = JsonUtils.jsonToMap(result);
        if (SysConf.SUCCESS.equals(picMap.get(SysConf.CODE))) {
            List<Map<String, Object>> picData = (List<Map<String, Object>>) picMap.get(SysConf.DATA);
            list = picData.stream().map(item -> {
                if (EFilePriority.QI_NIU.equals(systemConfig.getPicturePriority())) {
                    item.put(SysConf.URL, qiNiuPictureBaseUrl + item.get(SysConf.QI_NIU_URL));
                } else if (EFilePriority.MINIO.equals(systemConfig.getPicturePriority())) {
                    item.put(SysConf.URL, minioPictureBaseUrl + item.get(SysConf.MINIO_URL));
                } else {
                    item.put(SysConf.URL, localPictureBaseUrl + item.get(SysConf.PIC_URL));
                }
                return item;
            }).collect(Collectors.toList());
        }
        return ResultUtil.result(SysConf.SUCCESS, list);
    }

    /**
     * @param fileIds 文件id
     * @param code    文件id间隔符
     * @description:  根据文件id获取图片
     * @author:       alex
     * @return:       java.lang.String
    */
    @Override
    public String getPicture(String fileIds, String code) {
        if (StringUtils.isEmpty(code)) {
            code = Constants.SYMBOL_COMMA;
        }
        if (StringUtils.isEmpty(fileIds)) {
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.PICTURE_UID_IS_NULL);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> fileIdList = StringUtils.splitStringByCode(fileIds, code);
        QueryWrapper<File> query = new QueryWrapper<>();
        query.in(SysConf.ID, fileIdList);
        List<File> fileList = fileService.list(query);
        if (fileList != null && fileList.size() > 0) {
           fileList.forEach(item -> {
               Map<String, Object> remap = new HashMap<>();
               // 获取七牛云地址
               remap.put(SysConf.QI_NIU_URL, item.getQiNiuUrl());
               // 获取Minio对象存储地址
               remap.put(SysConf.MINIO_URL, item.getMinioUrl());
               // 获取本地地址
               remap.put(SysConf.URL, item.getPicUrl());
               // 后缀名，也就是类型
               remap.put(SysConf.EXPANDED_NAME, item.getPicExpandedName());
               remap.put(SysConf.FILE_OLD_NAME, item.getFileOldName());
               //名称
               remap.put(SysConf.NAME, item.getPicName());
               remap.put(SysConf.ID, item.getId());
               remap.put(SQLConf.FILE_OLD_NAME, item.getFileOldName());
               list.add(remap);
           });
        }
        return ResultUtil.result(SysConf.SUCCESS, list);
    }

    /**
     * @param request
     * @param multipartFileList
     * @param systemConfig
     * @description:  批量上传文件
     * @author:       alex
     * @return:       java.lang.String
    */
    @Override
    public String batchUploadFile(HttpServletRequest request, List<MultipartFile> multipartFileList, SystemConfig systemConfig) {
        if (multipartFileList == null || multipartFileList.size() > 0) {
            return ResultUtil.result(SysConf.ERROR, "请上传图片");
        }

        //获取请求来源
        String source = request.getParameter(SysConf.SOURCE);
        //如果是用户上传，则包含用户id；如果是管理员上传，则包含管理员id
        String userId;
        String adminId;
        String projectName;
        String sortName;
        if (SysConf.PICTURE.equals(source)) {
            userId = request.getParameter(SysConf.USER_ID);
            adminId = request.getParameter(SysConf.ADMIN_ID);
            projectName = request.getParameter(SysConf.PROJECT_NAME);
            sortName = request.getParameter(SysConf.SORT_NAME);
        } else if (SysConf.ADMIN.equals(source)) {
            userId = request.getAttribute(SysConf.USER_ID).toString();
            adminId = request.getAttribute(SysConf.ADMIN_ID).toString();
            projectName = request.getAttribute(SysConf.PROJECT_NAME).toString();
            sortName = request.getAttribute(SysConf.SORT_NAME).toString();
        } else {
            userId = request.getAttribute(SysConf.USER_ID).toString();
            adminId = request.getAttribute(SysConf.ADMIN_ID).toString();
            projectName = request.getAttribute(SysConf.PROJECT_NAME).toString();
            sortName = request.getAttribute(SysConf.SORT_NAME).toString();
        }
        //如果项目名称为空，则默认为base
        if (StringUtils.isEmpty(projectName)) {
            projectName = "base";
        }
        // TODO: 2021/8/9 检测上传用户，如果不是网站的用户则不能调用
        if (checkUser(userId, adminId)) {
            return ResultUtil.result(SysConf.ERROR, "请先注册");
        }
        List<FileSort> fileSortList = getFileSortList(sortName, projectName);
        if (fileSortList == null || fileSortList.size() == 0) {
            return ResultUtil.result(SysConf.ERROR, "文件不允许上传");
        }
        FileSort fileSort = fileSortList.get(0);
        String sortUrl = fileSort.getUrl() == null ? "base/common" : fileSort.getUrl();
        List<File> list = new ArrayList<>();
        //上传文件
        String uploadLocal = systemConfig.getUploadLocal();
        String uploadMinio = systemConfig.getUploadMinio();
        String uploadQiNiu = systemConfig.getUploadQiNiu();
        multipartFileList.forEach(item -> {
            String oldName = item.getOriginalFilename();
            long size = item.getSize();
            // TODO: 2021/8/9 文件工具类
            String picExpandedName = FileUtils.getPicExpandedName(oldName);
            String newFileName = System.currentTimeMillis() + Constants.SYMBOL_POINT + picExpandedName;
            String localUrl = "";
            String qiNiuUrl = "";
            String minioUrl = "";
            try {
                if (EOpenStatus.OPEN.getCode().equals(uploadLocal)) {
                    // TODO: 2021/8/9 添加本地上传文件接口
                    localUrl = localService.uploadFile(item);
                }
                if (EOpenStatus.OPEN.getCode().equals(uploadQiNiu)) {
                    // TODO: 2021/8/9 添加七牛上传文件服务
                    qiNiuUrl = qiNiuService.uploadFile(item);
                }
                if (EOpenStatus.OPEN.getCode().equals(uploadMinio)) {
                    // TODO: 2021/8/9 添加minio上传文件服务
                    minioUrl = minioService.uploadFile(item);
                }
                File file = new File();
                file.setFileSortId(fileSort.getId());
                file.setFileOldName(oldName);
                file.setFileSize(size);
                file.setPicExpandedName(picExpandedName);
                file.setPicName(newFileName);
                file.setPicUrl(localUrl);
                file.setQiNiuUrl(qiNiuUrl);
                file.setMinioUrl(minioUrl);
                file.setUserId(userId);
                file.setAdminId(adminId);
                file.setStatus(EStatus.ENABLE.getCode());
                list.add(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fileService.saveBatch(list);
        return ResultUtil.result(SysConf.SUCCESS, list);
    }

    @Override
    public String uploadPictureByUrl(FileVo fileVo) {
        //获取配置文件
        SystemConfig systemConfig;
        if (fileVo.getSystemConfig() != null) {
            Map<String, String> map = fileVo.getSystemConfig();
            systemConfig = feignUtils.getSystemConfigMap(map);
        } else {
            //从redis中获取七牛云配置文件
            systemConfig = feignUtils.getSystemConfig();
        }
        String userId = fileVo.getUserId();
        String adminId = fileVo.getAdminId();
        String projectName = fileVo.getProjectName();
        String sortName = fileVo.getSortName();
        if (StringUtils.isEmpty(projectName)) {
            projectName = "base";
        }
        if (checkUser(userId, adminId)) {
            throw new AlexException("00200", "请先注册");
        }
        List<FileSort> fileSortList = getFileSortList(sortName, projectName);
        if (fileSortList == null || fileSortList.size() == 0) {
            throw new AlexException("00200", "文件不允许上传，请填写文件分类信息");
        }
        FileSort fileSort = fileSortList.get(0);
        List<String> urlList = fileVo.getUrlList();
        if(urlList == null || urlList.size() > 0) {
            return ResultUtil.result(SysConf.ERROR, "请上传图片");
        }
        return null;
    }

    /**
     * @param sortName
     * @param projectName
     * @description:  根据分类名和项目名称获取分类信息
     * @author:       alex
     * @return:       java.util.List<com.alex.blog.common.entity.file.FileSort>
    */
    private List<FileSort> getFileSortList(String sortName, String projectName) {
        QueryWrapper<FileSort> query = new QueryWrapper<>();
        query.eq(SysConf.SORT_NAME, sortName);
        query.eq(SysConf.PROJECT_NAME, projectName);
        query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
        return fileSortService.list(query);
    }

    // TODO: 2021/8/9 检测用户上传，如果不是网站上的用户或者会员就不能调用
    private boolean checkUser(String userId, String adminid) {
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(adminid)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object ckeditorUploadFile(HttpServletRequest request) {
        return null;
    }

    @Override
    public Object ckeditUploadCopyFile() {
        return null;
    }

    @Override
    public Object ckeditorUplaodToolFile(HttpServletRequest request) {
        return null;
    }
}
