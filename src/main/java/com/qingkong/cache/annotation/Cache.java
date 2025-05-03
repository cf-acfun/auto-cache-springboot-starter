package com.qingkong.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qingkong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    // 超时时间，0表示永不超时，单位：秒。过期时间对于Hash数据不生效
    long expire() default 60;

    // redis key
    String key();

    // redis hash表的key
    String field() default "";

    // 并发等待Redis查询结果为空时，是否最终执行方法，默认不执行
    boolean finallyAction() default false;

}
