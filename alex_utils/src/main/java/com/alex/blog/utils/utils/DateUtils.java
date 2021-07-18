package com.alex.blog.utils.utils;

import lombok.extern.slf4j.Slf4j;

/**
 *description:  时间工具类
 *author:       alex
 *createDate:   2021/7/19 7:10
 *version:      1.0.0
 */
@Slf4j
public class DateUtils {

    public static final String STARTTIME = " 00:00:00";

    public static final String ENDTIME = " 23:59:59";

    public static final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public static final String[] REPLACE_STRING = new String[] {"GMT+0800", "GMT+08:00"};

    public static final String SPLIT_STRING = "(中国标准时间)";

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {

    }
}
