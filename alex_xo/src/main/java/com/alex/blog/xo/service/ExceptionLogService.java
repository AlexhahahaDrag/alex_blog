package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.log.ExceptionLog;
import com.alex.blog.common.vo.log.ExceptionLogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *description:  操作异常日志服务类
 *author:       alex
 *createDate:   2021/9/27 20:34
 *version:      1.0.0
 */
public interface ExceptionLogService extends SuperService<ExceptionLog> {

    /**
     * @description:  获取异常日志列表
     * @author:       alex
     * @return:       IPage<ExceptionLog>
    */
    IPage<ExceptionLog> getPageList(ExceptionLogVo exceptionLogVo);
}
