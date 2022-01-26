package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.common.entity.sys.WebVisit;
import com.alex.blog.common.enums.EBehavior;
import com.alex.blog.common.vo.sys.WebVisitVo;
import com.alex.blog.utils.utils.DateUtils;
import com.alex.blog.xo.mapper.sys.WebVisitMapper;
import com.alex.blog.xo.service.sys.WebVisitService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * Web访问记录表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-26 17:27:16
 */
@Service
public class WebVisitServiceImp extends SuperServiceImpl<WebVisitMapper, WebVisit> implements WebVisitService {

    @Autowired
    private WebVisitMapper webVisitMapper;

    @Override
    public void addWebVisit(String adminId, String moduleId, String otherData) {

    }

    @Override
    public Integer getWebVisitCount() {
        String startTime = DateUtils.getNowTimeStrStartTime();
        String endTime = DateUtils.getNowTimeStr();
        //获取今日访问量
        return webVisitMapper.getIpCount(startTime, endTime);
    }

    @Override
    public Map<String, Object> getVisitByWeek() {
        return null;
    }

    @Override
    public IPage<WebVisit> getPageList(WebVisitVo webVisitVo) {
        QueryWrapper<WebVisit> query = new QueryWrapper<>();
        //得到所有枚举类
        EBehavior[] behaviors = EBehavior.values();
        return null;
    }
}
