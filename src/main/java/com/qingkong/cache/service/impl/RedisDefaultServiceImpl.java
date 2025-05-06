package com.qingkong.cache.service.impl;

import com.google.common.base.Strings;
import com.qingkong.cache.service.IRedisValueService;
import com.qingkong.cache.util.RedisClusterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description:
 */
@Service
@Slf4j
public class RedisDefaultServiceImpl implements IRedisValueService {
    @Override
    public Object getVal(String key, String field, Class<?> returnClass, Type returnType) {
        String val = null;
        if (Strings.isNullOrEmpty(key)) {
            log.info("开始获取缓存数据...");
            RedisClusterUtil.get(key);
        }
        return null;
    }

    @Override
    public void setVal(String key, String field, Object object, Long ex, Class<?> returnClass) {

    }
}
