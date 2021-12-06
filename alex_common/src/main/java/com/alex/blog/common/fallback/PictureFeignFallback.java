package com.alex.blog.common.fallback;

import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.feign.PictureFeignClient;
import com.alex.blog.common.vo.file.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  图片服务降级兜底方法
 *author:       alex
 *createDate:   2021/12/2 14:17
 *version:      1.0.0
 */
@Component
@Slf4j
public class PictureFeignFallback implements PictureFeignClient {

    @Override
    public String getPicture(String fileIds, String code) {
        HttpServletRequest request = RequestHolder.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        log.error("图片服务出现异常，服务降级返回，请求路径: {}", requestURL);
        return "获取图片服务降级返回";
    }

    @Override
    public Object uploadPictureByUrl(FileVo fileVo) {
        HttpServletRequest request = RequestHolder.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        log.error("图片服务出现异常，服务降级返回，请求路径: {}", requestURL);
        return "获取图片服务降级返回";
    }
}
