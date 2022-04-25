package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.entity.blog.WebNavbar;
import com.alex.blog.xo.mapper.blog.WebNavbarMapper;
import com.alex.blog.xo.service.blog.WebNavbarService;
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
import com.alex.blog.common.vo.blog.WebNavbarVo;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-03-10 14:15:42
 * @version:      1.0.0
 */
@Service
public class WebNavbarServiceImp extends SuperServiceImpl<WebNavbarMapper, WebNavbar> implements WebNavbarService {

    @Override
    public IPage<WebNavbar> getPageList(WebNavbarVo webNavbarVo) {
        QueryWrapper<WebNavbar> query = getQuery();
        Page<WebNavbar> page = new Page<>();
        page.setCurrent(webNavbarVo.getCurrentPage());
        page.setSize(webNavbarVo.getPageSize());
        Page<WebNavbar> webNavbarPage = this.page(page, query);
        return webNavbarPage;
    }

    @Override
    public String addWebNavbar(WebNavbarVo webNavbarVo) {
        WebNavbar webNavbar = new WebNavbar();
        BeanUtils.copyProperties(webNavbarVo, webNavbar);
        webNavbar.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editWebNavbar(WebNavbarVo webNavbarVo) {
        QueryWrapper<WebNavbar> query = getQuery();
        query.eq(SysConf.ID, webNavbarVo.getId());
        WebNavbar webNavbar = this.getOne(query);
        if (webNavbar == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(webNavbarVo, webNavbar);
        webNavbar.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchWebNavbar(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<WebNavbar> getQuery() {
        QueryWrapper<WebNavbar> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
