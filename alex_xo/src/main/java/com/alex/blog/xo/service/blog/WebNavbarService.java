package com.alex.blog.xo.service.blog;

import com.alex.blog.common.entity.blog.WebNavbar;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo.blog.WebNavbarVo;
import java.util.List;
/**
 *  服务类
 * @author: alex
 * @createDate: 2022-03-10 14:15:42
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface WebNavbarService extends SuperService<WebNavbar> {

    /**
     * @param: webNavbarVo
     * @description: 获取列表
     * @author:      alex
     * @createDate:  2022-03-10 14:15:42
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.WebNavbar>
     */
    IPage<WebNavbar> getPageList(WebNavbarVo webNavbarVo);

    /**
     * @param: webNavbarVo
     * @description: 新增
     * @author:      alex
     * @createDate:  2022-03-10 14:15:42
     * @return:      java.lang.String
     */
    String addWebNavbar(WebNavbarVo webNavbarVo);

    /**
     * @param: webNavbarVo
     * @description: 编辑
     * @author:      alex
     * @createDate:  2022-03-10 14:15:42
     * @return:      java.lang.String
     */
    String editWebNavbar(WebNavbarVo webNavbarVo);

    /**
     * @param ids
     * @description: 批量删除
     * @author:      alex
     * @createDate:  2022-03-10 14:15:42
     * @return:      java.lang.String
     */
    String deleteBatchWebNavbar(List<String> ids);
}
