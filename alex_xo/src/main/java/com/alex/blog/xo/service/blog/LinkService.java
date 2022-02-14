package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.Link;
import com.alex.blog.common.vo.blog.LinkVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 友情链接表 服务类
 * </p>
 *
 * @author alex
 * @since 2022-01-27 17:32:37
 */
public interface LinkService extends SuperService<Link> {

    /**
     * @param pageSize
     * @description: 根据页面大小获取链接列表 
     * @author:      alex 
     * @createDate:  2022/1/27 17:51
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Link>
    */
    List<Link> getListByPageSize(Integer pageSize);
    
    /**
     * @param linkVo
     * @description: 获取友链列表
     * @author:      alex 
     * @createDate:  2022/1/27 17:56
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Link>
    */
    IPage<Link> getPageList(LinkVo linkVo);

    /**
     * @param linkVo
     * @description: 新增友链
     * @author:      alex
     * @createDate:  2022/1/28 9:42
     * @return:      java.lang.String
    */
    String addLink(LinkVo linkVo);

    /**
     * @param linkVo
     * @description: 编辑友链
     * @author:      alex
     * @createDate:  2022/1/28 9:43
     * @return:      java.lang.String
    */
    String editLink(LinkVo linkVo);

    /**
     * @param ids
     * @description: 批量删除友链
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
    */
    String deleteBatchLink(List<String> ids);

    /**
     * @param linkVo
     * @description: 置顶友链
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
    */
    String stickLink(LinkVo linkVo);

    /**
     * @param id
     * @description: 点击友链
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
    */
    String addLinkCount(String id);
}
