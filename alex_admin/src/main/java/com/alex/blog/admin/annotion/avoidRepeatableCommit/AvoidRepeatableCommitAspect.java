package com.alex.blog.admin.annotion.avoidRepeatableCommit;

import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.IpUtils;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 *description:  避免接口重复提交aop
 *author:       alex
 *createDate:   2021/10/10 13:32
 *version:      1.0.0
 */
@Aspect
@Component
@Slf4j
public class AvoidRepeatableCommitAspect {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param point
     * @description:  过滤AvoidRepeatableCommit，避免表单重复提交
     * @author:       alex
     * @return:       java.lang.Object
    */
    @Around("@annotation(com.alex.blog.admin.annotion.avoidRepeatableCommit.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = IpUtils.getIpAddr(request);
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //目标类方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();

        //得到类名的方法
        String ipKey = String.format("%s#%s", className, name);

        //转换成hashCode
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s:%s_%d", RedisConf.AVOID_REPEATABLE_COMMIT, ip, hashCode);

        log.info("ipKey={}, hashCode={},key={}", ipKey, hashCode, key);

        //判断是否redis中存在，如果存在
        String value = redisUtils.get(key);
        if (StringUtils.isNotBlank(value)) {
            log.info("请勿重复提交表单！");
            return ResultUtil.result(SysConf.SUCCESS, "请勿重复提交表单!");
        }
        //设置表单提交时间
        AvoidRepeatableCommit avoidRepeatableCommit = method.getAnnotation(AvoidRepeatableCommit.class);
        long timeout = avoidRepeatableCommit.timeout();
        redisUtils.setEx(key, StringUtils.getUUID(), timeout, TimeUnit.MILLISECONDS);

        //执行方法
        return point.proceed();
    }
}
