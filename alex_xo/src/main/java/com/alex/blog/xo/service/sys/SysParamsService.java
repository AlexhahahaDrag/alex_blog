package com.alex.blog.xo.service.sys;

import com.alex.blog.common.entity.sysParams.SysParams;
import com.alex.blog.base.service.SuperService;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-12-06 16:10:18
 */
public interface SysParamsService extends SuperService<SysParams> {

    String getSysParamsValueByKey(String key);
}
