package com.alex.blog.picture.service.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.file.FileSort;
import com.alex.blog.picture.mapper.FileSortMapper;
import com.alex.blog.picture.service.FileSortService;
import org.springframework.stereotype.Service;

/**
 *description:  文件分类实现类
 *author:       alex
 *createDate:   2021/8/10 7:31
 *version:      1.0.0
 */
@Service
public class FileSortServiceImpl extends SuperServiceImpl<FileSortMapper, FileSort> implements FileSortService {
}
