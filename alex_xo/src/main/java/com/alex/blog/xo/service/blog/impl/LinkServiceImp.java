package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.common.entity.blog.Link;
import com.alex.blog.xo.blog.mapper.LinkMapper;
import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.xo.service.blog.LinkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author alex
 * @since 2022-01-27 17:32:37
 */
@Service
public class LinkServiceImp extends SuperServiceImpl<LinkMapper, Link> implements LinkService {

}
