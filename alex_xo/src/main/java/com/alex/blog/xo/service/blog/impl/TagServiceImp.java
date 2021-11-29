package com.alex.blog.xo.service.blog.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.blog.Tag;
import com.alex.blog.xo.mapper.blog.TagMapper;
import com.alex.blog.xo.service.blog.TagService;
import org.springframework.stereotype.Service;

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

}
