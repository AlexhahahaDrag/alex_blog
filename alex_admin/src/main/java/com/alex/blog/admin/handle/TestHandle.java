package com.alex.blog.admin.handle;

import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;


public class TestHandle extends MybatisParameterHandler {


    public TestHandle(MappedStatement mappedStatement, Object parameter, BoundSql boundSql) {
        super(mappedStatement, parameter, boundSql);
    }
}
