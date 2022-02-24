package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.Comment;
import com.alex.blog.common.vo.blog.CommentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
public interface CommentService extends SuperService<Comment> {

    /**
     * @param status
     * @description: 获取评论数
     * @author:      alex
     * @createDate:  2022/1/26 10:42
     * @return:      java.lang.Integer
    */
    Integer getCommentCount(int status);

    /**
     * @param commentVo
     * @description: 获取评论列表
     * @author:      alex
     * @createDate:  2022/1/26 10:43
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Comment>
    */
    IPage<Comment> getPageList(CommentVo commentVo);

    /**
     * @param commentVo
     * @description: 新增评论
     * @author:      alex
     * @createDate:  2022/1/26 10:44
     * @return:      java.lang.String
    */
    String addComment(CommentVo commentVo);

    /**
     * @param commentVo
     * @description: 修改评论
     * @author:      alex
     * @createDate:  2022/1/26 10:45
     * @return:      java.lang.String
    */
    String editComment(CommentVo commentVo);

    /**
     * @param ids
     * @description: 批量删除评论
     * @author:      alex
     * @createDate:  2022/1/26 10:45
     * @return:      java.lang.String
    */
    String deleteBatchComment(List<String> ids);

    /**
     * @param blogIds 博客ids
     * @description: 根据博客id批量删除评论信息
     * @author:      alex
     * @return:      boolean
    */
    String deleteBatchCommentByBlogIds(List<String> blogIds);
}
