package com.alex.blog.miaosha.xo.mapper;

import com.alex.blog.miaosha.common.entity.Goods;
import com.alex.blog.miaosha.common.vo.GoodsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description:   mapper
 * @author:       alex
 * @createDate: 2022-07-04 18:03:44
 * @version:      1.0.0
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    GoodsVo getGoodsVoByGoodsId(@Param(value = "goodsId") long goodsId);
}
