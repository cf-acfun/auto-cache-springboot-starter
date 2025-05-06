package com.qingkong.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: QingKong
 * @Date: 2025/4/25
 * @Description:
 */

@Data
@ConfigurationProperties(prefix = "auto.cache.redis")
public class AutoCacheProperties {

    /**
     * redis地址
     */
    private String address;

    /**
     * redis密码
     */
    private String password;

    /**
     * 命令超时时间，单位秒
     */
    private long timeout = 5;

    /**
     * 开启
     */
    private boolean enable = true;

    /**
     * 并发线程是否等待
     * true: 多个线程同时请求同一个Key时，只有一个线程去请求方法，其余线程等待结果
     * false: 所有线程都不等待，全部调用方法。
     * 不开启容易造成缓存穿透，默认开启
     */
    private boolean waiting = true;

    /**
     * 配合waiting一起使用，并发请求相同的Key，若Redis不存在时，每次等待的时间间隔
     */
    private long retryTime = 1;

    /**
     * 配合waiting一起使用，并发请求相同的Key，若Redis不存在时，一共等待的次数
     */
    private int retryNum = 3;

    /**
     * 延迟删除的线程大小，若项目中有用到大量的延迟删除，可以把线程池调大
     */
    private int delayPoolNum = 10;
}
