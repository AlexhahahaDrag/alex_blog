package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.vo.blog.TagVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:48
 */
public interface TagService extends SuperService<Tag> {

    /**
     * @param tagVo
     * @description: 获取博客标签列表
     * @author:      alex
     * @createDate:  2022/2/11 10:27
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Tag>
    */
    IPage<Tag> getPageList(TagVo tagVo);

    /**
     * @description: 获取全部博客标签列表
     * @author:      alex
     * @createDate:  2022/2/11 10:27
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Tag>
    */
    List<Tag> getList();

    /**
     * @param tagVo
     * @description: 新增博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:28
     * @return:      java.lang.String
    */
    String addTag(TagVo tagVo);

    /**
     * @param tagVo
     * @description: 编辑博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:28
     * @return:      java.lang.String
    */
    String editTag(TagVo tagVo);

    /**
     * @param ids
     * @description: 批量删除博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:30
     * @return:      java.lang.String
    */
    String deleteBatchTag(List<String> ids);

    /**
     * @param tagVo
     * @description: 置顶博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:30
     * @return:      java.lang.String
    */
    String stickTag(TagVo tagVo);

    /**
     * @description: 通过点击量排序博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:31
     * @return:      java.lang.String
    */
    String tagSortByClickCount();

    /**
     * @description: 通过引用量排序博客
     * @author:      alex
     * @createDate:  2022/2/14 15:16
     * @return:      java.lang.String
    */
    String tagSortByCite();

    /**
     * @param hotTagCount
     * @description: 获取热门标签
     * @author:      alex
     * @createDate:  2022/2/11 10:32
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Tag>
    */
    List<Tag> getHotTag(Integer hotTagCount);

    /**
     * @description: 获取排序最高的博客标签
     * @author:      alex
     * @createDate:  2022/2/11 10:32
     * @return:      com.alex.blog.common.entity.blog.Tag
    */
    Tag getTopTag();
}
