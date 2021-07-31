package com.alex.blog.base.vo;

import com.alex.blog.base.validator.annotion.IdValid;
import com.alex.blog.base.validator.group.Delete;
import com.alex.blog.base.validator.group.Update;
import lombok.Data;

/**
 *description:  vo父类
 *author:       alex
 *createDate:   2021/7/11 20:24
 *version:      1.0.0
 */
@Data
public class BaseVo<T> extends PageInfo<T> {

    @IdValid(groups = {Update.class, Delete.class})
    private String id;

    private Integer status;
}
