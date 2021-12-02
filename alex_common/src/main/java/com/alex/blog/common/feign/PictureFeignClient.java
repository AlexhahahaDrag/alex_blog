package com.alex.blog.common.feign;

import com.alex.blog.base.validator.group.GetList;
import com.alex.blog.common.config.feign.FeignConfiguration;
import com.alex.blog.common.fallback.PictureFeignFallback;
import com.alex.blog.common.vo.file.FileVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *description:  picture feign
 *author:       alex
 *createDate:   2021/12/2 14:14
 *version:      1.0.0
 */
@FeignClient(name = "alex-picture", configuration = FeignConfiguration.class, fallback = PictureFeignFallback.class)
public interface PictureFeignClient {

    /**
     * @param fileIds 图片id
     * @param code    分隔符
     * @description:  获取文件信息接口
     * @author:      alex
     * @return:      java.lang.String
    */
    @ApiOperation(value = "通过fields获取图片信息接口", notes = "获取图片信息接口")
    @GetMapping(value = "/file/getPicture")
    String getPicture(@ApiParam(name = "fileIds", value = "文件ids") @RequestParam(value = "fileIds", required = false) String fileIds,
                             @ApiParam(name = "code", value = "切割符") @RequestParam(value = "code", required = false) String code);

    @ApiOperation(value = "通过url上传图片", notes = "通过url上传图片")
    @PostMapping(value = "/file/uploadPictureByUrl")
    Object uploadPictureByUrl(@Validated({GetList.class}) @RequestBody FileVo fileVo);
}
