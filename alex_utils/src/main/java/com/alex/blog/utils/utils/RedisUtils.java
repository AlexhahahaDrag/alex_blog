package com.alex.blog.utils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *description:  redis工具类
 *author:       alex
 *createDate:   2021/6/6 15:15
 *version:      1.0.0
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //string相关操作
    /**
     * @param key    key
     * @description:  删除key
     * @author:       alex
     * @return:       void
     */

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /*
     * @param keys    关键字集合
     * @description:    批量删除key
     * @author:       alex
     * @return:
     */

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * @param key
     * @description:  序列化key
     * @author:       alex
     * @return:       byte[]
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * @param key
     * @description:  是否存在key
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @param timeout
     * @param timeUnit
     * @description:  设置过期时间
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * @param key
     * @param date
     * @description:  设置过期时间
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * @param pattern
     * @description:  查找匹配的key
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * @param key
     * @param dbIndex
     * @description:  将当前数据库的key移动到指定的数据库db中去
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * @param key
     * @description:  移除key的过期时间，key将永久保持
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * @param key
     * @description:  返回key剩余的过期时间
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @param key
     * @param timeUnit
     * @description:  返回key剩余的过期时间
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * @description:  从当前数据库中随机返回一个key
     * @author:       alex
     * @return:       java.lang.String
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /*
     * @param oldKey
     * @param newkey
     * @description: 修改key的名称
     * @author:       alex
     * @return:       void
     */
    public void rename(String oldKey, String newkey) {
        redisTemplate.rename(oldKey, newkey);
    }

    /**
     * @param oldKey
     * @param newKey
     * @description: 仅当newKey不存在时，将oldKey改名为newKey
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * @param key
     * @description:  返回key值所储存的类型
     * @author:       alex
     * @return:       org.springframework.data.redis.connection.DataType
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * @param key
     * @param value
     * @description:  设置指定key的值
     * @author:       alex
     * @return:       void
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key
     * @description:  获取key的值
     * @author:       alex
     * @return:       java.lang.String
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  获取key中字符串值的子字符串
     * @author:       alex
     * @return:       java.lang.String
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * @param key
     * @param value
     * @description:  将给定的key的值设置为value，并返回key的旧值
     * @author:       alex
     * @return:       java.lang.String
     */
    public String getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * @param key
     * @param offset
     * @description:  对key的值获取指定偏移量上的位
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * @param keys
     * @description:  批量获取key值对应的value
     * @author:       alex
     * @return:       java.util.List<java.lang.String>
     */
    public List<String> muliGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * @param key
     * @param offset
     * @param value
     * @description:  将key值对应的value值的第offset位转化为value值（true 1， false 0）
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @description:  设置key的值为value，并将key的过期时间设置为timeout
     * @author:       alex
     * @return:       void
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * @param key
     * @param value
     * @description:  如果不存在key，设置key的值为vaue
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * @param key
     * @param value
     * @param offset
     * @description:  将key的值从offset开始覆写
     * @author:       alex
     * @return:       void
     */
    public void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * @param key
     * @description:  获取value字符串的长度
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * @param maps
     * @description:  批量添加key,value
     * @author:       alex
     * @return:       void
     */
    public void mutiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * @param maps
     * @description:  如果不存在批量添加key，value
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean mitiSetIfAbsent(Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * @param key
     * @param increment
     * @description:  增长increment
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @param key
     * @param increment
     * @description:  增长increment
     * @author:       alex
     * @return:       java.lang.Double
     */
    public Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @param key
     * @param value
     * @description:  将value值添加到末尾
     * @author:       alex
     * @return:       java.lang.Integer
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }


    //hash相关操作
    /**
     * @param key
     * @param field
     * @description:  获取哈希表key的field字段对应的值
     * @author:       alex
     * @return:       java.lang.Object
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * @param key
     * @description:  获取哈希表key的所有键值对
     * @author:       alex
     * @return:       java.util.Map<java.lang.Object,java.lang.Object>
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key
     * @param fields
     * @description:  获取哈希表key中所有给定字段的值
     * @author:       alex
     * @return:       java.util.List<java.lang.Object>
     */
    public List<Object> hMutiGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @description:  设置哈希表keyhashKey为value
     * @author:       alex
     * @return:       void
     */
    public void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * @param key
     * @param maps
     * @description:  批量往哈希表key中添加值
     * @author:       alex
     * @return:       void
     */
    public void hPutAll(String key, Map<String, Object> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @description:  当哈希表key的hashKey不存在的时候，才设置value
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean hPutIfAbsent(String key, String hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * @param key
     * @param fields
     * @description:  删除一个或多个哈希表key的字段
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * @param key
     * @param field
     * @description:  查看哈希表key中指定字段是否存在
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * @param key
     * @param field
     * @param increment
     * @description:  为哈希表key的field字段增加increment
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * @param key
     * @param field
     * @param incremnt
     * @description:  为哈希表key的field字段增加increment
     * @author:       alex
     * @return:       java.lang.Double
     */
    public Double hIncrByFloat(String key, Object field, double incremnt) {
        return redisTemplate.opsForHash().increment(key, field, incremnt);
    }

    /**
     * @param key
     * @description:  获取哈希表key的所有key字段
     * @author:       alex
     * @return:       java.util.Set<java.lang.Object>
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * @param key
     * @description:  获取哈希表key的长度
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * @param key
     * @description:  获取哈希表key的所有值
     * @author:       alex
     * @return:       java.util.List<java.lang.Object>
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }


    //list相关操作
    /**
     * @param key
     * @param index
     * @description:  通过索引获取列表的值
     * @author:       alex
     * @return:       java.lang.Object
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * @param key
     * @param start
     * @param end    -1返回所有值
     * @description:  获取列表指定范围的元素
     * @author:       alex
     * @return:       java.util.List<java.lang.String>
     */
    public List<String> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * @param key
     * @param value
     * @description:  存储在list头部
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @param key
     * @param values
     * @description:  批量添加value
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lLeftPushAll(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * @param key
     * @param list
     * @description:  批量添加list
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lLeftPushAll(String key, Collection<String> list) {
        return redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * @param key
     * @param value
     * @description:  当列表key存在时才加入
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * @param key
     * @param pivot
     * @param value
     * @description:  当指定字符串在列表中存在时，在自定字符串前添加值
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * @param key
     * @param value
     * @description:   在列表尾部添加值
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /*
     * @param key
     * @param values
     * @description:  批量在尾部添加元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRightPushAll(String key, String... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * @param key
     * @param values
     * @description:  批量在尾部添加元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRightPushAll(String key, Collection<String> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * @param key
     * @param value
     * @description:  如果列表存在，在列表的尾部添加元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * @param key
     * @param pivot
     * @param value
     * @description:  如果指定值pivot在列表中存在，则在pivot数据后添加元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRightPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * @param key
     * @description:  移除并获取列表第一个元素
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @param key
     * @param timeout
     * @param unit
     * @description:  移除并获取列表的第一个元素，如果元素不存在会阻塞列表直到超时或发现可弹出元素为止
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lBLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * @param key
     * @description:  移除并获取列表的最有一个元素
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @param key
     * @param timeout
     * @param unit
     * @description:  移除并获取列表的最后一个元素，如果元素不存在会阻塞列表直到超时或发现可弹出元素为止
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lBRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /*
     * @param sourceKey
     * @param destinatiokey
     * @description:  移除列表sourceKey的最后一个元素，并将该元素添加到列表destinationKey中
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinatiokey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinatiokey);
    }

    /*
     * @param sourceKey
     * @param destinatiokey
     * @description:  移除列表sourceKey的最后一个元素，并将该元素添加到列表destinationKey中，如果没有元素会阻塞
     * @author:       alex
     * @return:       java.lang.String
     */
    public String lBRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * @param key
     * @param index  index =0,删除所有值等于value的元素；index>0，从头开始删除第一个等于value的元素；index<0，从后开始删除第一个等于value的值
     * @param value
     * @description:
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  裁剪列表key
     * @author:       alex
     * @return:       void
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /*
     * @param key
     * @description:  返回列表长度
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    //set集合操作
    /**
     * @param key
     * @param values
     * @description:  批量往集合key中添加元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * @param values
     * @description:  批量移除元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * @param key
     * @description:  移除并返回集合一个随机元素
     * @author:       alex
     * @return:       java.lang.String
     */
    public String sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * @param sourceKey
     * @param value
     * @param destinationKey
     * @description:  将元素value从集合sourceKey移动到destinationKey中
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean sMove(String sourceKey, String value, String destinationKey) {
        return redisTemplate.opsForSet().move(sourceKey, value, destinationKey);
    }

    /**
     * @param key
     * @description:  获取集合key的大小
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * @param key
     * @param value
     * @description:  判断集合key中是否存在value元素
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * @param key
     * @param otherKey
     * @description:  获取两个集合的交集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @description:  获取集合key和其他集合们的交集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * @param key
     * @param otherKey
     * @param destKey
     * @description:  获取集合key和集合otherKey的交集，并将结果存储到destKey中去
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /*
     * @param key
     * @param ohterKeys
     * @param destKey
     * @description:  获取集合key和其他集合们的交集，并将结果存储到destKey中
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sIntersectAndStore(String key, Collection<String> ohterKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, ohterKeys, destKey);
    }

    /**
     * @param key
     * @param otherKey
     * @description:  获取集合key和集合otherKey的并集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @description:  获取集合key和其他集合们的并集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * @param key
     * @param otherKey
     * @param destkey
     * @description:  获取集合key和集合otherKey的并集，并将结果存储到destKey中去
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sUnionAndStore(String key, String otherKey, String destkey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destkey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destkey
     * @description:  获取集合key和其他集合的并集，并将结果存储到destKey中去
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destkey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destkey);
    }

    /**
     * @param key
     * @param otherKey
     * @description:  获取集合key和集合otherKey的差集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @description:  获取集合key和其他集合的差集
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * @param key
     * @description:  获取集合所有元素
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /*
     * @param key
     * @description:  随机获取集合的一个元素
     * @author:       alex
     * @return:       java.lang.String
     */
    public String sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /*
     * @param key
     * @param count
     * @description:  随机获取集合中count个元素
     * @author:       alex
     * @return:       java.util.List<java.lang.String>
     */
    public List<String > sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * @param key
     * @param count
     * @description:  随机获取集合中count个元素并且去除重复
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * @param key
     * @param options
     * @description:
     * @author:       alex
     * @return:       org.springframework.data.redis.core.Cursor<java.lang.String>
     */
    public Cursor<String> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }


    //zSet操作
    /**
     * @param key
     * @param value
     * @param score
     * @description:  添加元素value到有序集合key中，元素的值按照score值的由小到大排序
     * @author:       alex
     * @return:       java.lang.Boolean
     */
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @param key
     * @param values
     * @description:  批量添加元素到有序集合key中去
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @param key
     * @param values
     * @description:  删除有序集合key中的元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  删除有序集合key指定范围start到end的元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  删除有序集合key中score从min到max的元素
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param value
     * @param increment
     * @description:  增加increment元素value在有序集合key中的score的值 ,并返回score
     * @author:       alex
     * @return:       java.lang.Double
     */
    public Double zIncrementScore(String key, String value, double increment) {
        return redisTemplate.opsForZSet().incrementScore(key, value, increment);
    }

    /**
     * @param key
     * @param value
     * @description:  返回value值在有序集合key中的排名
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /*
     * @param key
     * @param value
     * @description:  返回value值在有序集合key中的从大到小的排名
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zReverserRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  获取有序集合key从start到end的元素(从小到大排序)
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  获取有序集合key从start到end的元素和score(从小到大排序)
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  根据score值查询集合元素
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  根据score值查询集合元素和score
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @description:  查询指定范围根据score值查询有序集合的元素
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @description:  查询指定范围根据score值查询有序集合的元素和score
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  获取有序集合key从start到end的元素(（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * @param key
     * @param start
     * @param end
     * @description:  获取有序集合key从start到end的元素和score（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  根据score值查询集合元素（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  根据score值查询集合元素和score（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @description:  查询指定范围根据score值查询有序集合的元素（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<java.lang.String>
     */
    public Set<String> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @description:  查询指定范围根据score值查询有序集合的元素和score（从大到小排序）
     * @author:       alex
     * @return:       java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @description:  根据score值获取有序集合key中元素的数量
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zCound(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * @param key
     * @description:  查询有序集合key的大小
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * @param key
     * @description:  获取集合大小
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * @param key
     * @param value
     * @description:  获取有序集合key中value对应的score值
     * @author:       alex
     * @return:       java.lang.Double
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * @param key
     * @param otherKey
     * @param destKey
     * @description:  获取有序集合key和有序集合otherKey的并集，并将结果存储到destKey中
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @description:  获取有序集合key和其他有序集合otherKeys的并集，并将结果存储到destKey中
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * @param key
     * @param otherKey
     * @param destKey
     * @description:  获取有序集合key和有序集合otherKey的交集，并将结果存储到destKey中
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @description:  获取有序集合key和其他有序集合otherKeys的交集，并将结果存储到destKey中
     * @author:       alex
     * @return:       java.lang.Long
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * @param key
     * @param options
     * @description:
     * @author:       alex
     * @return:       org.springframework.data.redis.core.Cursor<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.String>>
     */
    public Cursor<ZSetOperations.TypedTuple<String>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

}
