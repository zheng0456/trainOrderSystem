package com.etc.trainordersys.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public class RedisUtils {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 把对象转成json字符串
     * @param value
     * @return
     * @param <T>
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 把json字符串转成json对象
     * @param str
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() == 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     *
     * 减少值,自减1
     * @param prefix
     * @param key
     * @return
     * @param <T>
     */
    public <T> Long decr(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        return redisTemplate.opsForValue().decrement(realKey);
    }

    /**
     * 获取单个对象
     */
    public <T> T get(String prefix, String key, Class<T> clazz) {
        //生成真正的key
        String realKey = prefix + key;
        String str = (String) redisTemplate.opsForValue().get(realKey);
        T t = stringToBean(str, clazz);
        return t;
    }
    /**
     * 获取单个对象
     */
    public <T> T get(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        return (T) redisTemplate.opsForValue().get(realKey);
    }
    /**
     * 设置键值对
     */
    public <T> boolean set(String prefix, String key, T value, int seconds) {
        String str = beanToString(value);
        if (str == null || str.length() == 0) {
            return false;
        }
        //生成真正的key
        String realKey = prefix + key;
        //seconds如果等于0，表示永远不过期
        if (seconds <= 0) {
            redisTemplate.opsForValue().set(realKey, str);  //设置的键值对，永不过期
        } else {
            if (value instanceof Integer){
                redisTemplate.opsForValue().set(realKey,Integer.parseInt(str),seconds, TimeUnit.SECONDS); //设置键值对的同时，设置生存时间
            }else{
                redisTemplate.opsForValue().set(realKey,str,seconds, TimeUnit.SECONDS); //设置键值对的同时，设置生存时间
            }

        }
        return true;
    }

    /**
     * 设置键值对
     */
    public void set(String prefix, String key, int val) {
        //生成真正的key
        String realKey = prefix + key;
        redisTemplate.opsForValue().set(realKey,val);
    }

    /**
     * 判断key是否存在
     */
    public <T> boolean exists(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        return redisTemplate.hasKey(realKey);
    }

    /**
     *
     * 增加值,自加1
     * @param prefix
     * @param key
     * @return
     * @param <T>
     */
    public <T> Long incr(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        return redisTemplate.opsForValue().increment(realKey);
    }
}

