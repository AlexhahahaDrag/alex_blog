package com.alex.blog.admin.annotion.operationLogger;

import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.config.security.SecurityUser;
import com.alex.blog.common.utils.UserUtil;
import com.alex.blog.utils.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;

/**
 *description:  日志切面
 *author:       alex
 *createDate:   2022/1/5 7:17
 *version:      1.0.0
 */
@Aspect
@Component
@Slf4j
public class OperationLoggerAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private LocalDateTime startTime;

    @Pointcut(value = "@annotation(operationLogger)")
    public void pointcut(OperationLogger operationLogger) {

    }

    @Around(value = "pointcut(operationLogger)")
    public Object doAround(ProceedingJoinPoint joinPoint, OperationLogger operationLogger) throws Throwable {
        startTime = LocalDateTime.now();
        //先执行业务
        Object result = joinPoint.proceed();
        try {
            handle(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param point
     * @description:  日志收集
     * @author:       alex
     * @return:       void
     */
    private void handle(ProceedingJoinPoint point) throws Exception {
        HttpServletRequest request = RequestHolder.getRequest();
        Method method = AspectUtils.INSTANCE.getMethod(point);
        OperationLogger annotation = method.getAnnotation(OperationLogger.class);
        boolean save = annotation.save();
        String businessName = AspectUtils.INSTANCE.parseParams(point.getArgs(), annotation.value());
        String ip = IpUtils.getIpAddr(request);
        String requestMethod = RequestUtils.getMethod();
        String requestUrl = RequestUtils.getRequestUrl();
        String userAgent = RequestUtils.getUserAgent();
        log.info("{} | {} - {} {} - {}", businessName, ip, requestMethod, requestUrl, userAgent);
        if (!save) {
            return;
        }
        Map<String, Object> nameAndArgsMap = AopUtils.getFieldName(point);
        String paramsJson = JsonUtils.objectToJson(nameAndArgsMap);
        String type = null;
        String uri = null;
        if (request != null) {
            type = request.getMethod();
            uri = request.getRequestURI();
        }

        SecurityUser loginUser = UserUtil.getLoginUser();
        //异步存储日志
        threadPoolTaskExecutor.execute(new SyslogHandle(paramsJson, point.getTarget().getClass().getName(),
                method.getName(), startTime, businessName, ip, type, uri, loginUser));
    }
}
