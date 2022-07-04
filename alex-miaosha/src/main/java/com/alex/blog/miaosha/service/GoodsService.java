package com.alex.blog.xo.service.;

import com.alex.blog.common.entity..Goods;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo..GoodsVo;
import java.util.List;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-07-04 18:03:44
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface GoodsService extends SuperService<Goods> {

    /**
     * @param: goodsVo
     * @description: 获取列表
     * @author:      alex
     * @createDate:  2022-07-04 18:03:44
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity..Goods>
     */
    IPage<Goods> getPageList(GoodsVo goodsVo);

    /**
     * @param: goodsVo
     * @description: 新增
     * @author:      alex
     * @createDate:  2022-07-04 18:03:44
     * @return:      java.lang.String
     */
    String addGoods(GoodsVo goodsVo);

    /**
     * @param: goodsVo
     * @description: 编辑
     * @author:      alex
     * @createDate:  2022-07-04 18:03:44
     * @return:      java.lang.String
     */
    String editGoods(GoodsVo goodsVo);

    /**
     * @param ids
     * @description: 批量删除
     * @author:      alex
     * @createDate:  2022-07-04 18:03:44
     * @return:      java.lang.String
     */
    String deleteBatchGoods(List<String> ids);
}
