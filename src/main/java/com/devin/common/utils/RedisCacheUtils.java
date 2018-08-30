package com.devin.common.utils;

import com.devin.common.enums.CacheKeyEnum;
import com.devin.common.utils.spring.SpringUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.List;

public class RedisCacheUtils {

    static RedisTemplate redisTemplate;

    public static List  getCacheInfoByList(CacheKeyEnum keyEnum) {
        ListOperations listOperations = getRedisTemplate().opsForList();
        List info = null;
        if (!getRedisTemplate().hasKey(keyEnum.getKey())){
            synchronized (RedisCacheUtils.class) { // 防止并发造成脏读(redis集群则删除)
                if (listOperations.range(keyEnum.getKey(),0,-1).size() > 0) {
                    return listOperations.range(keyEnum.getKey(),0,-1);
                }
                cacheLoad(keyEnum);
                info = listOperations.range(keyEnum.getKey(),0,-1);
            }
        } else {
            info = listOperations.range(keyEnum.getKey(),0,-1);
        }
        return  info;
    }
    public static  void refreshCacheInfo (CacheKeyEnum keyEnum) {
//        cacheLoad(keyEnum);
        getRedisTemplate().delete(keyEnum.getKey());
    }

    private static void cacheLoad(CacheKeyEnum keyEnum) {
        Object bean = SpringUtils.getBean(keyEnum.getInitBean());
        try {
            Method load = bean.getClass().getMethod(keyEnum.getMethod(), null);
            load.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static RedisTemplate getRedisTemplate  () {
        if ( redisTemplate == null) {
            redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");
        }
        return redisTemplate;
    }

}
