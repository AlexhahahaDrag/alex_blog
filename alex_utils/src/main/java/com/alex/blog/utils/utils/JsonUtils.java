package com.alex.blog.utils.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


/**
 *description:  json解析工具类
 * Gson 是 Google 提供的用来在 Java 对象和 JSON 数据之间进行映射的 Java 类库。可以将一个 JSON 字符串转成一个 Java 对象，或者反过来。
 *author:       alex
 *createDate:   2021/1/25 21:23
 *version:      1.0.0
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**ma
     * 
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
