package com.newmesnet.nowmesnet.service.impl;

import com.newmesnet.nowmesnet.service.IOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zqh
 * @create 2022-06-29 14:11
 */
@Service
public class OAuthService implements IOAuthService {

    private static String LOGIN_REQUEST = "user.login.request";
    private static String REGISTRATION_REQUEST = "user.registration.request";

    /**
     * 时间间隔（分钟）
     */
    private static final int TIME_INTERVAL_MIN = 1;
    /**
     * 登录失败重试次数上限
     */
    private static final int FAILED_RETRY_TIMES = 5;
    /**
     * redis记录用户登录失败次数key
     */
    private static final String USER_LOGIN_FAILED_COUNT = "user.login.failed.count:";
    /**
     * redis记录用户注册失败次数key
     */
    private static final String USER_REGISTER_FAILED_COUNT = "user.register.failed.count:";
    /**
     * redis记录用户登录token key
     */
    private static final String USER_LOGIN_TOKEN = "user.login.token:";
    /**
     * 时间间隔（小时）
     */
    private static final int TIME_WAIT_ONE_HOUR = 1;
    /**
     * 时间间隔（天）
     */
    private static final int TIME_WAIT_ONE_DAY = 1;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Integer getLoginRequestFailByUserId(String userId) {

        String key = USER_LOGIN_FAILED_COUNT + userId;
        RedisAtomicInteger counter = getRedisCounter(key, TIME_INTERVAL_MIN * 10);

        //String key = StringUtils.join(new Object[]{USER_LOGIN_FAILED_COUNT, userId}, ":");
        //String value = redisTemplate.boundValueOps(key).get();

        int value = counter.get();

        return value;
    }

    @Override
    public void addLoginRequestFailByUserId(String userId, boolean flag) {
        //flag==true 次数加一
        if(flag){
            String key = USER_LOGIN_FAILED_COUNT + userId;
            RedisAtomicInteger counter = getRedisCounter(key, TIME_INTERVAL_MIN * 10);
            counter.getAndIncrement();
        }
    }

    @Override
    public void addLoginTokenByUserId(String userId, String token) {
        String key = USER_LOGIN_TOKEN + userId + ":" + token;
        getRedisWaitTimeDay(key, TIME_WAIT_ONE_DAY * 3);
    }

    @Override
    public void clearLoginTokenByUserId(String userId, String token) {
        String key = USER_LOGIN_TOKEN + userId + ":" + token;
        redisTemplate.delete(key);
    }

    @Override
    public Integer getRegisterRequestByIp(String userIp) {
        String key = USER_REGISTER_FAILED_COUNT + userIp;
        RedisAtomicInteger counter = getRedisCounter(key, TIME_INTERVAL_MIN * 10);

        //String key = StringUtils.join(new Object[]{USER_LOGIN_FAILED_COUNT, userId}, ":");
        //String value = redisTemplate.boundValueOps(key).get();

        int value = counter.get();

        return value;
    }

    @Override
    public void addRegisterRequestByIp(String userIp, boolean flag) {
        if(flag){
            String key = USER_REGISTER_FAILED_COUNT + userIp;
            RedisAtomicInteger counter = getRedisCounter(key, TIME_INTERVAL_MIN * 10);
            counter.getAndIncrement();
        }
    }

    @Override
    public void clearLoginRequestByUserId(String userId) {
        String key = USER_LOGIN_FAILED_COUNT + userId;
        redisTemplate.delete(key);
    }

    @Override
    public void clearRegisterRequestByIp(String userIp) {
        String key = USER_LOGIN_FAILED_COUNT + userIp;
        redisTemplate.delete(key);
    }

    /**
     * 根据key获取计数器
     *
     * @param key
     * @return
     */
    private RedisAtomicInteger getRedisCounter(String key, Integer timeInterval) {
        RedisAtomicInteger counter =
                new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        if (counter.get() == 0) {
            // 设置过期时间
            counter.expire(timeInterval, TimeUnit.MINUTES);
        }
        return counter;
    }

    /**
     * 根据key获取计数器（小时）
     *
     * @param key
     * @return
     */
    private RedisAtomicInteger getRedisWaitTimeHour(String key, Integer timeInterval) {
        RedisAtomicInteger counter =
                new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        if (counter.get() == 0) {
            // 设置过期时间
            counter.expire(timeInterval, TimeUnit.HOURS);
        }
        return counter;
    }

    /**
     * 根据key获取计数器（天）
     *
     * @param key
     * @return
     */
    private RedisAtomicInteger getRedisWaitTimeDay(String key, Integer timeInterval) {
        RedisAtomicInteger counter =
                new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        if (counter.get() == 0) {
            // 设置过期时间
            counter.expire(timeInterval, TimeUnit.DAYS);
        }
        return counter;
    }


}
