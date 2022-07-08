package com.alex.blog.common.entity.log;

import com.alex.blog.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  操作日志记录表
 *author:       alex
 *createDate:   2021/9/26 20:01
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "t_sys_log")
@AllArgsConstructor
@NoArgsConstructor
public class SysLog extends BaseEntity<SysLog> {

    /**
     *
     */
    private static final long serialVersionUID = -4851055162892178225L;

    //操作用户名
    private String username;

    //操作人id
    private Long adminId;

    //请求ip
    private String ip;

    //ip来源
    private String ipSource;

    //请求地址
    private String url;

    //请求类型
    private String type;

    //请求路径
    private String classPath;

    //请求方法
    private String method;

    //请求参数
    private String params;

    //描述
    private String operation;

    //花费时间
    private Long spendTime;
}
