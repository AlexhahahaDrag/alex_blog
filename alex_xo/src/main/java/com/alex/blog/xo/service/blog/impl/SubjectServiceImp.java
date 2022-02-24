package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.entity.blog.Subject;
import com.alex.blog.xo.mapper.blog.SubjectMapper;
import com.alex.blog.xo.service.blog.SubjectService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import org.springframework.beans.BeanUtils;
import java.util.List;
import com.alex.blog.base.enums.EStatus;

/**
 * <p>
 * @description:  专题表服务实现类
 * @author:       alex
 * @createDate:   2022-02-24 15:46:25
 * @version:      1.0.0
 */
@Service
public class SubjectServiceImp extends SuperServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public IPage<Subject> getPageList(FeedbackVo feedbackVo) {
        QueryWrapper<Subject> query = getQuery();
        Page<Subject> page = new Page<>();
        page.setCurrent(feedbackVo.getCurrentPage());
        page.setSize(feedbackVo.getPageSize());
        Page<Subject> feedbackPage = this.page(page, query);
        return feedbackPage;
    }

    @Override
    public String addSubject(FeedbackVo feedbackVo) {
        Subject feedback = new Subject();
        BeanUtils.copyProperties(feedbackVo, feedback);
        feedback.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editSubject(FeedbackVo feedbackVo) {
        QueryWrapper<Subject> query = getQuery();
        query.eq(SysConf.ID, feedbackVo.getId());
        Subject feedback = this.getOne(query);
        if (feedback == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(feedbackVo, feedback);
        feedback.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchSubject(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<Subject> getQuery() {
        QueryWrapper<Subject> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
