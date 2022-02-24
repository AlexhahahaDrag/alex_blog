package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.utils.UserUtil;
import com.alex.blog.common.vo.blog.FeedbackVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.blog.entity.Feedback;
import com.alex.blog.xo.mapper.blog.FeedbackMapper;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.blog.FeedbackService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 反馈表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-02-14 16:38:58
 */
@Service
public class FeedbackServiceImp extends SuperServiceImpl<FeedbackMapper, com.alex.blog.xo.blog.entity.Feedback> implements FeedbackService {

    @Autowired
    private AdminService adminService;

    @Override
    public IPage<Feedback> getPageList(FeedbackVo feedbackVo) {
        QueryWrapper<Feedback> query = getQuery();
        if (StringUtils.isNotEmpty(feedbackVo.getTitle())) {
            query.like(SysConf.TITLE, feedbackVo.getTitle());
        }
        if (feedbackVo.getFeedbackStatus() != null) {
            query.eq(SysConf.FEEDBACK_STATUS, feedbackVo.getFeedbackStatus());
        }
        query.orderByDesc(SysConf.OPERATE_TIME);
        Page<Feedback> page = new Page<>();
        page.setCurrent(feedbackVo.getCurrentPage());
        page.setSize(feedbackVo.getPageSize());
        Page<Feedback> feedbackPage = this.page(page, query);
        List<Feedback> records = feedbackPage.getRecords();
        List<String> userIdList = records.stream().map(Feedback::getUserId).filter(item -> StringUtils.isNotEmpty(item)).collect(Collectors.toList());
        List<Admin> userList = adminService.getUserListByIds(userIdList);
        Map<String, Admin> adminMap = userList.stream().map(item -> {
            item.setPassword(null);
            return item;
        }).collect(Collectors.toMap(Admin::getId, item -> item));
        //添加用户信息
        records.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getUserId())) {
                item.setUser(adminMap.get(item.getUserId()));
            }
        });
        feedbackPage.setRecords(records);
        return feedbackPage;
    }

    @Override
    public String addFeedback(FeedbackVo feedbackVo) {
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackVo, feedback);
        feedback.setAdminId(UserUtil.getLoginUser().getId());
        feedback.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editFeedback(FeedbackVo feedbackVo) {
        QueryWrapper<Feedback> query = getQuery();
        query.eq(SysConf.ID, feedbackVo.getId());
        Feedback feedback = this.getOne(query);
        if (feedback == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(feedbackVo, feedback);
        feedback.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchFeedback(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<Feedback> getQuery() {
        QueryWrapper<Feedback> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
