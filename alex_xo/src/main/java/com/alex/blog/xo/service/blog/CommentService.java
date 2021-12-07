package com.alex.blog.xo.service.blog;

import com.alex.blog.common.vo.blog.Comment;
import com.alex.blog.base.service.SuperService;

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
     * @param blogIds 博客ids
     * @description: 根据博客id批量删除评论信息
     * @author:      alex
     * @return:      boolean
    */
    boolean deleteBatchCommentByBlogIds(List<String> blogIds);
}
