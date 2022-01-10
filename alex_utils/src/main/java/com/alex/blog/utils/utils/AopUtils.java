package com.alex.blog.utils.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 *description:  aop工具类
 *author:       alex
 *createDate:   2022/1/8 17:14
 *version:      1.0.0
 */
@Slf4j
public class AopUtils {

    public static StringBuffer getNameAndArgs(Class<?> clazz, String clazzName, String methodName, Object[] args) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> nameAndArgs = getNameAndArgsMap(clazz, clazzName, methodName, args);
        boolean flag = false;
        if (nameAndArgs.size() > 0) {
            for (Map.Entry entry : nameAndArgs.entrySet()) {
                if (entry.getValue() instanceof  String) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            //从map中获取
            sb.append(JsonUtils.objectToJson(nameAndArgs));
        } else {
            if (args != null) {
                for(Object o : args) {
                    if (o != null) {
                        if (o instanceof MultipartFile || o instanceof ServletRequest || o instanceof ServletResponse) {
                            continue;
                        }
                        sb.append(JsonUtils.objectToJson(o));
                    }
                }
            }
        }

        return sb;
    }

    /**
     * @param clazz
     * @param clazzName
     * @param methodName
     * @param args
     * @description:  通过aop方法获取参数名称和值
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
    */
    public static Map<String, Object> getNameAndArgsMap(Class<?> clazz, String clazzName, String methodName, Object[] args) {
        HashMap<String, Object> nameAndArgs = new HashMap<>();
        try {

            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(clazz);
            pool.insertClassPath(classPath);
            CtClass ctClass = pool.getCtClass(clazzName);
            CtMethod declaredMethod = ctClass.getDeclaredMethod(methodName);
            MethodInfo methodInfo = declaredMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attribute == null) {
                //exception
            }
            int pos = Modifier.isStatic(declaredMethod.getModifiers()) ? 0 : 1;
            for (int i = 0; i < declaredMethod.getParameterTypes().length; i++) {
                nameAndArgs.put(attribute.variableName(i + pos), args[i]);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return nameAndArgs;
    }

    /**
     * @param point
     * @description:  获取参数名称和值
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
    */
    public static Map<String, Object> getFieldName(ProceedingJoinPoint point) throws ClassNotFoundException {
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], args[i]);
        }
        return map;
    }
}
