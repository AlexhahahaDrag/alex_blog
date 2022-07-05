package com.alex.blog.miaosha.xo.service.impl;

import com.alex.blog.miaosha.common.entity.MiaoshaGoods;
import com.alex.blog.miaosha.xo.mapper.MiaoshaGoodsMapper;
import com.alex.blog.miaosha.xo.service.MiaoshaGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-07-04 18:03:45
 * @version:      1.0.0
 */
@Service
public class MiaoshaGoodsServiceImp extends ServiceImpl<MiaoshaGoodsMapper, MiaoshaGoods> implements MiaoshaGoodsService {

//    @Override
//    public IPage<MiaoshaGoods> getPageList(MiaoshaGoodsVo miaoshaGoodsVo) {
//        QueryWrapper<MiaoshaGoods> query = getQuery();
//        Page<MiaoshaGoods> page = new Page<>();
//        page.setCurrent(miaoshaGoodsVo.getCurrentPage());
//        page.setSize(miaoshaGoodsVo.getPageSize());
//        Page<MiaoshaGoods> miaoshaGoodsPage = this.page(page, query);
//        return miaoshaGoodsPage;
//    }
//
//    @Override
//    public String addMiaoshaGoods(MiaoshaGoodsVo miaoshaGoodsVo) {
//        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
//        BeanUtils.copyProperties(miaoshaGoodsVo, miaoshaGoods);
//        miaoshaGoods.insert();
//        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
//    }
//
//    @Override
//    public String editMiaoshaGoods(MiaoshaGoodsVo miaoshaGoodsVo) {
//        QueryWrapper<MiaoshaGoods> query = getQuery();
//        query.eq(SysConf.ID, miaoshaGoodsVo.getId());
//        MiaoshaGoods miaoshaGoods = this.getOne(query);
//        if (miaoshaGoods == null) {
//            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
//        }
//        BeanUtils.copyProperties(miaoshaGoodsVo, miaoshaGoods);
//        miaoshaGoods.updateById();
//        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
//    }
//
//    @Override
//    public String deleteBatchMiaoshaGoods(List<String> ids) {
//        if (ids == null || ids.isEmpty()) {
//            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
//        }
//        this.baseMapper.deleteBatchIds(ids);
//        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
//    }
//
//    private QueryWrapper<MiaoshaGoods> getQuery() {
//        QueryWrapper<MiaoshaGoods> query = new QueryWrapper<>();
//        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
//        return query;
//    }
}
