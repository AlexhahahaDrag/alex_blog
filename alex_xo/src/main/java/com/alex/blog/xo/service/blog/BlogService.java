package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.vo.blog.BlogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author alex
 * @since 2021-11-25 16:17:08
 */
public interface BlogService extends SuperService<Blog> {

    /**
     * @param        list
     * @description: 给博客设置标签
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> setTagByBlogList(List<Blog> list);

    /**
     * @param list
     * @description: 给博客设置分类和标签
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> setTagAndSortByBlogList(List<Blog> list);

    /**
     * @param list
     * @description: 给博客列表设置分类、标签和图片
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> setTagPictureAndSortByBlogList(List<Blog> list);

    /**
     * @param blog
     * @description: 给博客设置标签
     * @author:      alex
     * @return:      com.alex.blog.common.entity.blog.Blog
    */
    Blog setTagByBlog(Blog blog);

    /**
     * @param blog
     * @description: 给博客设置分类
     * @author:      alex
     * @return:      com.alex.blog.common.entity.blog.Blog
    */
    Blog setSortByBlog(Blog blog);

    /**
     * @param level
     * @description: 通过推荐等级获取博客列表
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> getBlogListByLevel(Integer level);

    /**
     * @param page
     * @param level
     * @param useSort
     * @description: 通过推荐等级获取博客分页
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getBlogPageByLevel(Page<Blog> page, int level, Integer useSort);

    /**
     * @param status
     * @description: 通过状态获取博客数量
     * @author:      alex
     * @return:      java.lang.Integer
    */
    Integer getBlogCount(Integer status);

    /**
     * @description: 通过标签分类获取博客数量
     * @author:      alex
     * @return:      java.util.List<Map<String,Object>>
    */
    List<Map<String, Object>> getBlogCountByTag();

    /**
     * @description: 通过分类分类获取博客数量
     * @author:      alex
     * @return:      java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String, Object>> getBlogCountBySort();

    /**
     * @description: 获取一年内博客贡献数
     * @author:      alex
     * @return:      java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String, Object> getBlogContributeCount();

    /**
     * @param id
     * @description: 通过id获取博客
     * @author:      alex
     * @return:      com.alex.blog.common.entity.blog.Blog
    */
    Blog getBlogById(String id);

    /**
     * @param id
     * @description: 获取博客相似博客
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> getSameBlogById(Integer id);

    /**
     * @param top
     * @description: 获取点击量前top的博客列表
     * @author:      alex
     * @return:      java.util.List<com.alex.blog.common.entity.blog.Blog>
    */
    List<Blog> getBlogListByTop(Integer top);

    /**
     * @param blogVo
     * @description: 获取博客列表
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getPageList(BlogVo blogVo);

    /**
     * @param blogVo
     * @description: 新增博客
     * @author:      alex
     * @return:      java.lang.String
    */
    String addBolg(BlogVo blogVo);

    /**
     * @param blogVo
     * @description: 编辑博客
     * @author:      alex
     * @return:      java.lang.String
    */
    String editBlog(BlogVo blogVo);

    /**
     * @param blogVoList
     * @description: 推荐博客调整
     * @author:      alex
     * @return:      java.lang.String
    */
    String editBatch(List<BlogVo> blogVoList);

    /**
     * @param id
     * @description: 根据id删除博客
     * @author:      alex
     * @return:      java.lang.String
    */
    String deleteBlog(Integer id);

    /**
     * @param blogIds
     * @description: 批量删除博客
     * @author:      alex
     * @return:      java.lang.String
    */
    String deleteBatchBlog(List<Integer> blogIds);

    /**
     * @param blogInfo
     * @description: 上传本地博客
     * @author:      alex
     * @return:      java.lang.String
    */
    String uploadLocalBlog(List<MultipartFile> blogInfo) throws IOException;

    /**
     * @description: 删除和博客分类有关的redis缓存
     * @author:      alex
     * @return:      void
    */
    void deleteRedisByBlogSort();

    /**
     * @description: 删除和博客标签有关的redis缓存
     * @author:      alex
     * @return:      void
    */
    void deleteRedisByBlogTag();

    /**
     * @description: 删除redis中博客缓存
     * @author:      alex
     * @return:      void
    */
    void deleteRedisByBlog();

    /**
     * @param level
     * @param currentPage
     * @param currentPageSize
     * @param useSort
     * @description: 通过推荐等级获取博客分页信息
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getBlogPageByLevel(Integer level, Long currentPage, Long currentPageSize, Integer useSort);

    /**
     * @description: 获取首页排行博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getHotBlog();

    /**
     * @param currentPage
     * @param currentSize
     * @description: 获取最新的博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getNewBlog(Long currentPage, Long pageSize);

    /**
     * @param currentPage
     * @param currentSize
     * @description: 通过查询获取博客信息
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getBlogBySearch(Long currentPage, Long currentSize);

    /**
     * @param currentPage
     * @param currentSize
     * @description: 按时间戳获取博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getBlogByTime(Long currentPage, Long currentSize);

    /**
     * @param id
     * @description: 通过博客id获取博客点赞数
     * @author:      alex
     * @return:      java.lang.Integer
    */
    Integer getBlogPraiseCountById(String id);

    /**
     * @param id
     * @description: 通过id给博客点赞
     * @author:      alex
     * @return:      java.lang.String
    */
    String praiseBlogById(String id);

    /**
     * @param tagId
     * @description: 通过标签id获取相关的博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getSameBlogByTagId(Integer tagId);

    /**
     * @param blogSortId
     * @param currentPage
     * @param currentPageSize
     * @description: 通过分类id获取博客列表
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> getBlogListBySortId(Integer blogSortId, Long currentPage, Long currentPageSize);

    /**
     * @param keyword
     * @param currentPage
     * @param currentPageSize
     * @description: 通过关键字查询博客列表
     * @author:      alex
     * @return:      java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String, Object> getBlogByKeyword(String keyword, Long currentPage, Long currentPageSize);

    /**
     * @param blogTagId
     * @param currentPage
     * @param currentPageSize
     * @description: 通过标签搜索博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> searchBlogByTag(Integer blogTagId, Long currentPage, Long currentPageSize);

    /**
     * @param blogSortId
     * @param currentPage
     * @param currentPageSize
     * @description: 通过博客分类搜索博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> searchBlogByBlogSort(Integer blogSortId, Long currentPage, Long currentPageSize);

    /**
     * @param author
     * @param currentPage
     * @param currentPageSize
     * @description: 通过作者搜索博客
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    IPage<Blog> searchBlogByAuthor(String author, Long currentPage, Long currentPageSize);

    /**
     * @description: 获取博客的归档日期
     * @author:      alex
     * @return:      java.lang.String
    */
    String getBlogTimeSortList();

    /**
     * @description: 通过月份获取文章
     * @author:      alex
     * @return:      java.lang.String
    */
    String getArticleByMonth(String monthDate);
 }
