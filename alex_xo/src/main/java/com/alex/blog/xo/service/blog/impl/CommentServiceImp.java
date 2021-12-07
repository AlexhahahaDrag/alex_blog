package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.Comment;
import com.alex.blog.xo.mapper.blog.CommentMapper;
import com.alex.blog.xo.service.blog.CommentService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2021-12-02 17:14:54
 */
@Service
public class CommentServiceImp extends SuperServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentService commentService;

    @Override
    public boolean deleteBatchCommentByBlogIds(List<String> blogIds) {
        if (blogIds == null || blogIds.isEmpty()) {
            return false;
        }
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.in(SysConf.BLOG_ID, blogIds);
        return commentService.remove(query);
    }
}
