package com.alex.blog.utils.utils;

import com.alex.blog.base.holder.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 *description:  aop相关工具类
 *author:       alex
 *createDate:   2022/1/11 6:44
 *version:      1.0.0
 */
public class RequestUtils {

    /**
     * @description:  获取请求参数信息
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getParameters() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            sb.append("&").append(s).append("#").append(request.getParameter(s));
        }
        return sb.toString();
    }

    /**
     * @description:  获取请求参数map
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.String[]>
    */
    public static Map<String, String[]> getParamterMap() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        return request.getParameterMap();
    }

    /**
     * @param headerName
     * @description:  获取请求头信息
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getHeader(String headerName) {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader(headerName);
    }

    /**
     * @description:  获取referer
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getRerer() {
        return getHeader("Referer");
    }

    /**
     * @description:  获取用户代理
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getUserAgent() {
        return getHeader("User-Agent");
    }

    /**
     * @description:  获取ip
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getIp() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        return IpUtils.getRealIp(request);
    }

    /**
     * @description:  获取请求url
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getRequestUrl() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        return request.getRequestURL().toString();
    }

    /**
     * @description:  获取方法
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getMethod() {
        HttpServletRequest request = RequestHolder.getRequest();
        if (request == null) {
            return null;
        }
        return request.getMethod();
    }

    /**
     * @param request
     * @description:  判断是否是ajax
     * @author:       alex
     * @return:       boolean
    */
    public static boolean isAjax(HttpServletRequest request) {
        if (request == null) {
            request = RequestHolder.getRequest();
        }
        if (request == null) {
            return false;
        }
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))
                || request.getParameter("ajax") != null;
    }
}
