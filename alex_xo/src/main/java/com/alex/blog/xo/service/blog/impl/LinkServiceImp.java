package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.enums.EStatus;
import com.alex.blog.base.global.RedisConf;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Link;
import com.alex.blog.common.enums.ELinkStatus;
import com.alex.blog.common.feign.PictureFeignClient;
import com.alex.blog.common.global.MessageConf;
import com.alex.blog.common.global.SysConf;
import com.alex.blog.common.vo.blog.LinkVo;
import com.alex.blog.utils.utils.*;
import com.alex.blog.xo.mapper.blog.LinkMapper;
import com.alex.blog.xo.service.blog.LinkService;
import com.alex.blog.xo.utils.WebUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-27 17:32:37
 */
@Service
@Slf4j
public class LinkServiceImp extends SuperServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RabbitMqUtils rabbitMqUtils;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    @Autowired
    private WebUtils webUtils;

    @Override
    public List<Link> getListByPageSize(Integer pageSize) {
        QueryWrapper<Link> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode()).eq(SysConf.LINK_STATUS, ELinkStatus.PUBLISH.getCode())
                .orderByDesc(SysConf.SORT);
        Page<Link> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(1l);
        return this.page(page, query).getRecords();
    }

    @Override
    public IPage<Link> getPageList(LinkVo linkVo) {
        QueryWrapper<Link> query = new QueryWrapper<>();
        query.eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        if (StringUtils.isNotEmpty(linkVo.getKeyword()) && StringUtils.isNotEmpty(linkVo.getKeyword().trim())) {
            query.like(SysConf.TITLE, linkVo.getKeyword().trim());
        }
        if (StringUtils.isNotEmpty(linkVo.getLinkStatus())) {
            query.eq(SysConf.LINK_STATUS, linkVo.getLinkStatus());
        }
        if (linkVo.getOrderColumn() != null && !linkVo.getOrderColumn().isEmpty()) {
            for (Map.Entry<String, String> entry : linkVo.getOrderColumn().entrySet()) {
                switch (entry.getValue()) {
                    case "desc":
                        query.orderByDesc(entry.getKey());
                        break;
                    case "asc" :
                        query.orderByAsc(entry.getKey());
                }
            }
        } else {
            query.orderByDesc(SysConf.SORT);
        }
        Page<Link> page = new Page<>();
        page.setSize(linkVo.getPageSize());
        page.setCurrent(linkVo.getCurrentPage());
        Page<Link> pageList = this.page(page, query);
        List<Link> records = pageList.getRecords();
        String fileIds = records.stream().map(Link::getFileId).collect(Collectors.joining(SysConf.FILE_SEGMENTATION));
        if (StringUtils.isNotEmpty(fileIds)) {
            String picture = pictureFeignClient.getPicture(fileIds, SysConf.FILE_SEGMENTATION);
            List<Map<String, Object>> pictureList = webUtils.getPictureMap(picture);
            if (pictureList != null && !pictureList.isEmpty()) {
                Map<String, String> pictureMap = pictureList.stream().collect(Collectors.toMap(item -> item.get(SysConf.ID).toString(), item -> item.get(SysConf.URL).toString()));
                records.forEach(item -> {
                    //获取图片
                    if (StringUtils.isNotEmpty(item.getFileId())) {
                        List<String> fileIdList = StringUtils.splitStringByCode(item.getFileId(), SysConf.FILE_SEGMENTATION);
                        List<String> collect = fileIdList.stream().map(id -> pictureMap.get(id)).collect(Collectors.toList());
                        item.setPhotoList(collect);
                    }
                });
            }
        }
        return pageList;
    }

    @Override
    public String addLink(LinkVo linkVo) {
        Link link = new Link();
        BeanUtils.copyProperties(linkVo, link);
        link.insert();
        if (StringUtils.isNotEmpty(linkVo.getEmail()) && CheckUtils.checkEmail(linkVo.getEmail())
                && ELinkStatus.PUBLISH.getCode().equals(link.getLinkStatus())) {
            log.info("发送友链申请通过的邮件通知");
            String linkApplyText =  "<a href=\" " + link.getUrl() + "\">" + link.getTitle() + "</a> 站长，您申请的友链已经成功上架~";
            rabbitMqUtils.sendSimpleEmail(linkVo.getEmail(), linkApplyText);
        }
        deleteRedisBlogLinkList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editLink(LinkVo linkVo) {
        Link link = this.getById(linkVo.getId());
        if (link == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        BeanUtils.copyProperties(linkVo, link);
        link.updateById();
        //友链从申请状态到发布状态，需要发送邮箱到站长邮箱
        if (StringUtils.isNotEmpty(linkVo.getEmail()) && CheckUtils.checkEmail(linkVo.getEmail())
                && ELinkStatus.APPLY.getCode().equals(link.getLinkStatus()) && ELinkStatus.PUBLISH.getCode().equals(linkVo.getLinkStatus())) {
            log.info("发送友链申请通过的邮件通知");
            String linkApplyText =  "<a href=\" " + link.getUrl() + "\">" + link.getTitle() + "</a> 站长，您申请的友链已经成功上架~";
            rabbitMqUtils.sendSimpleEmail(linkVo.getEmail(), linkApplyText);
        }
        //删除redis中的友链信息
        deleteRedisBlogLinkList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String deleteBatchLink(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        Link link = new Link();
        link.setId(ids.get(0));
        linkMapper.deleteByIdWithFill(link);
        //删除redis中的博客连接
        deleteRedisBlogLinkList();
        return ResultUtil.resultSuccessWithMessage(MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickLink(LinkVo linkVo) {
        Link link = this.getById(linkVo.getId());
        if (link == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        QueryWrapper<Link> qeury = new QueryWrapper<>();
        qeury.orderByDesc(SysConf.SORT).eq(SysConf.STATUS, EStatus.ENABLE.getCode());
        Link one = this.getOne(qeury);
        link.setSort(one.getSort() == null ? 1 : one.getSort() + 1);
        link.updateById();
        //删除redis中的博客连接
        deleteRedisBlogLinkList();
        return ResultUtil.resultSuccessWithMessage("置顶成功!");
    }

    @Override
    public String addLinkCount(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        Link link = this.getById(id);
        if (link == null) {
            return ResultUtil.resultErrorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        link.setClickCount(link.getClickCount() + 1);
        link.updateById();
        return ResultUtil.resultSuccessWithData(MessageConf.UPDATE_SUCCESS);
    }

    /**
     * @description: 删除redis中的博客友链
     * @author:      alex
     * @createDate:  2022/1/28 9:54
     * @return:      void
     */
    private void deleteRedisBlogLinkList() {
        Set<String> keys = redisUtils.keys(RedisConf.BLOG_LINK + RedisConf.SEGMENTATION + "*");
        redisUtils.delete(keys);
    }
}
