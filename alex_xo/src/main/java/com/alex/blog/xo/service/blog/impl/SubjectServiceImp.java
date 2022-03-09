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
import com.alex.blog.common.vo.blog.SubjectVo;
/**
 * <p>
 * @description:  专题表服务实现类
 * @author:       alex
 * @createDate:   2022-03-03 21:32:04
 * @version:      1.0.0
 */
@Service
public class SubjectServiceImp extends SuperServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public IPage<Subject> getPageList(SubjectVo subjectVo) {
        QueryWrapper<Subject> query = getQuery();
        Page<Subject> page = new Page<>();
        page.setCurrent(subjectVo.getCurrentPage());
        page.setSize(subjectVo.getPageSize());
        Page<Subject> subjectPage = this.page(page, query);
        return subjectPage;
    }

    @Override
    public String addSubject(SubjectVo subjectVo) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectVo, subject);
        subject.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editSubject(SubjectVo subjectVo) {
        QueryWrapper<Subject> query = getQuery();
        query.eq(SysConf.ID, subjectVo.getId());
        Subject subject = this.getOne(query);
        if (subject == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(subjectVo, subject);
        subject.updateById();
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
