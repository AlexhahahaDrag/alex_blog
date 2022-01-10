package com.alex.blog.utils.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;

/**
 *description:  aop相关工具类
 *author:       alex
 *createDate:   2022/1/7 6:41
 *version:      1.0.0
 */
public enum AspectUtils {

    //实例
    INSTANCE;

    public String getKey(JoinPoint point, String prefix) {
        return (StringUtils.isNotEmpty(prefix) ? prefix : "") + getClassName(point);
    }

    /**
     * @param point
     * @description:  获取当前切面执行的方法所在的class
     * @author:       alex
     * @return:       java.lang.String
    */
    public String getClassName(JoinPoint point) {
        return point.getTarget().getClass().getName().replace("\\.", "_");
    }

    /**
     * @param point
     * @description:  获取当前切面执行的方法的方法名
     * @author:       alex
     * @return:       java.lang.reflect.Method
    */
    public Method getMethod(JoinPoint point) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object target = point.getTarget();
        return target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }

    /**
     * @param params
     * @param businessName
     * @description:  解析参数
     * @author:       alex
     * @return:       java.lang.String
    */
    public String parseParams(Object[] params, String businessName) {
        if (StringUtils.isEmpty(businessName)) {
            return "";
        }
        if (businessName.contains("{") && businessName.contains("}")) {
            List<String> result = RegexUtils.match(businessName, "(?<=\\{)(\\d+)");
            if (result == null) {
                return null;
            }
           for(String s : result) {
                int index = Integer.parseInt(s);
                businessName = businessName.replaceAll("{" + index + "}", JsonUtils.objectToJson(params[index- 1]));
            }
        }
        return businessName;
    }
}
