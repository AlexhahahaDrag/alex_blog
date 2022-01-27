package com.alex.blog.xo.service.sys.impl;


import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.log.ExceptionLog;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.log.ExceptionLogVo;
import com.alex.blog.utils.utils.DateUtils;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.sys.ExceptionLogMapper;
import com.alex.blog.xo.service.sys.ExceptionLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *description:  异常日志服务实现类
 *author:       alex
 *createDate:   2021/10/5 15:14
 *version:      1.0.0
 */
@Service
public class ExceptionLogServiceImpl extends SuperServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Override
    public IPage<ExceptionLog> getPageList(ExceptionLogVo exceptionLogVo) {
        QueryWrapper<ExceptionLog> query = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(exceptionLogVo.getKeyword())) {
//            query.like(SysConf.CONTENT, exceptionLogVo.getKeyword());
//        }
        if (StringUtils.isNotEmpty(exceptionLogVo.getOperation())) {
            query.like(SysConf.OPERATION, exceptionLogVo.getOperation());
        }
        if (StringUtils.isNotEmpty(exceptionLogVo.getStartTime())) {
            String[] times = exceptionLogVo.getStartTime().split(SysConf.FILE_SEGMENTATION);
            if (times.length == Constants.NUM_TWO) {
                query.between(SysConf.CREATE_TIME, DateUtils.parseStringToTime(times[0]), DateUtils.parseStringToTime(times[1]));
            }
        }
        Page<ExceptionLog> page = new Page<>();
        page.setCurrent(exceptionLogVo.getCurrentPage());
        page.setSize(exceptionLogVo.getPageSize());
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        query.orderByDesc(SysConf.CREATE_TIME);
        return exceptionLogService.page(page, query);
    }
}
