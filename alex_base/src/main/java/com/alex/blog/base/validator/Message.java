package com.alex.blog.base.validator;

/**
 *description:  错误消息模板
 *author:       alex
 *createDate:   2021/7/11 20:36
 *version:      1.0.0
 */
public interface Message {

    //类内部使用，自定义reject value
    String CK_RANGE_MESSAGE_LENGTH_TYPE = "length must be between 0 and 11:%s";

    String CK_NUMERIC_TYPE = "field must be a number:%s";


    //注解默认
    String CK_NOT_BLACK_DEFAULT = "can not be blank";

    String CK_NOT_NULL_DEFAULT = "can not be null";

    String CK_NUMERIC_DEFAULT = "must be a number";

    String CK_RANGE_DEFALUT = "should be an integer, between {min} and {max}";

    String PAGE_NOT_NULL = "page can not be null";

    String SIEZ_NOT_NULL = "size can not be null";

    String ID_LENGTH_THIRTY_TWO = "length must be 32";
}
