package com.qingkong.cache.service;

import java.lang.reflect.Type;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description:
 */

public interface IRedisValueService {

    Object getVal(String key, String field, Class<?> returnClass, Type returnType);

    void setVal(String key, String field, Object object, Long ex, Class<?> returnClass);
}
