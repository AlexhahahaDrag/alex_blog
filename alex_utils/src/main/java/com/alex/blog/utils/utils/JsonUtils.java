package com.alex.blog.utils.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    /**
     * @param obj
     * @description:  将object转换成map
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> objectToMap(Object obj) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(obj);
            return jsonToMap(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonString
     * @param clazz
     * @description:  将json字符串转化成对象
     * @author:       alex
     * @return:       java.lang.Object
     */
    public static Object jsonToObject(String jsonString, Class<?> clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Object obj = null;
        try {
            obj = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @param jsonArray
     * @description:  将json转换成arrayList
     * @author:       alex
     * @return:       java.util.ArrayList<?>
     */
    public static ArrayList<?> jsonToArrayList(String jsonArray) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                //设置导出空值
                .serializeNulls()
                //忽略策略
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();
        ArrayList<?> list = null;
        try {
            Type type = new TypeToken<ArrayList<?>>() {}.getType();
            list = gson.fromJson(jsonArray, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param json
     * @description:  将json转化成map<String, Object>
     * @author:       alex
     * @return:       java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> jsonToMap(String json) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param map
     * @param beanType
     * @description:  将map转化成pojo
     * @author:       alex
     * @return:       T
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> beanType) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        T pojo = null;
        try {
            JsonElement jsonElement = gson.toJsonTree(map);
            pojo = gson.fromJson(jsonElement, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pojo;
    }

    /**
     * @param jsonData
     * @param beanType
     * @description:  将json转化成对象
     * @author:       alex
     * @return:       T
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        T t = null;
        try {
            t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * @param jsonData
     * @param beanType
     * @description:  将json数据转化成pojo的list
     * @author:       alex
     * @return:       java.util.List<T>
    */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = null;
        try {
            list = MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> Map<String, Object> pojoToMap(T t) {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = t.getClass().getMethods();
        try {
            for (Method method : methods) {
                Class<?>[] paramClass = method.getParameterTypes();
                //如果方法带参数则跳过
                if (paramClass.length > 0) {
                    continue;
                }
                String name = method.getName();
                if (name.startsWith("get")) {
                    Object value = method.invoke(t);
                    map.put(name, value);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
