package com.alex.blog.xo.service.sys.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.Constants;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Blog;
import com.alex.blog.common.entity.blog.BlogSort;
import com.alex.blog.common.entity.blog.Link;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.common.entity.sys.WebVisit;
import com.alex.blog.common.enums.EBehavior;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.sys.WebVisitVo;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.mapper.sys.WebVisitMapper;
import com.alex.blog.xo.service.blog.BlogService;
import com.alex.blog.xo.service.blog.BlogSortService;
import com.alex.blog.xo.service.blog.LinkService;
import com.alex.blog.xo.service.blog.TagService;
import com.alex.blog.xo.service.sys.WebVisitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Web访问记录表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-26 17:27:16
 */
@Service
public class WebVisitServiceImp extends SuperServiceImpl<WebVisitMapper, WebVisit> implements WebVisitService {

    @Autowired
    private WebVisitMapper webVisitMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private TagService tagService;

    @Autowired
    private LinkService linkService;

    @Override
    public void addWebVisit(String adminId, HttpServletRequest request, String moduleId, String otherData) {
        Map<String, String> osAndBrowserInfo = IpUtils.getOsAndBrowserInfo(request);
        String os = osAndBrowserInfo.get("OS");
        String broswer = osAndBrowserInfo.get("BROSWER");
        String ip = IpUtils.getIpAddr(request);
        WebVisit webVisit = new WebVisit();
        webVisit.setIp(ip);
        webVisit.setOs(os);
        webVisit.setBehavior(broswer);
        webVisit.setBehavior(broswer);
        webVisit.setAdminId(adminId);
        webVisit.setModuleId(moduleId);
        webVisit.setOtherData(otherData);
        //从redis中获取ip resource
        String jsonResult = redisUtils.get(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isNotEmpty(jsonResult)) {
            webVisit.setIpSource(jsonResult);
        } else {
            String addresses = IpUtils.getAddresses("ip=" + ip, "utf-8");
            if (StringUtils.isNotEmpty(addresses)) {
                webVisit.setIpSource(addresses);
                redisUtils.set(RedisConf.IP_SOURCE + RedisConf.SEGMENTATION + ip, addresses);
            }
        }
        webVisit.insert();
    }

    @Override
    public Integer getWebVisitCount() {
        String startTime = DateUtils.getNowTimeStrStartTime();
        String endTime = DateUtils.getNowTimeStr();
        //获取今日访问量
        return webVisitMapper.getIpCount(startTime, endTime);
    }

