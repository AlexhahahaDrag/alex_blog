package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.Comment;
import com.alex.blog.common.enums.ECommentSource;
import com.alex.blog.common.feign.PictureFeignClient;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.CommentVo;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.blog.CommentMapper;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.CommentService;
import com.alex.blog.xo.utils.WebUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Autowired
    private WebUtils webUtils;

    @Override
    public Integer getCommentCount(int status) {
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, status);
        return commentMapper.selectCount(query);
    }

    @Override
    public IPage<Comment> getPageList(CommentVo commentVo) {
        QueryWrapper<Comment> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(commentVo.getKeyword()) && StringUtils.isNotEmpty(commentVo.getKeyword().trim())) {
            query.like(SysConf.CONTENT, commentVo.getKeyword().trim());
        }
        if (commentVo.getType() != null) {
            query.eq(SysConf.TYPE, commentVo.getType());
        }
        if (StringUtils.isNotEmpty(commentVo.getSource()) && !SysConf.ALL.equals(commentVo.getSource())) {
            query.eq(SysConf.SOURCE, commentVo.getSource());
        }
        if (StringUtils.isNotEmpty(commentVo.getUsername())) {
            QueryWrapper<Admin> userQuery = new QueryWrapper<>();
            userQuery.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).like(SysConf.NICK_NAME, commentVo.getUsername());
            List<Admin> adminList = adminService.list(userQuery);
            List<String> adminIdList = adminList.stream().map(Admin::getId).collect(Collectors.toList());
            query.in(SysConf.USER_ID, adminIdList);
        }
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).orderByDesc(SysConf.OPERATE_TIME);
        Page<Comment> page = new Page<>();
        page.setSize(commentVo.getPageSize());
        page.setCurrent(commentVo.getCurrentPage());
        Page<Comment> pageList = this.page(page, query);
        List<Comment> records = pageList.getRecords();
        Set<String> userIdSet = new HashSet<>();
        Set<String> blogIdSet = new HashSet<>();
        records.forEach(item -> {
            if(StringUtils.isNotEmpty(item.getUserId())) {
                userIdSet.add(item.getUserId());
            }
            if(StringUtils.isNotEmpty(item.getToUserId())) {
                userIdSet.add(item.getToUserId());
            }
            if(StringUtils.isNotEmpty(item.getBlogId())) {
                blogIdSet.add(item.getBlogId());
            }
        });
        //获取博客信息
        Map<String, Blog> blogMap = new HashMap<>();
        if (!blogIdSet.isEmpty()) {
            QueryWrapper<Blog> blogQuery = new QueryWrapper<>();
            query.select(i -> !i.getProperty().equals(SysConf.CONTENT)).eq(SysConf.STATUS, EStatus.ENABLE.getCode())
                    .in(SysConf.ID, blogIdSet);
            List<Blog> blogList = blogService.list(blogQuery);
            blogMap = blogList.stream().collect(Collectors.toMap(Blog::getId, blog -> blog));
        }
        //获取头像信息
        Map<String, Admin> adminMap = new HashMap<>();
        if(!userIdSet.isEmpty()) {
            List<Admin> adminList = adminService.listByIds(userIdSet);
            String pictureIdList = adminList.stream().map(Admin::getAvatar).collect(Collectors.joining(SysConf.FILE_SEGMENTATION));
            String pictureResult = pictureFeignClient.getPicture(pictureIdList, SysConf.FILE_SEGMENTATION);
            List<Map<String, Object>> pictureList = webUtils.getPictureMap(pictureResult);
            Map<Object, String> pictureMap = pictureList.stream().collect(Collectors.toMap(item -> item.get(SysConf.ID), item -> item.get(SysConf.URL).toString()));
            adminMap = adminList.stream().map(item -> {
                if (pictureMap.get(item.getAvatar()) != null) {
                    item.setPhotoList(Lists.newArrayList(pictureMap.get(item.getAvatar())));
                }
                return item;
            }).collect(Collectors.toMap(Admin::getId, admin -> admin));
        }
        //给评论附相关用户信息和博客信息
        for (Comment comment : records) {
            comment.setSourceName(ECommentSource.valueOf(comment.getSource()).getValue());
            if (StringUtils.isNotEmpty(comment.getUserId())) {
                comment.setUser(adminMap.get(comment.getUserId()));
            }
            if (StringUtils.isNotEmpty(comment.getToUserId())) {
                comment.setToUser(adminMap.get(comment.getToUserId()));
            }
            if (StringUtils.isNotEmpty(comment.getBlogId())) {
                comment.setBlog(blogMap.get(comment.getBlogId()));
            }
        }
        pageList.setRecords(records);
        return pageList;
    }

    @Override
    public String addComment(CommentVo commentVo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);
        comment.insert();
        return ResultUtil.resultErrorWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editComment(CommentVo commentVo) {
        if (StringUtils.isEmpty(commentVo.getId())) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo, comment);
        comment.updateById();
        return ResultUtil.resultErrorWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchComment(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        commentMapper.deleteBatchIds(ids);
        return ResultUtil.resultErrorWithMessage(MessageConf.DELETE_SUCCESS);
    }

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
