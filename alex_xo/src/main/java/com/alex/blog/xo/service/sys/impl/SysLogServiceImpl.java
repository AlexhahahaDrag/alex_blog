package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.log.SysLog;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.log.SysLogVo;
import com.alex.blog.utils.utils.DateUtils;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.sys.SysLogMapper;
import com.alex.blog.xo.service.sys.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *description:  系统日志服务实现类
 *author:       alex
 *createDate:   2021/9/26 20:25
 *version:      1.0.0
 */
@Service
public class SysLogServiceImpl extends SuperServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogService sysLogService;

    /**
     *description:  查询日志列表
     *author:       alex
     *createDate:   2021/9/26 21:25
     *version:      1.0.0
     */
    @Override
    public IPage<SysLog> getPageList(SysLogVo sysLogVo) {
        QueryWrapper<SysLog> query = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysLogVo.getUsername())) {
            query.eq(SysConf.USERNAME, sysLogVo.getUsername());
        }
        if (StringUtils.isNotBlank(sysLogVo.getOperation())) {
            query.like(SysConf.OPERATION, sysLogVo.getOperation());
        }
        if (StringUtils.isNotBlank(sysLogVo.getIp())) {
            query.eq(SysConf.IP, sysLogVo.getIp());
        }
        if (StringUtils.isNotBlank(sysLogVo.getStartTime())) {
            if (sysLogVo.getStartTime() != null) {
                String[] times = sysLogVo.getStartTime().split(SysConf.FILE_SEGMENTATION);
                if (times.length == Constants.NUM_TWO) {
                    query.between(SysConf.CREATE_TIME, DateUtils.parseStringToTime(times[0]), DateUtils.parseStringToTime(times[1]));
                }
            }
        }
        if (StringUtils.isNotBlank(sysLogVo.getSpendTimeStr())) {
            if (sysLogVo.getSpendTimeStr() != null) {
                String[] spendTimeList = sysLogVo.getSpendTimeStr().split(Constants.SYMBOL_UNDERLINE);
                if (spendTimeList.length == Constants.NUM_TWO) {
                    query.between(SysConf.SPEND_TIME, Integer.valueOf(spendTimeList[0]), Integer.valueOf(spendTimeList[1]));
                }
            }

        }
        Page<SysLog> page = new Page<>();
        page.setCurrent(sysLogVo.getCurrentPage());
        page.setSize(sysLogVo.getPageSize());
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        query.orderByDesc(SysConf.UPDATE_TIME);
        return sysLogService.page(page, query);
    }
}
