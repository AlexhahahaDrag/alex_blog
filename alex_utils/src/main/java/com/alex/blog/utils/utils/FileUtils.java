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

    /*
     * @param fileName
     * @description:  校验文件是否是markdown
     * @author:       alex
     * @return:       boolean
    */
    public static boolean isMarkdown(String fileName) {
        if (StringUtils.isEmpty(fileName)){
            return false;
        }
        return fileName.contains(".md");
    }

    // TODO: 2021/12/23 添加markdown转html方法
    public static String markdownToHtml(String markdown) {
        //new MutableDa
        return "";
    }

    public static String getFileName(String fileOriginalName) {
        if (StringUtils.isNotBlank(fileOriginalName) && StringUtils.contains(fileOriginalName, ".")) {
            return fileOriginalName.substring(0, fileOriginalName.indexOf("."));
        }
        return "";

    }
}
