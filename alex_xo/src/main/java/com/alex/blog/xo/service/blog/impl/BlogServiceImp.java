package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.holder.RequestHolder;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.admin.Admin;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.Comment;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.enums.*;
import com.alex.blog.common.feign.PictureFeignClient;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SQLConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.utils.UserUtil;
import com.alex.blog.common.vo.blog.BlogVo;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.mapper.blog.BlogMapper;
import com.alex.blog.xo.service.admin.AdminService;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.BlogSortService;
import com.alex.blog.xo.service.blog.CommentService;
import com.alex.blog.xo.service.blog.TagService;
import com.alex.blog.xo.service.sys.SysParamsService;
import com.alex.blog.xo.service.sys.SystemConfigService;
import com.alex.blog.xo.utils.WebUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    @Resource
    private PictureFeignClient pictureFeignClient;

    @Autowired
    private WebUtils webUtils;

    @Autowired
    private CommentService commentService;

    private static final String TAG = "tag";
    private static final String BLOG_SORT = "blogSort";

    @Autowired
    private SysParamsService sysParamsService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public List<Blog> setTagByBlogList(List<Blog> list) {
        list.forEach(this::setTagByBlog);
        return list;
    }

    @Override
    public List<Blog> setTagAndSortByBlogList(List<Blog> list) {
        List<String> sortIds = new ArrayList<>();
        List<String> tagIds = new ArrayList<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getTagId())) {
                tagIds.addAll(StringUtils.splitStringByCode(item.getTagId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                sortIds.addAll(StringUtils.splitStringByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION));
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
        //将标签信息按id做成map
        Map<String, Tag> tagMap = new HashMap<>();
        if (tags != null && !tags.isEmpty()) {
            tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        }
        //将分类信息按id做成map
        Map<String, BlogSort> blogSortMap = new HashMap<>();
        if (blogSorts != null &&!blogSorts.isEmpty()) {
            blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        }
        Map<String, Tag> finalTagMap = tagMap;
        Map<String, BlogSort> finalBlogSortMap = blogSortMap;
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                item.setTagList(StringUtils.splitStringByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalTagMap::get).collect(Collectors.toList()));
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                item.setBlogSortList(StringUtils.splitStringByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalBlogSortMap::get).collect(Collectors.toList()));
            }
        });
        return list;
    }

    @Override
    public List<Blog> setTagPictureAndSortByBlogList(List<Blog> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<String> sortIds = new ArrayList<>();
        List<String> tagIds = new ArrayList<>();
        Set<String> fileIdSet = new HashSet<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getTagId())) {
                tagIds.addAll(StringUtils.splitStringByCode(item.getTagId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                sortIds.addAll(StringUtils.splitStringByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION));
            }
            if (StringUtils.isNotEmpty(item.getFileId())) {
                fileIdSet.addAll(StringUtils.splitStringByCode(item.getFileId(), SysConf.FILE_SEGMENTATION));
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
        Map<String, Tag> tagMap = new HashMap<>();
        if (tags != null && !tags.isEmpty()) {
            tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, item -> item));
        }
        //将分类信息按id做成map
        Map<String, BlogSort> blogSortMap = new HashMap<>();
        if (blogSorts != null && !blogSorts.isEmpty()) {
            blogSortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getId, item -> item));
        }
        //将图片信息按id做成map
        Map<String, String> picMap = new HashMap<>();
        if (picList != null && !picList.isEmpty()) {
            picMap = picList.stream().collect(Collectors.toMap(item -> (String)item.get(SysConf.ID), item -> item.get(SysConf.URL).toString()));
        }
        Map<String, Tag> finalTagMap = tagMap;
        Map<String, BlogSort> finalBlogSortMap = blogSortMap;
        Map<String, String> finalPicMap = picMap;
        list.forEach(item -> {
            //设置标签信息
            if (StringUtils.isNotEmpty(item.getTagId())) {
                List<Tag> tagList = StringUtils.splitStringByCode(item.getTagId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalTagMap::get).collect(Collectors.toList());
                item.setTagList(tagList);
                if (!tagList.isEmpty() && (tagList.get(0) != null && tagList.get(0).getId() != null)) {
                    item.setTagName(tagList.stream().map(Tag::getContent).collect(Collectors.joining(",")));
                }
            }
            //设置分类信息
            if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                List<BlogSort> blogSortList = StringUtils.splitStringByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                        stream().map(finalBlogSortMap::get).collect(Collectors.toList());
                item.setBlogSortList(blogSortList);
                if (!blogSortList.isEmpty() && blogSortList.get(0) != null) {
                    item.setBlogSortName(blogSortList.stream().map(BlogSort::getSortName).collect(Collectors.joining(",")));
                }
            }
            //设置图片信息
            if (StringUtils.isNotEmpty(item.getFileId())) {
                List<String> fileIdList = StringUtils.splitStringByCode(item.getFileId(), SysConf.FILE_SEGMENTATION);
                for(String fileId : fileIdList) {
                    //只设置一张标题图
                    if (finalPicMap.get(fileId) != null) {
                        item.setPhotoUrl(finalPicMap.get(fileId));
                        break;
                    }
                }
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

    /**
     * @param page
     * @param level
     * @param useSort
     * @description: 分页查询数据
     * @author:       alex
     * @return:       com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
     */
    @Override
    public IPage<Blog> getBlogPageByLevel(Page<Blog> page, int level, Integer useSort) {
        String sortStr;
        if (useSort == 0) {
            sortStr = SysConf.CREATE_TIME;
        } else {
            sortStr = SysConf.SORT;
        }
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put(SysConf.LEVEL, level);
        return searchBlogByType(page.getCurrent(), page.getSize(), eqMap, sortStr);
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
        String blogCountTagList = redisUtils.get(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.SEGMENTATION + SysConf.BLOG_COUNT_BY_TAG);
        if (StringUtils.isNotEmpty(blogCountTagList)) {
            return JsonUtils.jsonToArrayList(blogCountTagList);
        }
        List<Map<String, Object>> blogTagCountList = blogMapper.getBlogCountByTag();
        Map<String, Long> tagCountMap = new HashMap<>();
        String tagId;
        for (Map<String, Object> map : blogTagCountList) {
            tagId = map.get(SysConf.TAG_ID).toString();
            //如果长度是32所名师一个标签
            if (tagId.length() == Constants.NUM_32) {
                tagCountMap.put(tagId, tagCountMap.getOrDefault(tagId, 0L) + Long.parseLong(map.get(SysConf.COUNT).toString()));
            } else {
                //多标签情况
                String[] tagIds = tagId.split(",");
                for (String tId : tagIds) {
                    tagCountMap.put(tId, tagCountMap.getOrDefault(tId, 0L) + Long.parseLong(map.get(SysConf.COUNT).toString()));
                }
            }
        }
        //查询标签信息
        List<Tag> tagList = tagService.listByIds(tagCountMap.keySet());
        Map<String, String> tagMap = tagList.parallelStream().collect(Collectors.toMap(Tag::getId, Tag::getContent));
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map;
        for (Map.Entry<String, Long> entry: tagCountMap.entrySet()) {
            map = new HashMap<>();
            map.put(SysConf.TAG_ID, entry.getKey());
            map.put(SysConf.COUNT, entry.getValue());
            map.put(SysConf.NAME, tagMap.get(entry.getKey()));
            resultList.add(map);
        }
        //保存到redis中,有效时间两小时
        if (resultList.size() > 0) {
            redisUtils.setEx(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.BLOG_COUNT_BY_TAG, JsonUtils.objectToJson(resultList), 2, TimeUnit.HOURS);
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getBlogCountBySort() {
        //从redis中获取标签下含有的博客数量
        String blogCountSortList = redisUtils.get(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.SEGMENTATION + SysConf.BLOG_COUNT_BY_SORT);
        if (StringUtils.isNotEmpty(blogCountSortList)) {
            return JsonUtils.jsonToArrayList(blogCountSortList);
        }
        List<Map<String, Object>> blogTagCountList = blogMapper.getBlogCountBySort();
        Map<String, Long> blogSortCountMap = new HashMap<>();
        String blogSortId;
        for (Map<String, Object> map : blogTagCountList) {
            blogSortId = map.get(SysConf.BLOG_SORT_ID).toString();
            //如果长度是32所名师一个标签
            if (blogSortId.length() == Constants.NUM_32) {
                blogSortCountMap.put(blogSortId, blogSortCountMap.getOrDefault(blogSortId, 0L) + Long.parseLong(map.get(SysConf.COUNT).toString()));
            } else {
                //多标签情况
                String[] blogSortIds = blogSortId.split(",");
                for (String tId : blogSortIds) {
                    blogSortCountMap.put(tId, blogSortCountMap.getOrDefault(tId, 0L) + Long.parseLong(map.get(SysConf.COUNT).toString()));
                }
            }
        }
        //查询标签信息
        List<BlogSort> blogSortList = blogSortService.listByIds(blogSortCountMap.keySet());
        Map<String, String> blogSortMap = new HashMap<>();
        if (blogSortList != null && !blogSortList.isEmpty()) {
            blogSortMap = blogSortList.parallelStream().collect(Collectors.toMap(BlogSort::getId, BlogSort::getSortName));
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map;
        for (Map.Entry<String, Long> entry: blogSortCountMap.entrySet()) {
            map = new HashMap<>();
            map.put(SysConf.BLOG_SORT_ID, entry.getKey());
            map.put(SysConf.COUNT, entry.getValue());
            map.put(SysConf.NAME, blogSortMap.get(entry.getKey()));
            resultList.add(map);
        }
        //保存到redis中,有效时间两小时
        if (resultList.size() > 0) {
            redisUtils.setEx(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.BLOG_COUNT_BY_SORT, JsonUtils.objectToJson(resultList), 2, TimeUnit.HOURS);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getBlogContributeCount() {
        return null;
    }

    @Override
    public Blog getBlogById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Blog blog = blogService.getById(id);
        if (blog != null && EStatus.ENABLE.getCode().equals(blog.getStatus())) {
            blog = setTagByBlog(blog);
            blog = setSortByBlog(blog);
        }
        return blog;
    }

    @Override
    public List<Blog> getSameBlogById(String id) {
        Blog blog = blogService.getById(id);
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put(SysConf.BLOG_SORT_ID, blog.getBlogSortId());
        IPage<Blog> blogIPage = searchBlogByType(0L, 10L, eqMap, null);
        return blogIPage.getRecords();
    }

    @Override
    public List<Blog> getBlogListByTop(Integer top) {
        IPage<Blog> blogPage = searchBlogByType(0L, (long) top, null, SysConf.SORT);
        return blogPage.getRecords();
    }

    @Override
    public IPage<Blog> getPageList(BlogVo blogVo) {
        Map<String, Object> eqMap = new HashMap<>();
        if (!StringUtils.isEmpty(blogVo.getLevelKeyword())) {
            eqMap.put(SQLConf.LEVEL, blogVo.getLevelKeyword());
        }
        if (!StringUtils.isEmpty(blogVo.getIsPublish())) {
            eqMap.put(SQLConf.IS_PUBLISH, blogVo.getIsPublish());
        }
        if (!StringUtils.isEmpty(blogVo.getIsOriginal())) {
            eqMap.put(SQLConf.IS_ORIGINAL, blogVo.getIsOriginal());
        }
        if(!StringUtils.isEmpty(blogVo.getType())) {
            eqMap.put(SQLConf.TYPE, blogVo.getType());
        }
        Map<String, Object> likeMap = new HashMap<>();
        if (StringUtils.isNotEmpty(blogVo.getKeyword()) && !StringUtils.isEmpty(blogVo.getKeyword().trim())) {
            likeMap.put(SysConf.TITLE, blogVo.getKeyword().trim());
        }
        if (!StringUtils.isEmpty(blogVo.getTagId())) {
            likeMap.put(SQLConf.TAG_ID, blogVo.getTagId());
        }
        if (!StringUtils.isEmpty(blogVo.getBlogSortId())) {
            likeMap.put(SysConf.BLOG_SORT_ID, blogVo.getBlogSortId());
        }
        if (blogVo.getLevel() != null) {
            eqMap.put(SQLConf.LEVEL, blogVo.getLevel());
        }
        List<String> orderList = null;
        if (blogVo.getUseSort() != null && blogVo.getUseSort() == 1){
            orderList = new ArrayList<>();
            orderList.add(SysConf.SORT);
        }
        return searchBlogByType(blogVo.getCurrentPage(), blogVo.getPageSize(), eqMap, likeMap, orderList);
    }

    @Override
    public String addBlog(BlogVo blogVo) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        query.eq(SysConf.LEVEL, blogVo.getLevel()).eq(SysConf.STATUS, blogVo.getStatus());
        int count = blogService.count(query);
        String res = addVerdict(count + 1, blogVo.getLevel());
        if (StringUtils.isNotEmpty(res)) {
            return res;
        }
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVo, blog);
        //如果是原创，为用户添加昵称
        if (EOriginal.ORIGINAL.getCode().equals(blogVo.getIsOriginal())) {
            String adminId = RequestHolder.getAdminId();
            Admin admin = adminService.getAdminById(adminId);
            if (admin != null) {
                blog.setAuthor(admin.getNickName() == null ? admin.getUsername() : admin.getNickName());
                blog.setAdminId(adminId);
            }
            String projectName = sysParamsService.getSysParamsValueByKey(SysConf.PROJECT_NAME);
            blog.setArticlesPart(projectName);
        } else {
            blog.setAuthor(blogVo.getAuthor());
            blog.setArticlesPart(blogVo.getArticlesPart());
        }
        blog.setStatus(EStatus.ENABLE.getCode());
        boolean isSave = blog.insert();
        //保存成功后，需要发送消息到redis
        updateSolrAndRedis(isSave, blog);
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editBlog(BlogVo blogVo) {
        Blog blog = blogService.getBlogById(blogVo.getId());
        if (blog == null) {
            return ResultUtil.resultErrorWithMessage("博客不存在！");
        }
        //如果数据库中的级别和vo中的级别不一致，则说明级别已经进行修改了
        if (!blog.getLevel().equals(blogVo.getLevel())) {
            //查询当前vo级别的博客数量
            QueryWrapper<Blog> query = new QueryWrapper<>();
            query.eq(SysConf.LEVEL, blogVo.getLevel()).eq(SysConf.STATUS, EStatus.ENABLE.getCode());
            int count = blogService.count(query);
            String res = addVerdict(count + 1, blogVo.getLevel());
            if (StringUtils.isNotEmpty(res)) {
                return res;
            }
        }
        BeanUtils.copyProperties(blogVo, blog);
        //如果是原创，为用户添加昵称
        if (EOriginal.ORIGINAL.getCode().equals(blogVo.getIsOriginal())) {
            String adminId = RequestHolder.getAdminId();
            Admin admin = adminService.getAdminById(adminId);
            if (admin != null) {
                blog.setAuthor(admin.getNickName() == null ? admin.getUsername() : admin.getNickName());
                blog.setAdminId(adminId);
            }
            String projectName = sysParamsService.getSysParamsValueByKey(SysConf.PROJECT_NAME);
            blog.setArticlesPart(projectName);
        } else {
            blog.setAuthor(blogVo.getAuthor());
            blog.setArticlesPart(blogVo.getArticlesPart());
        }
        boolean isSave = blog.updateById();
        //保存成功后，需要发送消息到redis
        updateSolrAndRedis(isSave, blog);
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String editBatch(List<BlogVo> blogVoList) {
        if (blogVoList == null || blogVoList.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        List<String> blogIdList = blogVoList.stream().map(BlogVo::getId).collect(Collectors.toList());
        Map<String, BlogVo> map = blogVoList.stream().collect(Collectors.toMap(BlogVo::getId, blogVo -> blogVo));
        List<Blog> blogList = blogService.listByIds(blogIdList);
        blogList.forEach(blog -> {
            BlogVo blogVo = map.get(blog.getId());
            if (blogVo != null) {
                BeanUtils.copyProperties(blogVo, blog);
                blog.setStatus(EStatus.ENABLE.getCode());
            }
        });
        boolean isSave = blogService.updateBatchById(blogList);
        if (isSave) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put(SysConf.COMMAND, SysConf.EDIT_BATCH);
            rabbitTemplate.convertAndSend(SysConf.EXCHANGE_DIRECT, SysConf.ALEX_BLOG, map1);
        }
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBlog(String id) {
        Blog blog = blogService.getById(id);
        blog.setDeleter(UserUtil.getLoginUser().getId());
        blog.setDeleteTime(LocalDateTime.now());
        boolean delete = blog.deleteById();
        //删除成功后，删除redis和rabbit中的博客
        if (delete) {
            Map<String, Object> map = new HashMap<>();
            map.put(SysConf.COMMAND, SysConf.DELETE);
            map.put(SysConf.BLOG_ID, id);
            map.put(SysConf.LEVEL, blog.getLevel());
            map.put(SysConf.OPERATE_TIME, blog.getOperateTime());
            rabbitTemplate.convertAndSend(SysConf.EXCHANGE_DIRECT, SysConf.ALEX_BLOG, map);
            //移除专题中包含该博客的信息
            // TODO: 2021/12/7 添加专题
            //删除所有评论
            List<String> list = new ArrayList<>(0);
            list.add(id);
            commentService.deleteBatchCommentByBlogIds(list);
        }
        return ResultUtil.resultErrorWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String deleteBatchBlog(List<String> blogIds) {
        if (blogIds == null || blogIds.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        String ids = blogIds.stream().collect(Collectors.joining(","));
        int isSave = blogMapper.deleteBatchIds(blogIds);
        //删除成功后，需要发送消息到redis和elasticsearch
        if (isSave == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put(SysConf.COMMAND, SysConf.DELETE_BATCH);
            map.put(SysConf.ID, ids);
            //发送到rabbitmq
            rabbitTemplate.convertAndSend(SysConf.EXCHANGE_DIRECT, SysConf.ALEX_BLOG, map);
            // TODO: 2021/12/23 移除专题 
            //删除博客下的评论
            commentService.deleteBatchCommentByBlogIds(blogIds);
        }
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String uploadLocalBlog(List<MultipartFile> blogInfo) throws IOException {
        SystemConfig systemConfig = systemConfigService.getConfig();
        if(systemConfig == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.SYSTEM_CONFIG_NOT_EXIST);
        }
        if (EOpenStatus.OPEN.getCode().equals(systemConfig.getUploadQiNiu()) && (StringUtils.isEmpty(systemConfig.getQiNiuAccessKey()) ||
                StringUtils.isEmpty(systemConfig.getQiNiuArea()) || StringUtils.isEmpty(systemConfig.getQiNiuBucket()) ||
                StringUtils.isEmpty(systemConfig.getQiNiuPictureBaseUrl()) || StringUtils.isEmpty(systemConfig.getQiNiuSecretKey()))) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PLEASE_SET_QI_NIU);

        }
        if (EOpenStatus.OPEN.getCode().equals(systemConfig.getUploadLocal()) && (StringUtils.isEmpty(systemConfig.getLocalPictureBaseUrl()) )) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PLEASE_SET_LOCAL);
        }
        List<MultipartFile> fileList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        //校验文件
        String fileOriginalName;
        for (MultipartFile file : blogInfo) {
            fileOriginalName = file.getOriginalFilename();
            if (FileUtils.isMarkdown(fileOriginalName)) {
                fileList.add(file);
                fileNameList.add(FileUtils.getFileName(fileOriginalName));
            } else {
                return ResultUtil.resultErrorWithMessage("目前仅支持Markdown文件!");
            }
        }
        if (fileList.isEmpty()) {
            return ResultUtil.resultErrorWithMessage("请选中要上传的文件！");
        }
        //文件解析
        List<String> fileContentList = new ArrayList<>();
// TODO: 2021/12/23 未完待续
        return null;
    }

    @Override
    public void deleteRedisByBlogSort() {
        //删除redis中博客分类下的博客数量
        redisUtils.delete(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.BLOG_COUNT_BY_SORT);
        //删除博客相关缓存
        deleteRedisByBlog();
    }

    @Override
    public void deleteRedisByBlogTag() {
        //删除redis中博客标签下的博客数量
        redisUtils.delete(RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.BLOG_COUNT_BY_TAG);
        //删除博客相关缓存
        deleteRedisByBlog();
    }

    @Override
    public void deleteRedisByBlog() {
        redisUtils.delete(RedisConf.HOT_BLOG);
        redisUtils.delete(RedisConf.NEW_BLOG);
        redisUtils.delete(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.FIRST.getCode());
        redisUtils.delete(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.SECOND.getCode());
        redisUtils.delete(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.THIRD.getCode());
        redisUtils.delete(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.FOURTH.getCode());
    }

    @Override
    public IPage<Blog> getBlogPageByLevel(Integer level, Long currentPage, Long currentPageSize, Integer useSort) {
        //从redis中获取内容
        String jsonResult = redisUtils.get(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + level);
        if (StringUtils.isNotEmpty(jsonResult)) {
            List<Blog> levelBlogList = JsonUtils.jsonToArrayList(jsonResult, Blog.class);
            IPage<Blog> page = new Page<>();
            page.setRecords(levelBlogList);
            return page;
        }
        String blogCount = sysParamsService.getSysParamsValueByKey(ELevel.values()[level].getValue());
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        if (StringUtils.isEmpty(blogCount)) {
            log.error(MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
        } else {
            page.setSize(Long.parseLong(blogCount));
        }
        IPage<Blog> blogPage = blogService.getBlogPageByLevel(page, level, useSort);
        List<Blog> blogList = blogPage.getRecords();
        //如果查询的是一二级推荐的时候，如果没有值，自动将top5的数据放入
        if ((Constants.NUM_ONE == level || Constants.NUM_TWO == level) && blogList.isEmpty()) {
            String blogHotCountStr = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_HOT_COUNT);
            String blogFirstCountStr = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_FIRST_COUNT);
            Long blogHotCount;
            Long blogFirstCount;
            if (StringUtils.isEmpty(blogHotCountStr) || StringUtils.isEmpty(blogFirstCountStr)) {
                log.error(MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
                return null;
            } else {
                blogHotCount = Long.parseLong(blogHotCountStr);
                blogFirstCount = Long.parseLong(blogFirstCountStr);
            }
            IPage<Blog> hotBlog = getHotBlog();
            List<Blog> hotBlogList = hotBlog.getRecords();
            List<Blog> firstBlogList = hotBlogList.subList(0, Math.toIntExact(blogFirstCount > blogHotCount ? blogHotCount : blogFirstCount));
            List<Blog> secondBlogList = hotBlogList.subList(Math.toIntExact(blogFirstCount), Math.toIntExact(blogHotCount));
            //设置redis，有效时间1小时
            if (firstBlogList.size() > 0) {
                redisUtils.setEx(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.FIRST.getCode(), JsonUtils.objectToJson(firstBlogList), 1, TimeUnit.HOURS);
            }
            if (secondBlogList.size() > 0) {
                redisUtils.setEx(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + ELevel.SECOND.getCode(), JsonUtils.objectToJson(secondBlogList), 1, TimeUnit.HOURS);
            }
            if (ELevel.FIRST.getCode().equals(level)) {
                blogList = firstBlogList;
            } else if (ELevel.SECOND.getCode().equals(level)) {
                blogList = secondBlogList;
            }
        }
        blogList = setTagPictureAndSortByBlogList(blogList);
        blogPage.setRecords(blogList);
        //设置数据到redis中，有效时间1小时
        if (blogList.size() > 0) {
            redisUtils.setEx(RedisConf.BLOG_LEVEL + RedisConf.SEGMENTATION + level, JsonUtils.objectToJson(blogList), 1, TimeUnit.HOURS);
        }
        return blogPage;
    }

    @Override
    public IPage<Blog> getHotBlog() {
        //从redis中获取内容
        String hotBlogJson = redisUtils.get(RedisConf.HOT_BLOG);
        if (StringUtils.isNotEmpty(hotBlogJson)) {
            List<Blog> hotBlogList = JsonUtils.jsonToArrayList(hotBlogJson);
            IPage<Blog> hotPage = new Page<>();
            hotPage.setRecords(hotBlogList);
            return hotPage;
        }
        String blogHotCountStr = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_HOT_COUNT);
        IPage<Blog> blogIPage = null;
        if (StringUtils.isEmpty(blogHotCountStr)) {
            log.error(MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
        } else {
            blogIPage = searchBlogByType(0L, Long.parseLong(blogHotCountStr), new HashMap<>(), SysConf.CLICK_COUNT);
            //保存热门博客列表到redis,有效时间为1小时
            if (blogIPage.getRecords() != null && !blogIPage.getRecords().isEmpty()) {
                redisUtils.setEx(RedisConf.HOT_BLOG, JsonUtils.objectToJson(blogIPage.getRecords()), 1, TimeUnit.HOURS);
            }
        }
        return blogIPage;
    }

    @Override
    public IPage<Blog> getNewBlog(Long currentPage, Long pageSize) {
        String blogNewCountStr = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_NEW_COUNT);
        IPage<Blog> blogPage = null;
        if (StringUtils.isEmpty(blogNewCountStr)) {
            log.error(MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
        } else {
            blogPage = searchBlogByType(currentPage, Long.parseLong(blogNewCountStr), new HashMap<>(), SysConf.CLICK_COUNT);
            //保存热门博客列表到redis,有效时间为1小时
            if (blogPage.getRecords() != null && !blogPage.getRecords().isEmpty()) {
                redisUtils.setEx(RedisConf.HOT_BLOG, JsonUtils.objectToJson(blogPage.getRecords()), 1, TimeUnit.HOURS);
            }
        }
        return blogPage;
    }

    @Override
    public IPage<Blog> getBlogBySearch(Long currentPage, Long currentSize) {
        return getNewBlog(currentPage, currentSize);
    }

    @Override
    public IPage<Blog> getBlogByTime(Long currentPage, Long currentSize) {
        return searchBlogByType(currentPage, currentSize, new HashMap<>(), null);
    }

    @Override
    public Integer getBlogPraiseCountById(String id) {
        int praiseCount = 0;
        if (StringUtils.isEmpty(id)) {
            log.error("传入id为空!");
            return praiseCount;
        }
        //从redis中取点赞数
        String praiseJson = redisUtils.get(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + id);
        if (StringUtils.isNotEmpty(praiseJson)) {
            praiseCount = Integer.parseInt(praiseJson);
        }
        return praiseCount;
    }

    @Override
    public String praiseBlogById(String id) {
        if (id == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        String userId;
        HttpServletRequest request = RequestHolder.getRequest();
        //如果用户登录
        if (request.getAttribute(SysConf.USER_ID) == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PLEASE_LOGIN_TO_PRISE);
        } else {
            userId = request.getAttribute(SysConf.USER_ID).toString();
            QueryWrapper<Comment> query = new QueryWrapper<>();
            query.eq(SysConf.USER_ID, userId).eq(SysConf.BLOG_ID, id).eq(SysConf.TYPE, ECommentType.PRAISE.getCode())
                    .last(SysConf.LIMIT_ONE);
            Comment praise = commentService.getOne(query);
            if (praise != null) {
                return ResultUtil.resultErrorWithMessage(MessageConf.YOU_HAVE_BEEN_PRISE);
            }
        }
        //更新博客点赞数
        Blog blog = blogService.getBlogById(id);
        int praiseCount = (blog.getPraiseCount() == null ? 0 : blog.getPraiseCount()) + 1;
        redisUtils.set(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + id, praiseCount + "");
        blog.setPraiseCount(praiseCount);
        blog.updateById();
        //插入评论数
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setBlogId(id);
        comment.setSource(ECommentSource.BLOG_INFO.getCode());
        comment.setType(ECommentType.PRAISE.getCode());
        comment.insert();
        return ResultUtil.resultSuccessWithData(blog.getCollectCount());
    }

    @Override
    public IPage<Blog> getSameBlogByTagId(Integer tagId) {
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put(SysConf.TAG_ID, tagId);
        return searchBlogByType(0L, 10L, eqMap, null);
    }

    @Override
    public IPage<Blog> getBlogListBySortId(Integer blogSortId, Long currentPage, Long currentPageSize) {
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put(SysConf.BLOG_SORT_ID, blogSortId);
        return searchBlogByType(currentPage, currentPageSize, eqMap, null);
    }

    @Override
    public Map<String, Object> getBlogByKeyword(String keyword, Long currentPage, Long currentPageSize) {
        currentPage = currentPage == null ? 1 : currentPage;
        currentPageSize = currentPageSize == null ? 10 : currentPageSize;
        QueryWrapper<Blog> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .select(item -> !item.getProperty().equals(SysConf.CONTENT))
                .orderByDesc(SysConf.CLICK_COUNT);
        String keywords = "";
        if (StringUtils.isNotEmpty(keyword)) {
            keywords = keyword.trim();
            String finalKeywords = keywords;
            query.and(wrapper -> wrapper.like(SysConf.TITLE, finalKeywords).or().like(SysConf.SUMMARY, finalKeywords))
                    .select(item -> !item.getProperty().equals(SysConf.CONTENT))
                    .orderByDesc(SysConf.CLICK_COUNT);
        }
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage).setSize(currentPageSize);
        Page<Blog> resPage = blogService.page(page, query);
        List<Blog> blogList = resPage.getRecords();
        List<String> blogSortIdList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        String finalKeywords1 = keywords;
        blogList.forEach(item -> {
            blogSortIdList.add(item.getBlogSortId());
            if (StringUtils.isNotEmpty(item.getFileId())) {
                sb.append(item.getFileId() + SysConf.FILE_SEGMENTATION);
            }
            //给标题和简介设置高亮
            if (StringUtils.isNotEmpty(finalKeywords1)) {
                item.setTitle(getHitCode(item.getTitle(), finalKeywords1));
                item.setSummary(getHitCode(item.getSummary(), finalKeywords1));
            }
        });
        Map<String, String> blogSortMap = null;
        if (blogSortIdList.size() > 0) {
            List<BlogSort> blogSortList = blogSortService.listByIds(blogSortIdList);
            blogSortMap = blogSortList.stream().collect(Collectors.toMap(BlogSort::getId, BlogSort::getSortName));
        }
        String pictureList = null;
        if (sb.length() > 0) {
            pictureList = pictureFeignClient.getPicture(sb.toString(), SysConf.FILE_SEGMENTATION);
        }
        List<Map<String, Object>> picList = webUtils.getPictureMap(pictureList);
        Map<String, String> pictureMap = picList.stream().collect(Collectors.toMap(item -> item.get(SysConf.ID).toString(), item -> item.get(SysConf.URL).toString()));
        //设置博客分类和图片
        if (blogSortMap != null || pictureMap != null) {
            Map<String, String> finalBlogSortMap = blogSortMap;
            blogList.forEach(item -> {
                //设置分类信息
                if (StringUtils.isNotEmpty(item.getBlogSortId())) {
                    String collect = StringUtils.splitLongByCode(item.getBlogSortId(), SysConf.FILE_SEGMENTATION).
                            stream().map(finalBlogSortMap::get).collect(Collectors.joining(","));
                    item.setBlogSortName(collect);
                }
                //设置图片信息
                if (StringUtils.isNotEmpty(item.getFileId())) {
                    List<String> fileIdList = StringUtils.splitStringByCode(item.getFileId(), SysConf.FILE_SEGMENTATION);
                    for(String fileId : fileIdList) {
                        //只设置一张标题图
                        if (pictureMap.get(fileId) != null) {
                            item.setPhotoUrl(pictureMap.get(fileId));
                            break;
                        }
                    }
                }
            });
        }
        HashMap<String, Object> map = new HashMap<>();
        //总记录数
        map.put(SysConf.TOTAL, resPage.getTotal());
        //返回总页数
        map.put(SysConf.TOTAL_PAGE, resPage.getPages());
        //当前页大小
        map.put(SysConf.PAGE_SIZE, currentPageSize);
        //当前页
        map.put(SysConf.CURRENT_PAGE, currentPage);
        //当前页数据
        map.put(SysConf.BLOG_LIST, blogList);
        return map;
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
        Map<String, Object> eqMqp = new HashMap<>();
        eqMqp.put(SysConf.TAG_ID, blogTagId);
        return searchBlogByType(currentPage, currentPageSize, eqMqp, null);
    }

    /**
     * @param currentPage
     * @param currentPageSize
     * @param eqMap  相等sql条件
     * @description: 根据类型分类查询
     * @author:      alex
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
     */
    public IPage<Blog> searchBlogByType(Long currentPage, Long currentPageSize, Map<String, Object> eqMap, String orderBy) {
        //设置分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage == null ? 1 : currentPage);
        page.setSize(currentPageSize == null ? 10 : currentPageSize);
        //设置查询条件
        QueryWrapper<Blog> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .orderByDesc(StringUtils.isEmpty(orderBy) ? SysConf.OPERATE_TIME : orderBy).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        //当传tag_id和blog_sort_id的时候，需要用in，因为这两个都是多标签、多分类的情况
        if (eqMap != null && !eqMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : eqMap.entrySet()) {
                switch (entry.getKey()) {
                    case SysConf.BLOG_SORT_ID:
                    case SysConf.TAG_ID:
                        query.in(entry.getKey(), Arrays.asList(entry.getValue().toString().split(",")));
                    default:
                        query.eq(entry.getKey(), entry.getValue());
                }
            }
        }
        IPage<Blog> pageList = blogService.page(page, query);
        List<Blog> blogList = pageList.getRecords();
        //设置分类、标签和图片
        blogList = setTagPictureAndSortByBlogList(blogList);
        pageList.setRecords(blogList);
        return pageList;
    }

    /**
     * @param currentPage
     * @param currentPageSize
     * @param eqMap
     * @param likeMap
     * @param orderList
     * @description:
     * @author:       alex
     * @return:       com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Blog>
    */
    private IPage<Blog> searchBlogByType(Long currentPage, Long currentPageSize, Map<String, Object> eqMap,  Map<String, Object> likeMap,  List<String> orderList) {
        //设置分页
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage == null ? 1 : currentPage);
        page.setSize(currentPageSize == null ? 10 : currentPageSize);
        //设置查询条件
        QueryWrapper<Blog> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode())
                .select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        //当传tag_id和blog_sort_id的时候，需要用in，因为这两个都是多标签、多分类的情况
        if (eqMap != null && !eqMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : eqMap.entrySet()) {
                switch (entry.getKey()) {
                    case SysConf.BLOG_SORT_ID:
                    case SysConf.TAG_ID:
                        query.in(entry.getKey(), Arrays.asList(entry.getValue().toString().split(",")));
                    default:
                        query.eq(entry.getKey(), entry.getValue());
                }
            }
        }
        if (likeMap != null && !likeMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : likeMap.entrySet()) {
                query.like(entry.getKey(), entry.getValue());
            }
        }
        if (orderList != null && !orderList.isEmpty()) {
            for (String order : orderList) {
                query.orderByDesc(order);
            }
        } else {
            //默认按照操作时间排序
            query.orderByDesc(SysConf.OPERATE_TIME);
        }
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
        Map<String, Object> eqMap = new HashMap<>();
        eqMap.put(SysConf.BLOG_SORT_ID, blogSortId);
        return searchBlogByType(currentPage, currentPageSize, eqMap, null);
    }

    @Override
    public IPage<Blog> searchBlogByAuthor(String author, Long currentPage, Long currentPageSize) {
        QueryWrapper<Blog> query = new QueryWrapper<>();
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(currentPageSize);
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .eq(SysConf.AUTHOR, author).orderByDesc(SysConf.OPERATE_TIME).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
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
        qeury.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.IS_PUBLISH, EPublish.PUBLISH.getCode())
                .orderByDesc(SysConf.OPERATE_TIME).select(Blog.class, i -> !i.getProperty().equals(SysConf.CONTENT));
        List<Blog> list = blogService.list(qeury);
        //将分类、标签和图片添加到博客中去
        list = blogService.setTagPictureAndSortByBlogList(list);
        Map<String, Blog> map = list.stream().collect(Collectors.toMap(item -> DateUtils.getTimeStr(item.getOperateTime(), "YYYY-MM-DD"), item -> item));
        //将数据缓存到redis中
        map.forEach((key, value) -> {
            redisUtils.set(SysConf.BLOG_SORT_BY_MONTH + RedisConf.SEGMENTATION + key, JsonUtils.objectToJson(value));
        });
        //将月份缓存到redis中
        redisUtils.set(SysConf.MONTH_SET, JsonUtils.objectToJson(map.keySet()));
        return map;
    }

    // TODO: 2021/12/2 测试getHitCode
    private static String getHitCode(String str, String keyword) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(keyword)) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        String start = "<span style = 'color:red'>";
        String end = "</span>";
        if (str.equals(keyword)) {
            sb.append(start);
            sb.append(str);
            sb.append(end);
        } else {
            String lowerCaseStr = str.toLowerCase();
            String lowerKeyword = keyword.toLowerCase();
            String[] lowerCaseArray = lowerCaseStr.split(lowerKeyword);
            for (int i = 0; i < lowerCaseArray.length; i++) {
                sb.append(lowerCaseArray[i]);
                if (i < lowerCaseArray.length - 1) {
                    sb.append(start);
                    sb.append(keyword);
                    sb.append(end);
                }
            }
        }

        return sb.toString();
    }

    // TODO: 2021/12/9 测试标题变红
    public static void main(String[] args) {
        System.out.println(getHitCode("abdbbb", "b"));
    }

    private String addVerdict(Integer count, Integer level) {
        if (level > 4) {
            return ResultUtil.resultErrorWithMessage("不能设置四级以上推荐");
        }
        //添加博客的时候进行判断
        switch (Objects.requireNonNull(ELevel.getByCode(level))) {
            case FIRST:
                String blogFirstCount = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_FIRST_COUNT);
                if (count > Long.parseLong(blogFirstCount)) {
                    return ResultUtil.resultErrorWithMessage("一级推荐不能超过" + blogFirstCount + "个");
                }
                break;
            case SECOND:
                String blogSecondCount = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_SECOND_COUNT);
                if (count > Long.parseLong(blogSecondCount)) {
                    return ResultUtil.resultErrorWithMessage("二级推荐不能超过" + blogSecondCount + "个");
                }
                break;
            case THIRD:
                String blogThirdCount = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_THIRD_COUNT);
                if (count > Long.parseLong(blogThirdCount)) {
                    return ResultUtil.resultErrorWithMessage("三级推荐不能超过" + blogThirdCount + "个");
                }
                break;
            case FOURTH:
                String blogFourthCount = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_FOURTH_COUNT);
                if (count > Long.parseLong(blogFourthCount)) {
                    return ResultUtil.resultErrorWithMessage("四级推荐不能超过" + blogFourthCount + "个");
                }
                break;
        }
        return null;
    }

    private void updateSolrAndRedis(Boolean isSave, Blog blog) {
        //保存文章，并且文章已经发布
        if (isSave && EPublish.PUBLISH.getCode().equals(blog.getIsPublish())) {
            Map<String, Object> map = new HashMap<>();
            map.put(SysConf.COMMAND, SysConf.ADD);
            map.put(SysConf.BLOG_ID, blog.getId());
            map.put(SysConf.LEVEL, blog.getLevel());
            map.put(SysConf.OPERATE_TIME, blog.getOperateTime());
            // TODO: 2021/12/23 了解发送到消息队列的目的
            rabbitTemplate.convertAndSend(SysConf.EXCHANGE_DIRECT, SysConf.ALEX_BLOG, map);
        } else if (EPublish.NO_PUBLISH.getCode().equals(blog.getIsPublish())) {
            Map<String, Object> map = new HashMap<>();
            map.put(SysConf.COMMAND, SysConf.EDIT);
            map.put(SysConf.BLOG_ID, blog.getId());
            map.put(SysConf.LEVEL, blog.getLevel());
            map.put(SysConf.OPERATE_TIME, blog.getOperateTime());
            rabbitTemplate.convertAndSend(SysConf.EXCHANGE_DIRECT, SysConf.ALEX_BLOG, map);
        }
    }



}