package com.qingkong.cache.util;

import com.google.common.base.Strings;
import com.qingkong.cache.config.AutoCacheProperties;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description: Redis客户端工厂类，负责初始化和提供Redis集群连接
 */

@Component
@Slf4j
public class RedisClientFactory {

    /**
     * Redis集群客户端实例
     */
    private static RedisClusterClient redisClusterClient;
    /**
     * Redis集群连接实例
     */
    private static StatefulRedisClusterConnection<String, String> connection;

    /**
     * 构造函数，根据配置属性初始化Redis集群连接
     * @param properties 自动缓存配置属性，包含Redis连接所需的信息
     */
    public RedisClientFactory(AutoCacheProperties properties) {
        log.info("---------------- Redis 连接初始化开始----------------");
        String address = properties.getAddress();
        String password = properties.getPassword();
        // 构建RedisURI对象
        RedisURI.Builder builder = RedisURI.builder();
        if (address.contains(",")) {
            String[] hosts = address.split(",");
            for (String host : hosts) {
                String[] hostAndPort = host.split(":");
                builder.withHost(hostAndPort[0]).withPort(Integer.parseInt(hostAndPort[1]));
            }
        } else {
            String[] hostAndPort = address.split(":");
            builder.withHost(hostAndPort[0]).withPort(Integer.parseInt(hostAndPort[1]));
        }
        if (Strings.isNullOrEmpty(password)) {
            builder.withPassword(password.toCharArray());
        }
        // 构建完成的RedisURI对象
        RedisURI redisURI = builder.build();
        // 设置连接超时时间
        redisURI.setTimeout(Duration.ofSeconds(properties.getTimeout()));
        // 创建Redis集群客户端
        redisClusterClient = RedisClusterClient.create(redisURI);
        // 配置集群拓扑刷新选项
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh(Duration.ofSeconds(30))
                .build();
        // 配置集群客户端选项
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .autoReconnect(true)
                .topologyRefreshOptions(topologyRefreshOptions)
                .socketOptions(SocketOptions.builder().keepAlive(true).build())
                .build();
        // 应用集群客户端配置
        redisClusterClient.setOptions(clusterClientOptions);
        log.info("redis client init success");
        // 建立Redis集群连接
        connection = redisClusterClient.connect();
        log.info("redis connection success");
    }

    /**
     * 获取Redis集群连接，如果连接为空或已关闭则重新连接
     * @return
     */
    public static synchronized StatefulRedisClusterConnection<String, String> getConnection() {
        if (connection == null || !connection.isOpen()) {
            connection = redisClusterClient.connect();
        }
        return connection;
    }
}
