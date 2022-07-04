package com.alex.blog.xo.service..impl;

import com.alex.blog.common.entity..Goods;
import com.alex.blog.xo.mapper..GoodsMapper;
import com.alex.blog.xo.service..GoodsService;
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
import com.alex.blog.common.vo..GoodsVo;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-07-04 18:03:44
 * @version:      1.0.0
 */
@Service
public class GoodsServiceImp extends SuperServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public IPage<Goods> getPageList(GoodsVo goodsVo) {
        QueryWrapper<Goods> query = getQuery();
        Page<Goods> page = new Page<>();
        page.setCurrent(goodsVo.getCurrentPage());
        page.setSize(goodsVo.getPageSize());
        Page<Goods> goodsPage = this.page(page, query);
        return goodsPage;
    }

    @Override
    public String addGoods(GoodsVo goodsVo) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo, goods);
        goods.insert();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editGoods(GoodsVo goodsVo) {
        QueryWrapper<Goods> query = getQuery();
        query.eq(SysConf.ID, goodsVo.getId());
        Goods goods = this.getOne(query);
        if (goods == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(goodsVo, goods);
        goods.updateById();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchGoods(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        this.baseMapper.deleteBatchIds(ids);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    private QueryWrapper<Goods> getQuery() {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }
}
