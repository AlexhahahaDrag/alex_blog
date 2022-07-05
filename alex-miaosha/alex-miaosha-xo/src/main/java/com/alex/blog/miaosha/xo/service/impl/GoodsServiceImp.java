package com.alex.blog.miaosha.xo.service.impl;

import com.alex.blog.miaosha.common.entity.Goods;
import com.alex.blog.miaosha.common.vo.GoodsVo;
import com.alex.blog.miaosha.xo.mapper.GoodsMapper;
import com.alex.blog.miaosha.xo.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * @description:  服务实现类
 * @author:       alex
 * @createDate:   2022-07-04 18:03:44
 * @version:      1.0.0
 */
@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }
}
