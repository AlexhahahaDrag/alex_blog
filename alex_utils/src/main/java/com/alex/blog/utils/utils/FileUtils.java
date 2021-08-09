package com.alex.blog.utils.utils;

import lombok.extern.slf4j.Slf4j;

/**
 *description:  文件工具类
 *author:       alex
 *createDate:   2021/8/10 6:48
 *version:      1.0.0
 */
@Slf4j
public class FileUtils {


    /**
     * @param fileName
     * @description:  获取图后缀名
     * @author:       alex
     * @return:       java.lang.String
    */
    public static String getPicExpandedName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        String ex = "";
        if (StringUtils.isNotBlank(fileName) && StringUtils.contains(fileName, ".")) {
            ex = StringUtils.substring(fileName, fileName.indexOf(".") + 1);
        }
        return StringUtils.isEmpty(ex) ? "jpg" : ex.toLowerCase();
    }
}
