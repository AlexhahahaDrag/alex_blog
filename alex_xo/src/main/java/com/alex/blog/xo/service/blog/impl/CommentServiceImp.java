package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.Comment;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.xo.mapper.blog.CommentMapper;
import com.alex.blog.xo.service.blog.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public String deleteBatchCommentByBlogIds(List<String> blogIds) {
        if (blogIds == null || blogIds.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.in(SysConf.BLOG_ID, blogIds);
        commentService.remove(query);
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }
}
