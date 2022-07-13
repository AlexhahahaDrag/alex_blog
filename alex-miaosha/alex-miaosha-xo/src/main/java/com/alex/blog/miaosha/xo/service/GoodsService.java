package com.alex.blog.miaosha.xo.service;

import com.alex.blog.miaosha.common.entity.Goods;
import com.alex.blog.miaosha.common.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-07-04 18:03:44
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface GoodsService extends IService<Goods> {

    GoodsVo getGoodsVoByGoodsId(long goodsId);

//    Result<GoodsVoOrder> listGoodsVo();
}
