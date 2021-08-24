package com.alex.blog.picture.restapi;

import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.vo.file.FileVo;
import com.alex.blog.picture.service.FileService;
import com.alex.blog.picture.utils.MinioUtils;
import com.alex.blog.utils.utils.FeignUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *description:  文件上传接口
 *author:       alex
 *createDate:   2021/8/7 15:20
 *version:      1.0.0
 */
@RestController
@RequestMapping("/file")
@Slf4j
@Api(value = "文件服务相关接口", tags = {"文件服务相关接口"})
public class FileRestApi {

    @Autowired
    private FileService fileService;
    
    @Autowired
    private FeignUtils feignUtils;

    @Autowired
    private MinioUtils minioUtils;

    @ApiOperation(value = "截图上传", notes = "截图上传")
    @RequestMapping(value = "/copperPicture", method = RequestMethod.POST)
    public String cropperPicture(@ApiParam(value = "file", required = true) @RequestParam(value = "file")MultipartFile file) {
        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(file);
        return fileService.cropperPicture(multipartFileList);
    }
    
    @ApiOperation(value = "通过fields获取图片信息接口", notes = "获取图片信息接口")
    @GetMapping(value = "/getPicture")
    public String getPicture(@ApiParam(name = "fieldIds", value = "文件ids") @RequestParam(value = "fields", required = false) String fieldIds, 
                             @ApiParam(name = "code", value = "切割符") @RequestParam(value = "code", required = false) String code) {
        return fileService.getPicture(fieldIds, code);
    }
    
    @ApiOperation(value = "多图片上传接口", notes = "多图片上传接口")
    @PostMapping(value = "/pictures")
    public synchronized Object uploadPictures(HttpServletRequest request, 
                                              @RequestBody List<MultipartFile> fileDataList) {
        //获取系统配置文件
        SystemConfig systemConfig = feignUtils.getSystemConfig();
        return fileService.batchUploadFile(request, fileDataList, systemConfig);
    }

    // TODO: 2021/8/16 验证正确性 
    @ApiOperation(value = "通过url上传图片", notes = "通过url上传图片")
    @PostMapping(value = "/uploadPictureByUrl")
    public Object uploadPictureByUrl(@Validated({GetList.class}) @RequestBody FileVo fileVo) {
        return fileService.uploadPictureByUrl(fileVo);
    }

    @ApiOperation(value = "上传复制的文件", notes = "上传复制的文件")
    @RequestMapping(value = "/ckeditorUploadCopyFile", method = RequestMethod.POST)
    public Object ckeditorUploadCopyFile() {
        return fileService.ckeditUploadCopyFile();
    }

    @ApiOperation(value = "上传工具栏的文件", notes = "上传工具栏的文件")
    @PostMapping(value = "/ckeditorUploadToolFile")
    public Object ckeditorUploadToolFile(HttpServletRequest request) {
        return fileService.ckeditorUplaodToolFile(request);
    }

    @RequestMapping(value = "/testMinio", method = RequestMethod.GET)
    public String testMinio() {
        return minioUtils.uploadFile(null);
    }

}
