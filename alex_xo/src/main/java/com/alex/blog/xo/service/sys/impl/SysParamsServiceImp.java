package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.exception.exceptionType.AlexException;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.sysParams.SysParams;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.sysParams.SysParamsVo;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.sys.SysParamsMapper;
import com.alex.blog.xo.service.sys.SysParamsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private SysParamsService sysParamsService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SysParamsMapper sysParamsMapper;

    @Override
    public IPage<SysParams> getPageList(SysParamsVo sysParamsVo) {
        QueryWrapper<SysParams> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysParamsVo.getParamsName())) {
            query.like(SQLConf.PARAMS_NAME, sysParamsVo.getParamsName());
        }
        if (StringUtils.isNotEmpty(sysParamsVo.getParamsKey())) {
            query.like(SQLConf.PARAMS_KEY, sysParamsVo.getParamsKey());
        }
        Page<SysParams> page = new Page<>();
        page.setCurrent(sysParamsVo.getCurrentPage());
        page.setSize(sysParamsVo.getPageSize());
        query.orderByDesc(SQLConf.SORT, SysConf.OPERATE_TIME);
        return sysParamsService.page(page, query);
    }

    @Override
    public SysParams getSysParamsByKey(String key) {
        QueryWrapper<SysParams> query = new QueryWrapper<>();
        query.eq(SQLConf.PARAMS_KEY, key).eq(SysConf.STATUS, EStatus.ENABLE.getCode()).last(SysConf.LIMIT_ONE);
        return sysParamsService.getOne(query);
    }

    @Override
    public String getSysParamsValueByKey(String key) {
        String redisKey = RedisConf.SYSTEM_PARAMS + RedisConf.SEGMENTATION + key;
        String paramsValue = redisUtils.get(redisKey);
        //如果redis中不存在，则从数据库中获取数据
        if (StringUtils.isEmpty(paramsValue)) {
            SysParams sysParams = sysParamsService.getSysParamsByKey(key);
            if (sysParams == null || StringUtils.isEmpty(sysParams.getParamsValue())) {
                throw new AlexException("00106", MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
            }
            paramsValue = sysParams.getParamsValue();
            redisUtils.set(redisKey, paramsValue);
        }
        return paramsValue;
    }

    @Override
    public String addSysParams(SysParamsVo sysParamsVo) {
        QueryWrapper<SysParams> query = new QueryWrapper<>();
        query.eq(SQLConf.PARAMS_KEY, sysParamsVo.getParamsKey()).eq(SQLConf.STATUS, EStatus.ENABLE.getCode())
        .last(SysConf.LIMIT_ONE);
        SysParams oldInfo = sysParamsService.getOne(query);
        if (oldInfo != null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.ENTITY_EXIST);
        }
        SysParams sysParams = new SysParams();
        BeanUtils.copyProperties(sysParamsVo, sysParams);
        sysParams.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editSysParams(SysParamsVo sysParamsVo) {
        SysParams sysParams = sysParamsService.getById(sysParamsVo.getId());
        if (sysParams == null) {
            throw new AlexException("参数配置不存在!");
        }
        //判断新修改的参数键名是否存在
        if(!sysParams.getParamsKey().equals(sysParamsVo.getParamsKey())) {
            SysParams oldInfo = sysParamsService.getSysParamsByKey(sysParamsVo.getParamsKey());
            if (oldInfo != null) {
                return ResultUtil.resultErrorWithMessage(MessageConf.ENTITY_EXIST);
            }
        }
        //清除原来的params_key
        redisUtils.delete(RedisConf.SYSTEM_PARAMS + RedisConf.SEGMENTATION + sysParams.getParamsKey());
        BeanUtils.copyProperties(sysParamsVo, sysParams);
        sysParams.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchSysParams(List<String> sysParamsIdList) {
        if (sysParamsIdList == null || sysParamsIdList.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        List<SysParams> sysParamsList = sysParamsService.listByIds(sysParamsIdList);
        for (SysParams item : sysParamsList) {//判断是否是系统内置参数
            if ("1".equals(item.getParamsType())) {
                return ResultUtil.resultErrorWithMessage("系统内置参数不能删除");
            }
            redisUtils.delete(RedisConf.SYSTEM_PARAMS + RedisConf.SEGMENTATION + item.getParamsKey());
        }
        sysParamsMapper.deleteBatchIds(sysParamsIdList);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }
}
