package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.enums.EPublish;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.BlogVo;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.mapper.blog.BlogMapper;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.BlogSortService;
import com.alex.blog.xo.service.blog.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.EntityWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    private static final String TAG = "tag";
    private static final String BLOG_SORT = "blogSort";

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
        //将标签信息按id做成map
        Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        //将分类信息按id做成map
        Map<Long, BlogSort> blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                item.setTagList(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(tagId -> tagMap.get(tagId)).collect(Collectors.toList()));
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(blogSortId -> blogSortMap.get(blogSortId)).collect(Collectors.toList()));
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
        if (tagIds.size() > 0) {
            blogSorts = blogSortService.listByIds(tagIds);
        }
        List<Map<String, Object>> picList = new ArrayList<>();
        // TODO: 2021/11/26 查询图片数据
        //将标签信息按id做成map
        Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        //将分类信息按id做成map
        Map<Long, BlogSort> blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        //将图片信息按id做成map
        Map<Long, String> picMap = picList.stream().collect(Collectors.toMap(item -> (long)item.get(SysConf.ID), item -> item.get(SysConf.URL).toString()));
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                item.setTagList(StringUtils.splitLongByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(tagId -> tagMap.get(tagId)).collect(Collectors.toList()));
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(blogSortId -> blogSortMap.get(blogSortId)).collect(Collectors.toList()));
            }
            //设置图片信息
            if (StringUtils.isNotEmpty(item.getFileId())) {
                item.setBlogSortList(StringUtils.splitLongByCode(item.getFileId(), SysConf.FILE_SEGMENTATION).
                        stream().map(blogSortId -> blogSortMap.get(blogSortId)).collect(Collectors.toList()));
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

        return null;
    }

    @Override
    public IPage<Blog> getBlogPageByLevel(Page<Blog> page, int level, Integer useSort) {
        return null;
    }

    @Override
    public Integer getBlogCount(Integer status) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getBlogCountByTag() {
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
        return searchBlogByType(tagId, (long)1, (long)10, TAG);
    }

    @Override
    public IPage<Blog> getBlogListBySortId(Integer blogSortId, Long currentPage, Long currentPageSize) {
        return searchBlogByType(blogSortId, currentPage, currentPageSize, BLOG_SORT);
    }

    @Override
    public Map<String, Object> getBlogByKeyword(String keyword, Long currentPage, Long currentPageSize) {
        return null;
    }

    @Override
    public IPage<Blog> searchBlogByTag(Integer blogTagId, Long currentPage, Long currentPageSize) {
        Tag tag = tagService.getById(blogTagId);
        if (tag != null) {
            HttpServletRequest request = RequestHolder.getRequest();
            String ip = IpUtils.getIpAddr(request);
            // TODO: 2021/11/30 添加当前登录人 
            //从redis中取数据判断该用户24小时内是否点击过该分类
            String jsonResult = redisUtils.get(RedisConf.TAG_CLICK + RedisConf.SEGMENTATION + ip + RedisConf.WELL_NUMBER + blogTagId);
            if (StringUtils.isEmpty(jsonResult)) {
                //给标签点击数增加
                int clickCount = tag.getClickCount() + 1;
                tag.setClickCount(clickCount);
                tag.updateById();
                //将该用户点击记录存储到redis中，24小时后过期
                redisUtils.setEx(RedisConf.TAG_CLICK + RedisConf.SEGMENTATION + ip + RedisConf.WELL_NUMBER + blogTagId,
                        clickCount + "", 24, TimeUnit.HOURS);
            }
        }
       return searchBlogByType(blogTagId, currentPage, currentPageSize, TAG);
    }

    /**
     * @param blogTagId
     * @param currentPage
     * @param currentPageSize
     * @param type  类型tag、blogSort
     * @description: 根据类型分类查询
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    private IPage<Blog> searchBlogByType(Integer blogTagId, Long currentPage, Long currentPageSize, String type) {
        //设置分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage == null ? 1 : currentPage);
        page.setSize(currentPageSize == null ? 10 : currentPageSize);
        //设置查询条件
        QueryWrapper<Blog> query = new QueryWrapper<>();
        String idType = "";
        if ("tag".equals(type)) {
            idType = SysConf.TAG_ID;
        } else if ("blogSort".equals(type)) {
            idType = SysConf.BLOG_SORT_ID;
        }
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .eq(idType, blogTagId).orderByDesc(SysConf.CREATE_TIME).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        IPage<Blog> pageList = blogService.page(page, query);
        List<Blog> blogList = pageList.getRecords();
        //设置分类、标签和图片
        blogList = setTagPictureAndSortByBlogList(blogList);
        pageList.setRecords(blogList);
        return pageList;
    }

    @Override
    public IPage<Blog> searchBlogByBlogSort(Integer blogSortId, Long currentPage, Long currentPageSize) {
        BlogSort blogSort = blogSortService.getById(blogSortId);
        if (blogSort != null) {
            HttpServletRequest request = RequestHolder.getRequest();
            String ip = IpUtils.getIpAddr(request);
            // TODO: 2021/11/30 添加当前登录人 
            //从redis中取数据判断该用户24小时内是否点击过该分类
            String jsonResult = redisUtils.get(RedisConf.SORT_CLICK + RedisConf.SEGMENTATION + ip + RedisConf.WELL_NUMBER + blogSortId);
            if (StringUtils.isEmpty(jsonResult)) {
                //给标签点击数增加
                int clickCount = blogSort.getClickCount() + 1;
                blogSort.setClickCount(clickCount);
                blogSort.updateById();
                //将该用户点击记录存储到redis中，24小时后过期
                redisUtils.setEx(RedisConf.SORT_CLICK + RedisConf.SEGMENTATION + ip + RedisConf.WELL_NUMBER + blogSortId,
                        clickCount + "", 24, TimeUnit.HOURS);
            }
        }
        return searchBlogByType(blogSortId, currentPage, currentPageSize, BLOG_SORT);
    }

    @Override
    public IPage<Blog> searchBlogByAuthor(String author, Long currentPage, Long currentPageSize) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(currentPageSize);
        // TODO: 2021/11/30 以后修改成按照操作时间排序
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .eq(SysConf.AUTHOR, author).orderByDesc(SysConf.CREATE_TIME).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        IPage<Blog> pageList = blogService.page(page, query);
        List<Blog> blogList = pageList.getRecords();
        blogList = setTagPictureAndSortByBlogList(blogList);
        pageList.setRecords(blogList);
        return pageList;
    }

    @Override
    public String getBlogTimeSortList() {
        //从redis中获取月份集合
        String monthResult = redisUtils.get(SysConf.MONTH_SET);
        //判断redis中是否包含归档内容
        if (StringUtils.isNotEmpty(monthResult)) {
            return ResultUtil.resultSuccessWithData(JsonUtils.jsonToArrayList(monthResult));
        }
        Map<String, Blog> map = getFile();
        return ResultUtil.resultSuccessWithData(JsonUtils.objectToJson(map.keySet()));
    }

    @Override
    public String getArticleByMonth(String monthDate) {
        if (StringUtils.isEmpty(monthDate)) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        //从redis中获取内容
        // TODO: 2021/11/30 添加不同管理员区分 
        String contentResult = redisUtils.get(SysConf.BLOG_SORT_BY_MONTH + RedisConf.SEGMENTATION + monthDate);
        //如果redis中存在则直接返回值
        if (StringUtils.isNotEmpty(contentResult)){
            return ResultUtil.resultSuccessWithData(JsonUtils.jsonToArrayList(contentResult));
        }
        Map<String, Blog> map = getFile();
        return ResultUtil.resultSuccessWithData(map.get(monthDate));
    }

    //获取归档博客信息
    private Map<String, Blog> getFile() {
        QueryWrapper<Blog> qeury = new QueryWrapper<>();
        //列表中不需要博客内容
        // TODO: 2021/11/30 按照操作时间排序
        qeury.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .orderByDesc(SysConf.CREATE_TIME).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        List<Blog> list = blogService.list(qeury);
        //将分类、标签和图片添加到博客中去
        list = blogService.setTagPictureAndSortByBlogList(list);
        // TODO: 2021/11/30 修改为按照操作时间排序
        Map<String, Blog> map = list.stream().collect(Collectors.toMap(item -> DateUtils.getTimeStr(item.getCreateTime(), "YYYY-MM-DD"), item -> item));
        //将数据缓存到redis中
        map.forEach((key, value) -> {
            redisUtils.set(SysConf.BLOG_SORT_BY_MONTH + RedisConf.SEGMENTATION + key, JsonUtils.objectToJson(value));
        });
        //将月份缓存到redis中
        redisUtils.set(SysConf.MONTH_SET, JsonUtils.objectToJson(map.keySet()));
        return map;
    }
}
