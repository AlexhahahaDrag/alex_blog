package com.alex.blog.admin.annotion.operationLogger;

import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.AbstractRequestAwareRunnable;
import com.alex.blog.common.config.security.SecurityUser;
import com.alex.blog.common.entity.log.SysLog;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.DateUtils;
import com.alex.blog.utils.utils.IpUtils;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 *description:  异步记录日志
 *author:       alex
 *createDate:   2022/1/10 21:24
 *version:      1.0.0
 */
public class SyslogHandle extends AbstractRequestAwareRunnable {

    private RedisUtils redisUtils;

    //参数json
    private String paramsJson;

    //类路径
    private String classPath;

    //方法名称
    private String methodName;

    //开始时间
    private LocalDateTime startTime;

    //操作名称
    private String operationName;

    //ip
    private String ip;

    //请求类型
    private String type;

    //请求url
    private String requestUrl;

    private SecurityUser securityUser;

    public SyslogHandle(String paramsJson, String classPath, String methodName, LocalDateTime startTime,
                        String operationName, String ip, String type, String requestUrl,
                        SecurityUser securityUser, RedisUtils redisUtils) {
        this.paramsJson = paramsJson;
        this.classPath = classPath;
        this.methodName = methodName;
        this.startTime = startTime;
        this.operationName = operationName;
        this.ip = ip;
        this.type = type;
        this.requestUrl = requestUrl;
        this.securityUser = securityUser;
        this.redisUtils = redisUtils;
    }

    @Override
    protected void onRun() {
        SysLog sysLog = new SysLog();
        String jsonResult = redisUtils.get(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isEmpty(jsonResult)) {
            String addresses = IpUtils.getAddresses(SysConf.IP + SysConf.EQUAL_TO + ip, SysConf.UTF_8);
            if (StringUtils.isNotEmpty(addresses)) {
                sysLog.setIpSource(addresses);
                redisUtils.setEx(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip, addresses, 24, TimeUnit.HOURS);
            }
        } else {
            sysLog.setIpSource(jsonResult);
        }
        sysLog.setUrl(requestUrl);
        sysLog.setIp(ip);
        sysLog.setClassPath(classPath);
        sysLog.setMethod(methodName);
        sysLog.setOperation(operationName);
        sysLog.setType(type);
        sysLog.setAdminId(securityUser.getId());
        sysLog.setUsername(securityUser.getUsername());
        sysLog.setParams(paramsJson);
        long spendTime = DateUtils.diffSecondByTwoDays(startTime, LocalDateTime.now());
        sysLog.setSpendTime(spendTime);
        sysLog.insert();
    }
}
