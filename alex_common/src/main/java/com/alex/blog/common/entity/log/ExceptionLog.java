package com.alex.blog.common.entity.log;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  异常日志类
 *author:       alex
 *createDate:   2021/9/27 21:24
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "t_exception_log")
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLog extends BaseEntity<ExceptionLog> {

    private static final long serialVersionUID = -4851055162892178225L;

    //操作ip
    private String ip;

    //ip来源
    private String ipSource;

    //方法
    private String method;

    //描述
    private String operation;

    //参数
    private String params;

    //异常对象json
    private String exceptionJson;

    //异常信息
    private String exceptionMessage;
}
