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
     * @description:  
     * @author:      alex 
     * @createDate:  2022/1/27 17:52
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Link>
    */
    IPage<Link> getPageList(LinkVo linkVo);
}
