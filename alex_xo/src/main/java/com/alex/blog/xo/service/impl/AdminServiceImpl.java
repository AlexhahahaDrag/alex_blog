package com.alex.blog.xo.service.impl;

import com.alex.blog.base.service.impl.SuperServiceImpl;
import com.alex.blog.common.entity.Admin;
import com.alex.blog.xo.service.AdminService;
import com.alex.blog.xo.service.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {
}
