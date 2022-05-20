package com.alex.blog.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *description:  mapper父类
 *author:       alex
 *createDate:   2021/7/8 7:34
 *version:      1.0.0
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    int deleteByIdWithFill(T entity);
}
