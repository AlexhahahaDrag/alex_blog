package com.alex.blog.common.vo.log;

import com.alex.blog.base.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:  系统日志视图
 *author:       alex
 *createDate:   2021/9/26 20:22
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogVo extends BaseVo<SysLogVo> {

    //操作用户名
    private String username;

    //操作人id
    private String adminId;

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

    //开始时间
    private String startTime;

    //花费时间
    private Long spendTime;

    //花费时间
    private String spendTimeStr;
}
