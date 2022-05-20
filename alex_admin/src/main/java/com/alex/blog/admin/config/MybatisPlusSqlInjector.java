package com.alex.blog.admin.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;

import java.util.List;

public class MybatisPlusSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        final List<AbstractMethod> methods = super.getMethodList(mapperClass);

        methods.add(new LogicDeleteByIdWithFill());

        return methods;
    }
}



