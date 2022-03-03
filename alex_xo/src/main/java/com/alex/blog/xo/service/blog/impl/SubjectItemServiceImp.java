package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.entity.blog.SubjectItem;
import com.alex.blog.xo.mapper.blog.SubjectItemMapper;
import com.alex.blog.xo.service.blog.SubjectItemService;
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
import com.alex.blog.common.vo.blog.SubjectItem.SubjectItemVo;
/**
 * <p>
 * @description:  专题Item表服务实现类
 * @author:       alex
 * @createDate:   2022-03-03 21:32:16
 * @version:      1.0.0
 */
@Service
public class SubjectItemServiceImp extends SuperServiceImpl<SubjectItemMapper, SubjectItem> implements SubjectItemService {

    @Override
    public IPage<SubjectItem> getPageList(SubjectItemVo subjectItemVo) {
        QueryWrapper<SubjectItem> query = getQuery();
        Page<SubjectItem> page = new Page<>();
        page.setCurrent(subjectItemVo.getCurrentPage());
        page.setSize(subjectItemVo.getPageSize());
        Page<SubjectItem> subjectItemPage = this.page(page, query);
        return subjectItemPage;
    }

    @Override
    public String addSubjectItem(SubjectItemVo subjectItemVo) {
        SubjectItem subjectItem = new SubjectItem();
        BeanUtils.copyProperties(subjectItemVo, subjectItem);
        subjectItem.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editSubjectItem(SubjectItemVo subjectItemVo) {
        QueryWrapper<SubjectItem> query = getQuery();
        query.eq(SysConf.ID, subjectItemVo.getId());
        SubjectItem subjectItem = this.getOne(query);
        if (subjectItem == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(subjectItemVo, subjectItem);
        subjectItem.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchSubjectItem(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<SubjectItem> getQuery() {
        QueryWrapper<SubjectItem> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
