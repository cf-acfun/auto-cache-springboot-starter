package com.qingkong.cache.component;

import com.google.common.base.Strings;
import com.qingkong.cache.annotation.Cache;
import com.qingkong.cache.config.AutoCacheProperties;
import com.qingkong.cache.service.IRedisValueService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: 晴空๓
 * @Date: 2025/5/12 20:18
 * @Version: 1.0
 */
@Aspect
@Component
@Order(3)
@Slf4j
public class RedisCacheComponent {

    @Autowired
    private IRedisValueService redisValueService;

    @Autowired
    private AutoCacheProperties properties;



    @Around("execution(* *(..)) && @annotation(com.qingkong.cache.annotation.Cache)")
    public Object cacheAround(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!properties.isEnable()) {
            log.info("cache was closed, start invoke...");
            return joinPoint.proceed(joinPoint.getArgs());
        }

        Object[] values = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> returnClass = method.getReturnType();
        Type returnType = method.getGenericReturnType();
        Cache cache = method.getAnnotation(Cache.class);
//        String key = SpringELParser.getELValue(cache.key(), values);
        String key = cache.key();
        log.info("缓存的key值为[{}]", key);
//        String field = SpringELParser.getELValue(cache.field(), values);
        String field = cache.field();
        log.info("缓存的value值为[{}]", field);
        if (Strings.isNullOrEmpty(key)) {
            return null;
        }

        Object object = redisValueService.getVal(key, field, returnClass, returnType);
        log.info("object = [{}]", object);

        // 如果从缓存中获取到了值则返回缓存中的值
        if (null != object) {
            return object;
        }

        // TODO 如果缓存中的值为空，则加分布式锁访问数据库解决缓存穿透
        /**
         * 通过缓存空对象的方法解决
         * 优点：实现简单，维护方便
         * 缺点：
         *  1. 额外的内存消耗
         *  2. 可能造成数据的短期不一致（根据业务设置一个合理的过期时间）
         */
        return object;
    }
}
