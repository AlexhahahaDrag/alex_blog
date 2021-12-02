package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.vo.blog.Comment;
import com.alex.blog.xo.mapper.blog.CommentMapper;
import com.alex.blog.xo.service.blog.CommentService;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;

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

}
