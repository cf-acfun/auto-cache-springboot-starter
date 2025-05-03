package com.qingkong.cache.service.impl;

import com.qingkong.cache.service.IRedisValueService;

import java.lang.reflect.Type;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description:
 */

public class RedisDefaultServiceImpl implements IRedisValueService {
    @Override
    public Object getVal(String key, String field, Class<?> returnClass, Type returnType) {
        return null;
    }

    @Override
    public void setVal(String key, String field, Object object, Long ex, Class<?> returnClass) {

    }
}
