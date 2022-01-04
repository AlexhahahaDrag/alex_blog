package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.base.exception.exceptionType.AlexException;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.enums.EFilePriority;
import com.alex.blog.common.enums.EOpenStatus;
import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.utils.utils.JsonUtils;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.xo.mapper.SystemConfigMapper;
import com.alex.blog.xo.service.sys.SystemConfigService;
import com.alex.blog.common.vo.admin.SystemConfigVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *description:  系统配置服务实现类
 *author:       alex
 *createDate:   2021/8/2 20:31
 *version:      1.0.0
 */
@Service
public class SystemConfigServiceImpl extends SuperServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * @description:  获取系统配置
     * @author:       alex
     * @return:       com.alex.blog.common.entity.admin.SystemConfig
     */
    @Override
    public SystemConfig getConfig() {
        String systemConfigJson = redisUtils.get(RedisConf.SYSTEM_CONFIG);
        SystemConfig systemConfig;
        if (StringUtils.isEmpty(systemConfigJson)) {
            QueryWrapper<SystemConfig> query = new QueryWrapper<>();
            query.orderByDesc(SQLConf.UPDATE_TIME);
            query.eq(SQLConf.STATUS, EStatus.ENABLE.getCode());
            query.last(SysConf.LIMIT_ONE);
            systemConfig = systemConfigService.getOne(query);
            if (systemConfig == null) {
                throw new AlexException(MessageConf.SYSTEM_CONFIG_NOT_EXIST);
            }
            redisUtils.setEx(RedisConf.SYSTEM_CONFIG, JsonUtils.objectToJson(systemConfig), 24, TimeUnit.HOURS);;
        } else {
            systemConfig = JsonUtils.jsonToPojo(systemConfigJson, SystemConfig.class);
            if (systemConfig == null) {
                throw new AlexException("00100", "系统配置转换错误，请检查系统配置，或者清空Redis后重试！");
            }
        }
        return systemConfig;
    }


    @Override
    public String editSystemConfig(SystemConfigVo systemConfigVo) {
        //图片必须选择一个上传区域
        if (EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadLocal())
                && EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadMinio())
                && EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadQiNiu())) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.PICTURE_MUST_BE_SELECT_AREA);
        }
        //图片显示的优先级为本地优先，必须开启图片上传本地
        if((EFilePriority.LOCAL.getCode().equals(systemConfigVo.getPicturePriority()) || EFilePriority.LOCAL.getCode().equals(systemConfigVo.getPicturePriority())) &&
                EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadLocal())){
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_OPEN_LOCAL_UPLOAD);
        }
        //图片显示的优先级为minIO优先，必须开启图片上传minIo
        if((EFilePriority.MINIO.getCode().equals(systemConfigVo.getPicturePriority()) || EFilePriority.MINIO.getCode().equals(systemConfigVo.getPicturePriority())) &&
                EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadMinio())){
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_OPEN_MINIO_UPLOAD);
        }
        //图片显示的优先级为七牛优先，必须开启图片上传七牛
        if((EFilePriority.QI_NIU.getCode().equals(systemConfigVo.getPicturePriority()) || EFilePriority.QI_NIU.getCode().equals(systemConfigVo.getPicturePriority())) &&
                EOpenStatus.CLOSE.getCode().equals(systemConfigVo.getUploadQiNiu())){
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_OPEN_QI_NIU_UPLOAD);
        }
        //开启email邮件通知时，必须保证email字段不为空
        if (EOpenStatus.OPEN.getCode().equals(systemConfigVo.getStartEmailNotification()) && StringUtils.isEmpty(systemConfigVo.getEmail())) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.MUST_BE_SET_EMAIL);
        }
        if (StringUtils.isEmpty(systemConfigVo.getId())) {
            SystemConfig systemConfig = new SystemConfig();
            BeanUtils.copyProperties(systemConfigVo, systemConfig);;
            systemConfig.insert();
        } else {
            SystemConfig systemConfig = systemConfigService.getById(systemConfigVo.getId());
            // TODO: 2021/8/5  判断是否更新了图片显示优先级【如果更新了，需要请求Redis中的博客，否者将导致图片无法正常显示】
            BeanUtils.copyProperties(systemConfigVo, systemConfig);
            systemConfig.updateById();
        }
        //更新配置成功后，删除redis中的系统配置
        redisUtils.delete(RedisConf.SYSTEM_CONFIG);
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }
}
