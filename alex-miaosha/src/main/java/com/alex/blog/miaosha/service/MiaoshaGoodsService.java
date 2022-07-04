package com.alex.blog.xo.service.;

import com.alex.blog.common.entity..MiaoshaGoods;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo..MiaoshaGoodsVo;
import java.util.List;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-07-04 18:03:45
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface MiaoshaGoodsService extends SuperService<MiaoshaGoods> {

    /**
     * @param: miaoshaGoodsVo
     * @description: 获取列表
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity..MiaoshaGoods>
     */
    IPage<MiaoshaGoods> getPageList(MiaoshaGoodsVo miaoshaGoodsVo);

    /**
     * @param: miaoshaGoodsVo
     * @description: 新增
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String addMiaoshaGoods(MiaoshaGoodsVo miaoshaGoodsVo);

    /**
     * @param: miaoshaGoodsVo
     * @description: 编辑
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String editMiaoshaGoods(MiaoshaGoodsVo miaoshaGoodsVo);

    /**
     * @param ids
     * @description: 批量删除
     * @author:      alex
     * @createDate:  2022-07-04 18:03:45
     * @return:      java.lang.String
     */
    String deleteBatchMiaoshaGoods(List<String> ids);
}
