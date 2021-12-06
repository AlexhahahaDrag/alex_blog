package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.enums.EPublish;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.BlogVo;
import com.alex.blog.utils.utils.RedisUtils;
import com.alex.blog.utils.utils.StringUtils;
import com.alex.blog.xo.mapper.blog.BlogMapper;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.BlogSortService;
import com.alex.blog.xo.service.blog.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2021-11-25 16:17:08
 */
@Service
public class BlogServiceImp extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Blog> setTagByBlogList(List<Blog> list) {
        list.forEach(item -> setTagByBlog(item));
        return list;
    }

    @Override
    public List<Blog> setTagAndSortByBlogList(List<Blog> list) {
        List<Long> sortIds = new ArrayList<>();
        List<Long> tagIds = new ArrayList<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getTagId())) {
                tagIds.addAll(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                sortIds.addAll(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION));
            }
        });
        List<Tag> tags = null;
        //查询标签信息
        if (tagIds.size() > 0) {
            tags = tagService.listByIds(tagIds);
        }
        //查询博客分类信息
        List<BlogSort> blogSorts = null;
        if (tagIds.size() > 0) {
            blogSorts = blogSortService.listByIds(tagIds);
        }
        Map<Long, Tag> tagMap = new HashMap<>();
        if (tags != null && !tags.isEmpty()) {
            //将标签信息按id做成map
            tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        }
        Map<Long, BlogSort> blogSortMap = new HashMap<>();
        if (blogSorts != null && !blogSorts.isEmpty()) {
            blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        }
        //将分类信息按id做成map
        Map<Long, Tag> finalTagMap = tagMap;
        Map<Long, BlogSort> finalBlogSortMap = blogSortMap;
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                item.setTagList(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalTagMap::get).collect(Collectors.toList()));
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalBlogSortMap::get).collect(Collectors.toList()));
            }
        });
        return list;
    }

    @Override
    public List<Blog> setTagPictureAndSortByBlogList(List<Blog> list) {
        List<Long> sortIds = new ArrayList<>();
        List<Long> tagIds = new ArrayList<>();
        Set<Long> fileIdSet = new HashSet<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getTagId())) {
                tagIds.addAll(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                sortIds.addAll(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getFileId())) {
                fileIdSet.addAll(StringUtils.splitLongByCode(item.getFileId(), SysConf.FILE_SEGMENTATION));
            }
        });
        List<Tag> tags = null;
        //查询标签信息
        if (tagIds.size() > 0) {
            tags = tagService.listByIds(tagIds);
        }
        //查询博客分类信息
        List<BlogSort> blogSorts = null;
        if (sortIds.size() > 0) {
            blogSorts = blogSortService.listByIds(sortIds);
        }
        List<Map<String, Object>> picList = new ArrayList<>();

        //将标签信息按id做成map
        Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        //将分类信息按id做成map
        Map<Long, BlogSort> blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        //将图片信息按id做成map
        // TODO: 2021/11/26 查询图片数据
        Map<Long, String> picMap = picList.stream().collect(Collectors.toMap(item -> (long)item.get(SysConf.ID), item -> item.get(SysConf.URL).toString()));
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                item.setTagList(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(tagMap::get).collect(Collectors.toList()));
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(blogSortMap::get).collect(Collectors.toList()));
            }
            //设置图片信息
            if (StringUtils.isNotEmpty(item.getFileId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getFileId(), SysConf.FILE_SEGMENTATION).
                        stream().map(blogSortMap::get).collect(Collectors.toList()));
            }
        });
        return list;
    }

    @Override
    public Blog setTagByBlog(Blog blog) {
        String tagIds = blog.getTagId();
        //如果博客对应标签不为空，则查询对应标签信息
        if (StringUtils.isNotEmpty(tagIds)) {
            String[] ids = tagIds.split(SysConf.FILE_SEGMENTATION);
            // TODO: 2021/11/26 标签id为int 这里是string 看看运行时是否报错
            List<Tag> tags = tagService.listByIds(Arrays.asList(ids));
            blog.setTagList(tags);
        }
        return blog;
    }

    @Override
    public Blog setSortByBlog(Blog blog) {
        String blogSortIds = blog.getBlogSortId();
        //如果博客对应博客分类不为空，则查询对应分类信息
        if (StringUtils.isNotEmpty(blogSortIds)) {
            String[] ids = blogSortIds.split(SysConf.FILE_SEGMENTATION);
            // TODO: 2021/11/26 标签id为int 这里是string 看看运行时是否报错
            List<BlogSort> blogSorts = blogSortService.listByIds(Arrays.asList(ids));
            blog.setBlogSortList(blogSorts);
        }
        return blog;
    }

    @Override
    public List<Blog> getBlogListByLevel(Integer level) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        //查询状态是可用的发布的博客信息
        query.eq(SQLConf.LEVEL, level).eq(SQLConf.STATUS, EStatus.ENABLE.getCode())
                .eq(SQLConf.IS_PUBLISH, EPublish.PUBLISH.getCode());
        return blogService.list(query);
    }

    @Override
    public IPage<Blog> getBlogPageByLevel(Page<Blog> page, int level, Integer useSort) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        //查询状态是可用的发布的博客信息
        query.eq(SQLConf.LEVEL, level).eq(SQLConf.STATUS, EStatus.ENABLE.getCode())
                .eq(SQLConf.IS_PUBLISH, EPublish.PUBLISH.getCode());
        if (useSort == 0) {
            query.orderByDesc(SysConf.CREATE_TIME);
        } else {
            query.orderByDesc(SysConf.SORT);
        }
        //首页不需要显示内筒，所以不需要查询内容
        query.select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        return blogService.page(page, query);
    }

    @Override
    public Integer getBlogCount(Integer status) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode());
        return blogService.count(query);
    }

    @Override
    public List<Map<String, Object>> getBlogCountByTag() {
        //从redis中获取标签下含有的博客数量
        redisUtils.get(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + );

        return null;
    }

    @Override
    public List<Map<String, Object>> getBlogCountBySort() {
        return null;
    }

    @Override
    public Map<String, Object> getBlogContributeCount() {
        return null;
    }

    @Override
    public Blog getBlogById(Integer id) {
        return null;
    }

    @Override
    public List<Blog> getSameBlogById(Integer id) {
        return null;
    }

    @Override
    public List<Blog> getBlogListByTop(Integer top) {
        return null;
    }

    @Override
    public IPage<Blog> getPageList(BlogVo blogVo) {
        return null;
    }

    @Override
    public String addBolg(BlogVo blogVo) {
        return null;
    }

    @Override
    public String editBlog(BlogVo blogVo) {
        return null;
    }

    @Override
    public String editBatch(List<BlogVo> blogVoList) {
        return null;
    }

    @Override
    public String deleteBlog(Integer id) {
        return null;
    }

    @Override
    public String deleteBatchBlog(List<Integer> blogIds) {
        return null;
    }

    @Override
    public String uploadLocalBlog(List<MultipartFile> blogInfo) throws IOException {
        return null;
    }

    @Override
    public void deleteRedisByBlogSort() {

    }

    @Override
    public void deleteRedisByBlogTag() {

    }

    @Override
    public IPage<Blog> getBlogPageByLevel(Integer level, Long currentPage, Long currentPageSize, Long useSort) {
        return null;
    }

    @Override
    public IPage<Blog> getHotBlog() {
        return null;
    }

    @Override
    public IPage<Blog> getNewBlog() {
        return null;
    }

    @Override
    public IPage<Blog> getBlogBySearch(Long currentPage, Long currentSize) {
        return null;
    }

    @Override
    public IPage<Blog> getBlogByTime(Long currentPage, Long currentSize) {
        return null;
    }

    @Override
    public Integer getBlogPraiseCountById(Integer id) {
        return null;
    }

    @Override
    public String praiseBlogById(Integer id) {
        return null;
    }

    @Override
    public IPage<Blog> getSameBlogByTagId(Integer tagId) {
        return null;
    }

    @Override
    public IPage<Blog> getBlogListBySortId(Integer blogSortId, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public Map<String, Object> getBlogByKeyword(String keyword, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public IPage<Blog> searchBlogByTag(Integer blogTagId, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public IPage<Blog> searchBlogByBlogSort(Integer blogSortId, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public IPage<Blog> searchBlogByAuthor(String author, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public String getBlogTimeSortList() {
        return null;
    }

    @Override
    public String getArticleByMonth() {
        return null;
    }
}
