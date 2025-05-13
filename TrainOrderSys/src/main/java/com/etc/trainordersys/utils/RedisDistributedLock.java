package com.etc.trainordersys.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
@Component
public class RedisDistributedLock {
    @Autowired
    RedisTemplate redisTemplate;
    //加锁
    public boolean tryLock(String key,long expireTime,Integer userId){
        //用户原子性设置键值对的核心方法：当键不存在的时候，设置键的值，返回true；如果已经存在不做任何操作，返回false
        return redisTemplate.opsForValue().setIfAbsent(key,userId,expireTime, TimeUnit.SECONDS);
    }
    //解锁,使用redis+Lua脚本实现安全的分布式锁的释放，主要用于解决分布式系统中的锁误删问题
    public void unLock(String key,Integer userId){
        //LUA脚本作用：在redis中用于安全的释放分布式锁，作用：确保只有锁的持有者才能释放锁，避免锁被误删
        //1.检查redis中KEYS[1]对应的值是否等于ARGV[1]中的值（判断是否属于当前请求者）
        //2.如果匹配，则删除键（释放锁）
        //3.如果不匹配，返回0（防止误删其他客户端的锁）
        //KEY[1]：锁的键（redis中存储锁的key）    ARGV[1]:键的值（通常情况应该使用UUID)
        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +  //// 1. 检查锁的值是否匹配
                        "   return redis.call('del', KEYS[1]) " +  // 2. 匹配则删除锁
                        "else " +
                        "   return 0 " + // 3. 不匹配返回0
                        "end";
        redisTemplate.execute(
                new DefaultRedisScript(script,Long.class),   //4.封装Lua脚本
                Collections.singletonList(key),   //5.传入键（锁的key）
                userId   //6.传入值（value），正常要使用的uuid唯一的标识，但是这里的键和值是相同的
        );
    }
}
