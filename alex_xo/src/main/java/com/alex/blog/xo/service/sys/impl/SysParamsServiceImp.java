package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.common.entity.sysParams.SysParams;
import com.alex.blog.xo.mapper.sysParams.SysParamsMapper;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.xo.service.sys.SysParamsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2021-12-06 16:10:18
 */
@Service
public class SysParamsServiceImp extends SuperServiceImpl<SysParamsMapper, SysParams> implements SysParamsService {

    @Override
    public String getSysParamsValueByKey(String key) {
        return null;
    }
}
