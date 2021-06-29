package com.alex.blog.utils.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *description:  统一返回类
 *author:       alex
 *createDate:   2021/1/20 20:50
 *version:      1.0.0
 */
public class ResultUtil {

    private static final String CODE = "code";

    private static final String SUCESS = "success";

    private static final String ERROR = "error";

    private static final String DATA = "data";

    private static final String MESSAGE = "message";

    private static final int NUM_TWO = 2;


    public static String result(Object code, Object data) {
        return resultWithData(code, data);
    }

    public static String resultWithData(Object code, Object data) {
        Map<Object, Object> map = new HashMap<>(NUM_TWO);
        map.put(CODE, code);
        map.put(DATA, data);
        return JsonUtils.objectToJson(map);
    }

    public static String resultWithMessage(Object code, String message) {
        Map<Object, Object> map = new HashMap<>();
        map.put(CODE, code);
        map.put(MESSAGE, message);
        return JsonUtils.objectToJson(map);
    }
}
