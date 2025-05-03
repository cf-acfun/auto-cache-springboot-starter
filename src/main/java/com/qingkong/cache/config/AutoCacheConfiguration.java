package com.qingkong.cache.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: QingKong
 * @Date: 2025/4/25
 * @Description:
 */
@Configuration
@ComponentScan(basePackages = "com.qingkong.cache")
@EnableConfigurationProperties(AutoCacheProperties.class)
public class AutoCacheConfiguration {
}