    @Override
    public Map<String, Object> getVisitByWeek() {
        //从redis中获取一周访问量
        String key = RedisConf.DASHBOARD + RedisConf.SEGMENTATION + RedisConf.WEEK_VISIT;
        String weekVisitResult = redisUtils.get(key);
        if (StringUtils.isNotEmpty(weekVisitResult)) {
            return JsonUtils.jsonToMap(weekVisitResult);
        }
        //当前日期
        String todayEndTime = DateUtils.getNowTimeStr();
        //七天前日期
        String sevenTimeAgo = DateUtils.getToDayStartTimeMinusNDay(LocalDateTime.now(), 6);
        List<Integer> pvList = webVisitMapper.getPvByWeek(sevenTimeAgo, todayEndTime);
        List<Integer> uvList = webVisitMapper.getUvByWeek(sevenTimeAgo, todayEndTime);
        List<String> dateList = DateUtils.getDaysBetweenDates(sevenTimeAgo, todayEndTime);
        Map<String, Object> result = new HashMap<>(Constants.NUM_THREE);
        result.put("date", dateList);
        result.put("pv", pvList);
        result.put("uv", uvList);
        //设置redis10分钟过期
        redisUtils.setEx(key, JsonUtils.objectToJson(result), 10, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public IPage<WebVisit> getPageList(WebVisitVo webVisitVo) {
        QueryWrapper<WebVisit> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).orderByDesc(SysConf.OPERATE_TIME);
        //得到所有枚举类
        EBehavior[] behaviors = EBehavior.values();
        //设置时间段查询
        if (StringUtils.isNotEmpty(webVisitVo.getStartTime())) {
            query.ge(SysConf.OPERATE_TIME, webVisitVo.getStartTime());
        }
        //设置时间段查询
        if (StringUtils.isNotEmpty(webVisitVo.getEndTime())) {
            query.lt(SysConf.OPERATE_TIME, webVisitVo.getEndTime());
        }
        Page<WebVisit> page = new Page<>();
        page.setCurrent(webVisitVo.getCurrentPage());
        page.setSize(webVisitVo.getPageSize());

        Page<WebVisit> pageList = this.page(page, query);
        List<WebVisit> list = pageList.getRecords();
        List<String> blogIds = new ArrayList<>();
        List<String> blogOids = new ArrayList<>();
        List<String> tagIds = new ArrayList<>();
        List<String> sortIds = new ArrayList<>();
        List<String> linkIds = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.forEach(item -> {
                switch (EBehavior.getByCode(item.getBehavior())) {
                    case BLOG_CONTNET:
                    case BLOG_PRAISE:
                        if (StringUtils.isNotEmpty(item.getModuleId())) {
                            blogIds.add(item.getModuleId());
                        } else if (StringUtils.isNotEmpty(item.getOtherData())) {
                            blogOids.add(item.getOtherData());
                        }
                        break;
                    case BLOG_SORT:
                    case VISIT_CLASSIFY:
                        if (StringUtils.isNotEmpty(item.getModuleId())) {
                            sortIds.add(item.getModuleId());
                        }
                        break;
                    case BLOG_TAG:
                    case VISIT_TAG:
                        if (StringUtils.isNotEmpty(item.getModuleId())) {
                            tagIds.add(item.getModuleId());
                        }
                        break;
                    case FRIENDSHIP_LINK:
                        if (StringUtils.isNotEmpty(item.getModuleId())) {
                            linkIds.add(item.getModuleId());
                        }
                        break;
                }
            });
            Map<Long, String> contentMap = new HashMap<>();
            if (blogIds.size() > 0) {
                List<Blog> blogList = blogService.listByIds(blogIds);
                blogList.forEach(item -> {
                    contentMap.put(item.getId(),  item.getTitle());
                });
            }
            if (blogOids.size() > 0) {
                QueryWrapper<Blog> queryBlog = new QueryWrapper<>();
                queryBlog.in(SysConf.OID, blogOids);
                List<Blog> blogList = blogService.list(queryBlog);
                blogList.forEach(item -> {
                    contentMap.put(item.getOid(),  item.getTitle());
                });
            }
            if (tagIds.size() > 0) {
                List<Tag> tagList = tagService.listByIds(tagIds);
                tagList.forEach(item -> {
                    contentMap.put(item.getId(),  item.getContent());
                });
            }
            if (sortIds.size() > 0) {
                List<BlogSort> blogSortList = blogSortService.listByIds(sortIds);
                blogSortList.forEach(item -> {
                    contentMap.put(item.getId(),  item.getSortName());
                });
            }
            if (linkIds.size() > 0) {
                List<Link> linkList = linkService.listByIds(linkIds);
                linkList.forEach(item -> {
                    contentMap.put(item.getId(),  item.getTitle());
                });
            }
            list.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getBehavior())) {
                    item.setBehaviorName(EBehavior.getByCode(item.getBehavior()).getValue());
                }
                switch (EBehavior.getByCode(item.getBehavior())) {
                    case BLOG_CONTNET:
                    case BLOG_PRAISE:
                    case BLOG_SORT:
                    case VISIT_CLASSIFY:
                    case BLOG_TAG:
                    case VISIT_TAG:
                    case FRIENDSHIP_LINK:
                        if (StringUtils.isNotEmpty(item.getModuleId())) {
                            item.setContent(contentMap.get(item.getModuleId()));
                        } else if (StringUtils.isNotEmpty(item.getOtherData())) {
                            item.setContent(contentMap.get(item.getOtherData()));
                        }
                        break;
                    default:
                        if (StringUtils.isNotEmpty(item.getOtherData())) {
                            item.setContent(contentMap.get(item.getOtherData()));
                        }
                }
            });
        }
        pageList.setRecords(list);
        return pageList;
    }
}
