package com.alex.blog.base.service.impl;

import com.alex.blog.base.mapper.SuperMapper;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class SuperServiceImpl <M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {
}
