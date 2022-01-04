package com.alex.blog.admin.annotion.operationLogger;

import com.alex.blog.utils.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
}
