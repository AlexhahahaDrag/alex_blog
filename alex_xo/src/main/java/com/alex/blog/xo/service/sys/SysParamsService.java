package com.alex.blog.xo.service.sys;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.sys.SysParams;
import com.alex.blog.common.vo.sys.SysParamsVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-12-06 16:10:18
 */
public interface SysParamsService extends SuperService<SysParams> {

    /**
     * @param sysParamsVo
     * @description:  获取参数列表
     * @author:       alex
     * @return:       com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.sysParams.SysParams>
    */
    IPage<SysParams> getPageList(SysParamsVo sysParamsVo);

    /**
     * @param key
     * @description:  通过键名获取参数配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.sysParams.SysParams
    */
    SysParams getSysParamsByKey(String key);
    /**
     * @param key
     * @description:  通过参数键名获取参数值
     * @author:       alex
     * @return:       java.lang.String
    */
    String getSysParamsValueByKey(String key);

    /**
     * @param sysParamsVo
     * @description:  新增参数配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String addSysParams(SysParamsVo sysParamsVo);

    /**
     * @param sysParamsVo
     * @description:  编辑参数配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String editSysParams(SysParamsVo sysParamsVo);

    /**
     * @param sysParamsList
     * @description:  批量删除参数配置
     * @author:       alex
     * @return:       java.lang.String
    */
    String deleteBatchSysParams(List<String> sysParamsList);
}
