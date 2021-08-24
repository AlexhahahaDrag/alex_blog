package com.alex.blog.common.config.feign;

import com.alex.blog.base.global.Constants;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.global.SysConf;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *description:  feign请求拦截器
 *author:       alex
 *createDate:   2021/8/21 23:19
 *version:      1.0.0
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取request请求
        HttpServletRequest request = RequestHolder.getRequest();
        //获取token，放入到feign的请求头中
        String token = null;
        if (request != null) {
            if (request.getParameter(SysConf.TOKEN) != null) {
                token = request.getParameter(SysConf.TOKEN);
            } else if (request.getAttribute(SysConf.TOKEN) != null) {
                token = request.getAttribute(SysConf.TOKEN).toString();
            }
        }
        if (StringUtils.isNotEmpty(token)) {
            if (token.contains(Constants.SYMBOL_QUESTION)) {
                String[] params = token.split("\\?url=");
                token = params[0];
            }
        }
        requestTemplate.header(SysConf.PICTURE_TOKEN, token);
    }
}
