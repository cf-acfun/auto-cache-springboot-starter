package com.qingkong.cache.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: QingKong
 * @Date: 2025/4/30
 * @Description:
 */
@Service
@Slf4j
public class RedisClusterUtil {

    private RedisClusterUtil() {
    }

    public static String get(String key) {
        log.info("get redis key [{}]", key);
        if (Strings.isNullOrEmpty(key)) {
            return null;
        }
        return RedisClientFactory.getConnection().sync().get(key);
    }

}
