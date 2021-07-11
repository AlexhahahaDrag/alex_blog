package com.alex.blog.base.vo;

import lombok.Data;

/**
 *description:  pagevo用于分页
 *author:       alex
 *createDate:   2021/7/11 20:25
 *version:      1.0.0
 */
@Data
public class PageInfo<T> {

    //关键字
    private String keyword;

    //当前页
    private Long currentPage;

    //页大小
    private Long pageSize;
}
