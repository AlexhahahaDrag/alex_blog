package com.alex.blog.picture.restapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

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

    @ApiOperation(value = "截图上传", notes = "截图上传")
    @RequestMapping(value = "/copperPicture", method = RequestMethod.POST)
    public String cropperPicture(@ApiParam(value = "file", required = true) @RequestParam(value = "file")MultipartFile file) {
        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(file);
        return fileService.copperPicture(multipartFileList);
    }
}
