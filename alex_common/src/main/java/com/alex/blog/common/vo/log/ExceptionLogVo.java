package com.alex.blog.common.vo.log;

import com.alex.blog.base.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *description:
 *author:       alex
 *createDate:   2021/9/27 21:24
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLogVo extends BaseVo<ExceptionLogVo> {

    //ip
    private String ip;

    //ip来源
    private String ipSource;

    //请求方法
    private String method;

    //描述
    private String operation;

    //请求参数
    private String params;

    //异常json
    private String exceptionJson;

    //异常信息
    private String exceptionMessage;

    //开始时间
    private String startTime;
}
