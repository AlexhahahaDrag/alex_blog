package com.alex.blog.utils.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *description:
 * 什么是Protocol Buffer？
 * Protocol Buffer是谷歌出品的一种数据交换格式，独立于语言和平台，类似于json。Google提供了多种语言的实现：java、c++、go和python。对象序列化城Protocol Buffer之后可读性差，但是相比xml，json，它占用小，速度快。适合做数据存储或 RPC 数据交换格式。
 * Java序列化库 - Protostuff
 * 相对我们常用的json来说，Protocol Buffer门槛更高，因为需要编写.proto文件，再把它编译成目标语言，这样使用起来就很麻烦。但是现在有了protostuff之后，就不需要依赖.proto文件了，他可以直接对POJO进行序列化和反序列化，使用起来非常简单。
 *author:       alex
 *createDate:   2021/7/2 6:30
 *version:      1.0.0
 */
public class ProtoStuffSerializerUtil {

    public static final int BUFFER_SIZE = 1024 * 1024;
    /**
     * 避免每次序列化都重新申请Buffer空间
     */
    private static LinkedBuffer buffer = LinkedBuffer.allocate(BUFFER_SIZE);
    /**
     * 缓存Schema
     */
    private static Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    /**
     * @param obj
     * @description:  序列化对象
     * @author:       alex
     * @return:       byte[]
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException("序列化对象(" + obj + ")参数异常!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        byte[] protoStuff;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * @param paarmArrayOfByte
     * @param targetClass
     * @description:  反序列化对象
     * @author:       alex
     * @return:       T
     */
    public static <T> T deserialize(byte[] paarmArrayOfByte, Class<T> targetClass) {
        if(paarmArrayOfByte == null || paarmArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();
        }catch (InstantiationException e1) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e1);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paarmArrayOfByte, instance, schema);;
        return instance;
    }

    /**
     * @param objList
     * @description:  序列化列表
     * @author:       alex
     * @return:       byte[]
     */
    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new RuntimeException("序列化对象列表(" + objList + ")参数异常!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        byte[] protoStuff;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            protoStuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表(" + objList + ")发生异常!", e);
        } finally {
            buffer.clear();
            try {
                if(bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return protoStuff;
    }

    /**
     * @param paramArrayOfByte
     * @param targetClass
     * @description:  反序列化列表
     * @author:       alex
     * @return:       java.util.List<T>
    */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        List<T> result;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        }
        catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!", e);
        }
        return result;
    }
}
