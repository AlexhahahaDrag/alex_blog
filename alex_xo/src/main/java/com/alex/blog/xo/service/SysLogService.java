package com.alex.blog.xo.service;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.log.SysLog;
import com.alex.blog.common.vo.log.SysLogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *description:  操作日志服务类
 *author:       alex
 *createDate:   2021/9/26 19:57
 *version:      1.0.0
 */
public interface SysLogService extends SuperService<SysLog> {

    /**
     * @param sysLogVo
     * @description:  获取操作日志列表
     * @author:       alex
     * @return:       IPag<SysLog>
    */
    IPage<SysLog> getPageList(SysLogVo sysLogVo);
}
