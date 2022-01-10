package com.alex.blog.utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *description:  正则表达式工具类
 *author:       alex
 *createDate:   2022/1/7 7:07
 *version:      1.0.0
 */
public class RegexUtils {

    /**
     * @param target
     * @param regex
     * @description:  根据正则表达式解析字符串
     * @author:       alex
     * @return:       List<String>
    */
    public static List<String> match(String target, String regex) {
        if (StringUtils.isEmpty(target)) {
            return null;
        }
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(target);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * @param target
     * @param regex
     * @description:  校验目标字符串是否存在正则
     * @author:       alex
     * @return:       boolean
    */
    public static boolean checkByRegex(String target, String regex) {
        if (StringUtils.isEmpty(target)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
