package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.enums.EPublish;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.TagVo;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.ResultUtil;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.blog.TagMapper;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2021-11-26 13:44:48
 */
@Service
public class TagServiceImp extends SuperServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    @Override
    public IPage<Tag> getPageList(TagVo tagVo) {
        return null;
    }

    @Override
    public List<Tag> getList() {
        QueryWrapper<Tag> query = getQuery();
        query.orderByDesc(SysConf.SORT);
        return this.list(query);
    }

    @Override
    public String addTag(TagVo tagVo) {
        QueryWrapper<Tag> query = getQuery();
        query.eq(SysConf.CONTENT, tagVo.getContent());
        Tag one = this.getOne(query);
        if (one != null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.ENTITY_EXIST);
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagVo, tag);
        //默认新增的标签是有效的
        tag.setStatus(EStatus.ENABLE.getCode());
        tag.insert();
        //删除redis中的blog_tag
        deleteRedisBlogTagList();
        return ResultUtil.resultErrorWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editTag(TagVo tagVo) {
        Tag one = this.getById(tagVo.getId());
        if (one == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        //如果名称不一致校验名称是否重复
        if (!one.getContent().equals(tagVo.getContent())) {
            QueryWrapper<Tag> query = getQuery();
            query.eq(SysConf.CONTENT, tagVo.getContent());
            Tag one1 = this.getOne(query);
            if (one1 != null) {
                return ResultUtil.resultErrorWithMessage("博客标签名称已存在!");
            }
        }
        BeanUtils.copyProperties(tagVo, one);
        one.updateById();
        //删除和标签相关博客信息
        blogService.deleteRedisByBlogTag();
        //删除博客标签缓存
        deleteRedisBlogTagList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchTag(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        //判断要删除的博客标签下是否有博客
        QueryWrapper<Blog> blogQuery = new QueryWrapper<>();
        blogQuery.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).in(SysConf.TAG_ID, ids);
        int count = blogService.count(blogQuery);
        if (count > 0) {
            return ResultUtil.resultErrorWithMessage(MessageConf.BLOG_UNDER_THIS_TAG);
        }
        this.baseMapper.deleteBatchIds(ids);
        deleteRedisBlogTagList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickTag(TagVo tagVo) {
        Tag tag = this.getById(tagVo.getId());
        if (tag == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        QueryWrapper<Tag> query = getQuery();
        query.orderByDesc(SysConf.SORT).last(SysConf.LIMIT_ONE);
        Tag one = this.getOne(query);
        int maxSort = one.getSort() + (one.getId().equals(tagVo.getId()) ? 0 : 1);
        tag.setSort(maxSort);
        tag.updateById();
        //删除redis中的blog tag
        deleteRedisBlogTagList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String tagSortByClickCount() {
        QueryWrapper<Tag> query = getQuery();
        query.orderByDesc(SysConf.CLICK_COUNT);
        List<Tag> tagList = this.list(query);
        for (int i = tagList.size() - 1; i >= 0; i--) {
            Tag tag = tagList.get(i);
            tag.setSort(i + 1);
        }
        this.updateBatchById(tagList);
        //删除redis中的blog tag
        deleteRedisBlogTagList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public String tagSortByCite() {
        QueryWrapper<Tag> tagQuery = getQuery();
        List<Tag> tagList = this.list(tagQuery);
        QueryWrapper<Blog> blogQuery = new QueryWrapper<>();
        blogQuery.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        List<Blog> blogList = blogService.list(blogQuery);
        Map<String, Integer> map = new HashMap<>();
        blogList.forEach(item -> {
            String tagIds = item.getTagId();
            List<String> tagIdsList = StringUtils.splitStringByCode(tagIds, SysConf.FILE_SEGMENTATION);
            for (String tagId : tagIdsList) {
                map.put(tagId, map.getOrDefault(tagId, 0) + 1);
            }
        });
        tagList.forEach(i -> {
            i.setSort(map.get(i.getId()));
        });
        this.updateBatchById(tagList);
        //删除redis中的blog tag
        deleteRedisBlogTagList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.OPERATION_SUCCESS);
    }

    @Override
    public List<Tag> getHotTag(Integer hotTagCount) {
        QueryWrapper<Tag> query = getQuery();
        query.orderByDesc(SysConf.SORT).orderByDesc(SysConf.CLICK_COUNT)
                .last("limit " + hotTagCount);
        return this.list(query);
    }

    @Override
    public Tag getTopTag() {
        QueryWrapper<Tag> query = getQuery();
        query.last(SysConf.LIMIT_ONE).orderByDesc(SysConf.SORT);
        return this.getOne(query);
    }

    private QueryWrapper<Tag> getQuery() {
        QueryWrapper<Tag> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        return query;
    }

    private void deleteRedisBlogTagList() {
        //删除redis中的blog_tag
        Set<String> keys = redisUtils.keys(RedisConf.BLOG_TAG + RedisConf.SEGMENTATION + "*");
        redisUtils.delete(keys);
    }
}
