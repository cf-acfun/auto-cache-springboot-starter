package com.qingkong.cache.util;

import com.qingkong.cache.config.AutoCacheProperties;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description:
 */

@Component
@Slf4j
public class RedisClientFactory {

    private static RedisClusterClient redisClusterClient;
    private static StatefulRedisClusterConnection<String, String> connection;

    public RedisClientFactory(AutoCacheProperties properties) {
        log.info("---------------- Redis 连接初始化开始----------------");
        String address = properties.getAddress();
        String password = properties.getPassword();
        RedisURI.Builder builder = RedisURI.builder();
        if (address.contains(",")) {
            String[] split = address.split(",");
        }
    }
}
